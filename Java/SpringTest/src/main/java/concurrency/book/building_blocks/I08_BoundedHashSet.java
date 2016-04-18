package concurrency.book.building_blocks;

import java.util.*;
import java.util.concurrent.*;

/**
 * BoundedHashSet
 * 
 * Using Semaphore to bound a collection
 *
 * Semaphores are useful for implementing resource pools such as database
 * connection pools. While it is easy to construct a fixed-sized pool that fails
 * if you request a resource from an empty pool, what you really want is to
 * block if the pool is empty and unblock when it becomes nonempty again. If you
 * initialize a Semaphore to the pool size, acquire a permit before trying to
 * fetch a resource from the pool, and release the permit after putting a
 * resource back in the pool, acquire blocks until the pool becomes nonempty.
 * 
 * Similarly, you can use a Semaphore to turn any collection into a blocking
 * bounded collection, as illustrated by BoundedHashSet
 */
public class I08_BoundedHashSet<T> {
	private final Set<T> set;
	private final Semaphore sem;

	public I08_BoundedHashSet(int bound) {
		/*
		 * Returns a synchronized (thread-safe) set backed by the specified set.
		 * In order to guarantee serial access, it is critical that all access
		 * to the backing set is accomplished through the returned set.
		 */
		this.set = Collections.synchronizedSet(new HashSet<T>());
		sem = new Semaphore(bound);
	}

	public boolean add(T o) throws InterruptedException {
		sem.acquire();
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o);
			return wasAdded;
		} finally {
			if (!wasAdded)
				sem.release();
		}
	}

	public boolean remove(Object o) {
		boolean wasRemoved = set.remove(o);
		if (wasRemoved)
			sem.release();
		return wasRemoved;
	}
}