import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.stream.IntStream;

public class QueueBenchmark {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int OPS_PER_THREAD = 1000000;
    private static final int QUEUE_SIZE = 1024;
    
    public static void main(String[] args) throws InterruptedException {
        UltraFastQueue<Integer> queue = new UltraFastQueue<>(QUEUE_SIZE);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.nanoTime();

        // 🚀 Parallel Writes
        IntStream.range(0, NUM_THREADS).forEach(i -> 
            executor.execute(() -> {
                for (int j = 0; j < OPS_PER_THREAD; j++) {
                    queue.put(j);
                }
            })
        );

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        long endTime = System.nanoTime();

        // 🚀 Results
        double elapsedTime = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Throughput: %.2f ops/sec%n", (NUM_THREADS * OPS_PER_THREAD) / (elapsedTime / 1000.0));
    }
}
