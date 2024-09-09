package lesson20240827;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class BlockingQueueWithSemaphore<T> {

	private final List<T> items = new ArrayList<>();
	private final int capacity;

	private final Semaphore itemsSemaphore;
	private final Semaphore spaceSemaphore;
	private final Semaphore accessSemaphore;

	public BlockingQueueWithSemaphore(int capacity) {
		this.capacity = capacity;
		this.itemsSemaphore = new Semaphore(0);
		this.spaceSemaphore = new Semaphore(capacity);
		this.accessSemaphore = new Semaphore(1);
	}

	public void put(T item) throws InterruptedException {
		spaceSemaphore.acquireUninterruptibly();
		accessSemaphore.acquireUninterruptibly();
		try {
			items.add(item);
		} finally {
			accessSemaphore.release();
		}
		itemsSemaphore.release();
	}

	public T get() throws InterruptedException {
		itemsSemaphore.acquireUninterruptibly();
		accessSemaphore.acquireUninterruptibly();
		T item;
		try {
			item = items.remove(0);
		} finally {
			accessSemaphore.release();
		}
		spaceSemaphore.release();
		System.out.println(spaceSemaphore.availablePermits());
		return item;
	}
}
