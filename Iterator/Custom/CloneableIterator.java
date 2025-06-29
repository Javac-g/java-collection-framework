import java.util.*;

public class CloneableIterator<T> {
    private final Iterator<T> source;
    private final List<T> buffer = new ArrayList<>();
    private final List<Integer> positions = new ArrayList<>();

    public CloneableIterator(Iterator<T> source) {
        this.source = source;
    }

    public Iterator<T> clone() {
        int index = 0;
        positions.add(index);
        int myId = positions.size() - 1;

        return new Iterator<T>() {
            private int pos = positions.get(myId);

            @Override
            public boolean hasNext() {
                fillBuffer();
                return pos < buffer.size();
            }

            @Override
            public T next() {
                fillBuffer();
                if (pos >= buffer.size()) {
                    throw new NoSuchElementException();
                }
                T value = buffer.get(pos++);
                positions.set(myId, pos);
                cleanupBuffer();
                return value;
            }

            private void fillBuffer() {
                if (!source.hasNext()) return;
                while (buffer.size() <= pos && source.hasNext()) {
                    buffer.add(source.next());
                }
            }

            private void cleanupBuffer() {
                int min = Collections.min(positions);
                if (min > 0) {
                    buffer.subList(0, min).clear();
                    for (int i = 0; i < positions.size(); i++) {
                        positions.set(i, positions.get(i) - min);
                    }
                }
            }
        };
    }
}
