package concurrency.book.building_blocks;

import java.math.BigInteger;
import java.util.*;

import net.jcip.annotations.*;

/**
 * Memoizer1
 *
 * Initial cache attempt using HashMap and synchronization
 *
 * HashMap is not thread-safe, so to ensure that two threads do not access the
 * HashMap at the same time, Memoizer1 takes the conservative approach of
 * synchronizing the entire compute method. This ensures thread safety but has
 * an obvious scalability problem: only one thread at a time can execute compute
 * at all. If another thread is busy computing a result, other threads calling
 * compute may be blocked for a long time. If multiple threads are queued up
 * waiting to compute values not already computed, compute may actually take
 * longer than it would have without memoization
 */
public class I10_Memoizer1<A, V> implements Computable<A, V> {
	@GuardedBy("this")
	private final Map<A, V> cache = new HashMap<A, V>();
	private final Computable<A, V> c;

	public I10_Memoizer1(Computable<A, V> c) {
		this.c = c;
	}

	public synchronized V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if (result == null) {
			result = c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}
}

interface Computable<A, V> {
	V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction implements Computable<String, BigInteger> {
	public BigInteger compute(String arg) {
		// after deep thought...
		return new BigInteger(arg);
	}
}