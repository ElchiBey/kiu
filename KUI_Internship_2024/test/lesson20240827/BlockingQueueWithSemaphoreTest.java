package lesson20240827;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class BlockingQueueWithSemaphoreTest {

    private BlockingQueueWithSemaphore<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new BlockingQueueWithSemaphore<>(3);
    }

    @Test
    void testPutAndGet() throws InterruptedException {
        queue.put(1);
        queue.put(2);
        queue.put(3);

        assertEquals(1, queue.get());
        assertEquals(2, queue.get());
        assertEquals(3, queue.get());
    }

    @Test
    void testBlockingBehaviorOnFullQueue() throws InterruptedException {
        queue.put(1);
        queue.put(2);
        queue.put(3);

        var executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                queue.put(4);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        assertFalse(future.isDone());

        // now get an item from the queue, allowing the blocked put to proceed
        queue.get();
        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testBlockingBehaviorOnEmptyQueue() throws InterruptedException {
        var executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            try {
                return queue.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        });

        assertFalse(future.isDone());

        // now put an item into the queue, allowing the blocked get to proceed
        queue.put(1);
        try {
            assertEquals(1, future.get(1, TimeUnit.SECONDS));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testConcurrentPutAndGet() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(2);

        Future<?> producer = executor.submit(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Future<?> consumer = executor.submit(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    int item = queue.get();
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        try {
            producer.get(1, TimeUnit.SECONDS);
            consumer.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
        assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS));
    }
}
