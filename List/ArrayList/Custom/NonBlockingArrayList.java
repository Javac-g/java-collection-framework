import java.util.concurrent.atomic.AtomicReferenceArray;

public class NonBlockingArrayList<T> {
    private final AtomicReferenceArray<T> content;
    private final int capacity;

    public NonBlockingArrayList(int size) {
        this.capacity = size;
        this.content = new AtomicReferenceArray<>(size);
    }

    public boolean add(int index, T item) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException();
        }
        return content.compareAndSet(index, null, item);
    }

    public boolean remove(int index) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException();
        }
        return content.compareAndSet(index, content.get(index), null);
    }

    public T get(int index) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException();
        }
        return content.get(index);
    }

    public int size() {
        int count = 0;
        for (int i = 0; i < capacity; i++) {
            if (content.get(i) != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < capacity; i++) {
            if (content.get(i) != null) {
                sb.append(content.get(i)).append(", ");
            }
        }
        if (sb.length() > 1) sb.setLength(sb.length() - 2);
        return sb.append("]").toString();
    }
}
