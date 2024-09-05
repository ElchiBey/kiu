package lesson20240820;

import org.junit.Test;

import java.util.concurrent.*;

public class BlockingQueueTest {

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<>();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable producer = () -> {
            for (int i = 0; i < 100; i++) {
                queue.put(i);
            }
        };

        Runnable consumer = () -> {
            for (int i = 0; i < 100; i++) {
                int item = queue.get();
                System.out.println(Thread.currentThread() + "Consumed: " + item);
            }
        };

        for (int i = 0; i < 5; i++) {
            executor.submit(producer);
            executor.submit(consumer);
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // Verify the final state of the queue or perform other assertions
        assert queue.size() == 0; // Ensure the queue is empty after all operations
    }
}