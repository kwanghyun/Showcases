package concurrency.book.performance;

import net.jcip.annotations.*;

/**
 * StripedMap
 * <p/>
 * Hash-based map using lock striping
 *
 * 11.4.3. Lock Striping
 * 
 * Splitting a heavily contended lock into two is likely to result in two
 * heavily contended locks. While this will produce a small scalability
 * improvement by enabling two threads to execute concurrently instead of one,
 * it still does not dramatically improve prospects for concurrency on a system
 * with many processors. The lock splitting example in the ServerStatus classes
 * does not offer any obvious opportunity for splitting the locks further.
 * 
 * Lock splitting can sometimes be extended to partition locking on a
 * variablesized set of independent objects, in which case it is called lock
 * striping. For example, the implementation of ConcurrentHashMap uses an array
 * of 16 locks, each of which guards 1/16 of the hash buckets; bucket N is
 * guarded by lock N mod 16. Assuming the hash function provides reasonable
 * spreading characteristics and keys are accessed uniformly, this should reduce
 * the demand for any given lock by approximately a factor of 16. It is this
 * technique that enables ConcurrentHashMap to support up to 16 concurrent
 * writers. (The number of locks could be increased to provide even better
 * concurrency under heavy access on high-processor-count systems, but the
 * number of stripes should be increased beyond the default of 16 only when you
 * have evidence that concurrent writers are generating enough contention to
 * warrant raising the limit.)
 * 
 * One of the downsides of lock striping is that locking the collection for
 * exclusive access is more difficult and costly than with a single lock.
 * Usually an operation can be performed by acquiring at most one lock, but
 * occasionally you need to lock the entire collection, as when
 * ConcurrentHashMap needs to expand the map and rehash the values into a larger
 * set of buckets. This is typically done by acquiring all of the locks in the
 * stripe set.[10]
 * 
 * [10]The only way to acquire an arbitrary set of intrinsic locks is via
 * recursion.
 * 
 * StripedMap in Listing 11.8 illustrates implementing a hash-based map using
 * lock striping. There are N_LOCKS locks, each guarding a subset of the
 * buckets. Most methods, like get, need acquire only a single bucket lock. Some
 * methods may need to acquire all the locks but, as in the implementation for
 * clear, may not need to acquire them all simultaneously.
 */
@ThreadSafe
public class I06_StripedMap {
	// Synchronization policy: buckets[n] guarded by locks[n%N_LOCKS]
	private static final int N_LOCKS = 16;
	private final Node[] buckets;
	private final Object[] locks;

	private static class Node {
		Node next;
		Object key;
		Object value;
	}

	public I06_StripedMap(int numBuckets) {
		buckets = new Node[numBuckets];
		locks = new Object[N_LOCKS];
		for (int i = 0; i < N_LOCKS; i++)
			locks[i] = new Object();
	}

	private final int hash(Object key) {
		return Math.abs(key.hashCode() % buckets.length);
	}

	public Object get(Object key) {
		int hash = hash(key);
		synchronized (locks[hash % N_LOCKS]) {
			for (Node m = buckets[hash]; m != null; m = m.next)
				if (m.key.equals(key))
					return m.value;
		}
		return null;
	}

	public void clear() {
		for (int i = 0; i < buckets.length; i++) {
			synchronized (locks[i % N_LOCKS]) {
				buckets[i] = null;
			}
		}
	}
}