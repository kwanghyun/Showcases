package concurrency.book.composing_objects;

import java.util.*;

import net.jcip.annotations.*;

/**
 * ListHelder
 * 
 * Examples of thread-safe and non-thread-safe implementations of put-if-absent
 * helper methods for List
 *
 *
 * Why wouldn't this work? After all, putIfAbsent is synchronized, right? The
 * problem is that it synchronizes on the wrong lock. Whatever lock the List
 * uses to guard its state, it sure isn't the lock on the ListHelper. ListHelper
 * provides only the illusion of synchronization; the various list operations,
 * while all synchronized, use different locks, which means that putIfAbsent is
 * not atomic relative to other operations on the List. So there is no guarantee
 * that another thread won't modify the list while putIfAbsent is executing.
 */

@NotThreadSafe
class BadListHelper<E> {
	/*
	 * Collections.synchronizedList()
	 * 
	 * Returns a synchronized (thread-safe) list backed by the specified list.
	 * In order to guarantee serial access, it is critical that all access to
	 * the backing list is accomplished through the returned list.
	 * 
	 * It is imperative that the user manually synchronize on the returned list
	 * when iterating over it:
	 */
	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public synchronized boolean putIfAbsent(E x) {
		boolean absent = !list.contains(x);
		if (absent)
			list.add(x);
		return absent;
	}
}

/*
 * To make this approach work, we have to use the same lock that the List uses
 * by using client-side locking or external locking. Client-side locking entails
 * guarding client code that uses some object X with the lock X uses to guard
 * its own state. In order to use client-side locking, you must know what lock X
 * uses.
 */
@ThreadSafe
class GoodListHelper<E> {
	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public boolean putIfAbsent(E x) {
		synchronized (list) {
			boolean absent = !list.contains(x);
			if (absent)
				list.add(x);
			return absent;
		}
	}
}
