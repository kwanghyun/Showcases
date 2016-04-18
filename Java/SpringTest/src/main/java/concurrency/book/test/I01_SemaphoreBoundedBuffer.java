package concurrency.book.test;

import java.util.concurrent.*;

import net.jcip.annotations.*;

/**
 * BoundedBuffer
 * <p/>
 * Bounded buffer using \Semaphore
 *
 * As a concrete illustration, we're going to build a set of test cases for a
 * bounded buffer. Listing 12.1 shows our BoundedBuffer implementation, using
 * Semaphore to implement the required bounding and blocking.
 * 
 * BoundedBuffer implements a fixed-length array-based queue with blocking put
 * and take methods controlled by a pair of counting semaphores. The
 * availableItems semaphore represents the number of elements that can be
 * removed from the buffer, and is initially zero (since the buffer is initially
 * empty). Similarly, availableSpaces represents how many items can be inserted
 * into the buffer, and is initialized to the size of the buffer.
 * 
 * A take operation first requires that a permit be obtained from
 * availableItems. This succeeds immediately if the buffer is nonempty, and
 * otherwise blocks until the buffer becomes nonempty. Once a permit is
 * obtained, the next element from the buffer is removed and a permit is
 * released to the availableSpaces semaphore.[2] The put operation is defined
 * conversely, so that on exit from either the put or take methods, the sum of
 * the counts of both semaphores always equals the bound. (In practice, if you
 * need a bounded buffer you should use ArrayBlockingQueue or
 * LinkedBlockingQueue rather than rolling your own, but the technique used here
 * illustrates how insertions and removals can be controlled in other data
 * structures as well.)
 */
@ThreadSafe
public class I01_SemaphoreBoundedBuffer<E> {
	private final Semaphore availableItems, availableSpaces;
	@GuardedBy("this")
	private final E[] items;
	@GuardedBy("this")
	private int putPosition = 0, takePosition = 0;

	public I01_SemaphoreBoundedBuffer(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException();
		availableItems = new Semaphore(0);
		availableSpaces = new Semaphore(capacity);
		items = (E[]) new Object[capacity];
	}

	public boolean isEmpty() {
		return availableItems.availablePermits() == 0;
	}

	public boolean isFull() {
		return availableSpaces.availablePermits() == 0;
	}

	public void put(E x) throws InterruptedException {
		/*
		 * acquire(): Acquires a permit, if one is available and returns
		 * immediately, reducing the number of available permits by one.
		 * 
		 * If no permit is available then the current thread becomes disabled
		 * for thread scheduling purposes and lies dormant until one of two
		 * things happens:
		 * 
		 * Some other thread invokes the release method for this semaphore and
		 * the current thread is next to be assigned a permit; or
		 * 
		 * Some other thread interrupts the current thread.
		 */
		availableSpaces.acquire();
		doInsert(x);
		/*
		 * release(): Releases a permit, increasing the number of available
		 * permits by one. If any threads are trying to acquire a permit, then
		 * one is selected and given the permit that was just released.
		 * 
		 * There is no requirement that a thread that releases a permit must
		 * have acquired that permit by calling acquire.
		 */
		availableItems.release();
	}

	public E take() throws InterruptedException {
		availableItems.acquire();
		E item = doExtract();
		availableSpaces.release();
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