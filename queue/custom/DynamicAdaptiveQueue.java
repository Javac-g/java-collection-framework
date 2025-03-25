import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class OptimizedDynamicQueue<T> {
    private final AtomicReferenceArray<T> content;
    private final int capacity;

    private final AtomicInteger size = new AtomicInteger(0);
    private final AtomicInteger out = new AtomicInteger(0);
    private final AtomicInteger in = new AtomicInteger(0);

    private final StampedLock putLock = new StampedLock();
    private final StampedLock getLock = new StampedLock();
    private final LongAdder contentionCount = new LongAdder();

    private volatile boolean lockFreeMode = true;

    public OptimizedDynamicQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
        this.content = new AtomicReferenceArray<>(capacity);
        new Thread(this::monitorContention).start();
    }

    /** 🔹 Adaptive Get **/
    public T get() throws InterruptedException {
        return lockFreeMode ? getLockFree() : getLockBased();
    }

    /** 🔹 Adaptive Put **/
    public boolean put(T value) throws InterruptedException {
        return lockFreeMode ? putLockFree(value) : putLockBased(value);
    }

    /** 🔹 LOCK-FREE GET **/
    @SuppressWarnings("unchecked")
    private T getLockFree() {
        while (size.get() > 0) {
            int index = out.get();
            if (content.compareAndSet(index, (T) content.get(index), null)) {
                out.set((index + 1) % capacity);
                size.decrementAndGet();
                return (T) content.get(index);
            }
        }
        contentionCount.increment();
        return null;
    }

    /** 🔹 LOCK-FREE PUT **/
    private boolean putLockFree(T value) {
        while (size.get() < capacity) {
            int index = in.get();
            if (content.compareAndSet(index, null, value)) {
                in.set((index + 1) % capacity);
                size.incrementAndGet();
                return true;
            }
        }
        contentionCount.increment();
        return false;
    }

    /** 🔹 LOCK-BASED GET **/
    @SuppressWarnings("unchecked")
    private T getLockBased() throws InterruptedException {
        long stamp = getLock.writeLock();
        try {
            while (size.get() == 0) Thread.yield();
            T value = (T) content.getAndSet(out.get(), null);
            out.set((out.get() + 1) % capacity);
            size.decrementAndGet();
            return value;
        } finally {
            getLock.unlockWrite(stamp);
        }
    }

    /** 🔹 LOCK-BASED PUT **/
    private boolean putLockBased(T value) throws InterruptedException {
        long stamp = putLock.writeLock();
        try {
            while (size.get() == capacity) Thread.yield();
            content.set(in.get(), value);
            in.set((in.get() + 1) % capacity);
            size.incrementAndGet();
            return true;
        } finally {
            putLock.unlockWrite(stamp);
        }
    }

    /** 🔹 Faster Auto-Switch **/
    private void monitorContention() {
        while (true) {
            try {
                Thread.sleep(500);  // Check contention every 500ms
                if (contentionCount.sum() > 5 && lockFreeMode) {
                    lockFreeMode = false;
                } else if (contentionCount.sum() == 0 && !lockFreeMode) {
                    lockFreeMode = true;
                }
                contentionCount.reset();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
