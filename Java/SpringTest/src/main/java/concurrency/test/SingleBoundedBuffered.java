package concurrency.test;

import java.util.concurrent.Semaphore;

import concurrency.annotations.GuardedBy;

public class SingleBoundedBuffered<E> {
	private final Semaphore semaphore;
	private final int capacity;
	@GuardedBy("this")
	private final E[] items;
	@GuardedBy("this")
	private int putPosition = 0, takePosition = 0;

	public SingleBoundedBuffered(int capacity) {
		this.capacity = capacity;
		semaphore = new Semaphore(this.capacity);
		items = (E[]) new Object[this.capacity];
	}

	public boolean isEmpty() {
		System.out.println("isEmpty - availablePermits : " + semaphore.availablePermits());
		return semaphore.availablePermits() ==this.capacity;
	}

	public boolean isFull() {
		System.out.println("isFull - availablePermits : " + semaphore.availablePermits());
		return semaphore.availablePermits() == 0;
	}

	public void put(E x) throws InterruptedException {
		semaphore.acquire();
		doInsert(x);
	}

	public E take() throws InterruptedException {
		E item = doExtract();
		semaphore.release();
		return item;
	}

	private synchronized void doInsert(E x) {
		int i = putPosition;
		items[i] = x;
		putPosition = (++i == items.length) ? 0 : i;
	}

	private synchronized E doExtract() {
		int i = takePosition;
		E x = items[i];
		items[i] = null;
		takePosition = (++i == items.length) ? 0 : i;
		return x;
	}
}