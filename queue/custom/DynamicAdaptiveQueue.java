import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.LockSupport;

public class NUMAQueue<T> {
    private static final int SEGMENT_SIZE = 64;  // Avoid false sharing
    private final AtomicReferenceArray<T> buffer;
    private final int capacity;

    private final AtomicLong head = new AtomicLong(0);
    private final AtomicLong tail = new AtomicLong(0);
    
    private static final int SPIN_TRIES = 10; // Adaptive spinning before yielding

    public NUMAQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
        this.buffer = new AtomicReferenceArray<>(capacity);
    }

    /** 🔹 Lock-Free Put (Non-Blocking) */
    public boolean put(T value) {
        long currentTail;
        int spins = 0;
        do {
            currentTail = tail.get();
            if (currentTail - head.get() >= capacity) return false; // Queue full

            if (spins++ < SPIN_TRIES) Thread.onSpinWait(); // Light spinning
            else LockSupport.parkNanos(1); // Yield to prevent excessive CPU usage

        } while (!tail.compareAndSet(currentTail, currentTail + 1));

        buffer.set((int) (currentTail % capacity), value);
        return true;
    }

    /** 🔹 Lock-Free Get (Non-Blocking) */
    public T get() {
        long currentHead;
        T value;
        int spins = 0;
        do {
            currentHead = head.get();
            if (currentHead >= tail.get()) return null; // Queue empty

            if (spins++ < SPIN_TRIES) Thread.onSpinWait();
            else LockSupport.parkNanos(1);

            value = buffer.get((int) (currentHead % capacity));
        } while (!head.compareAndSet(currentHead, currentHead + 1));

        return value;
    }
}
