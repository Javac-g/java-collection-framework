import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class DynamicAdaptiveQueue<T> {
    private final AtomicReferenceArray<T> content;
    private final int capacity;

    private final AtomicInteger size = new AtomicInteger(0);
    private final AtomicInteger out = new AtomicInteger(0);
    private final AtomicInteger in = new AtomicInteger(0);

    private final ReentrantLock putLock = new ReentrantLock();
    private final ReentrantLock getLock = new ReentrantLock();
    private final Condition notEmpty = getLock.newCondition();
    private final Condition notFull = putLock.newCondition();

    private volatile boolean lockFreeMode = true;  // 🔄 Dynamic Mode
    private final AtomicInteger contentionCount = new AtomicInteger(0);

    public DynamicAdaptiveQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
        this.content = new AtomicReferenceArray<>(capacity);
        new Thread(this::monitorContention).start(); // 🔄 Background Auto-Switching
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
        contentionCount.incrementAndGet();
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
        contentionCount.incrementAndGet();
        return false;
    }

    /** 🔹 LOCK-BASED GET **/
    @SuppressWarnings("unchecked")
    private T getLockBased() throws InterruptedException {
        getLock.lock();
        try {
            while (size.get() == 0) notEmpty.await();
            T value = (T) content.getAndSet(out.get(), null);
            out.set((out.get() + 1) % capacity);
            size.decrementAndGet();
            putLock.lock();
            try { notFull.signal(); } finally { putLock.unlock(); }
            return value;
        } finally {
            getLock.unlock();
        }
    }

    /** 🔹 LOCK-BASED PUT **/
    private boolean putLockBased(T value) throws InterruptedException {
        putLock.lock();
        try {
            while (size.get() == capacity) notFull.await();
            content.set(in.get(), value);
            in.set((in.get() + 1) % capacity);
            size.incrementAndGet();
            getLock.lock();
            try { notEmpty.signal(); } finally { getLock.unlock(); }
            return true;
        } finally {
            putLock.unlock();
        }
    }

    /** 🔹 Auto-Switch Between Modes **/
    private void monitorContention() {
        while (true) {
            try {
                Thread.sleep(1000);
                int count = contentionCount.getAndSet(0);
                if (count > 10 && lockFreeMode) {
                    lockFreeMode = false;  // 🔄 Too much contention, switch to lock-based
                } else if (count == 0 && !lockFreeMode) {
                    lockFreeMode = true;  // 🔄 No contention, revert to lock-free
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
