package concurrency.book.test;

import java.util.concurrent.atomic.*;

/**
 * XorShift
 *
 * To ensure that your test actually tests what you think it does, it is
 * important that the checksums themselves not be guessable by the compiler. It
 * would be a bad idea to use consecutive integers as your test data because
 * then the result would always be the same, and a smart compiler could
 * conceivably just precompute it.
 * 
 * To avoid this problem, test data should be generated randomly, but many
 * otherwise effective tests are compromised by a poor choice of random number
 * generator (RNG). Random number generation can create couplings between
 * classes and timing artifacts because most random number generator classes are
 * threadsafe and therefore introduce additional synchronization.[4] Giving each
 * thread its own RNG allows a non-thread-safe RNG to be used.
 * 
 * [4] Many benchmarks are, unbeknownst to their developers or users, simply
 * tests of how great a concurrency bottleneck the RNG is.
 * 
 * Rather than using a general-purpose RNG, it is better to use simple
 * pseudorandom functions. You don’t need high-quality randomness; all you need
 * is enough randomness to ensure the numbers change from run to run. The
 * xor-Shift function in Listing (Marsaglia, 2003) is among the cheapest
 * mediumquality random number functions. Starting it off with values based on
 * hashCode and nanoTime makes the sums both unguessable and almost always
 * different for each run.
 */
public class I03_XorShift {
	static final AtomicInteger seq = new AtomicInteger(8862213);
	int x = -1831433054;

	public I03_XorShift(int seed) {
		x = seed;
	}

	public I03_XorShift() {
		this((int) System.nanoTime() + seq.getAndAdd(129));
	}

	public int next() {
		x ^= x << 6;
		x ^= x >>> 21;
		x ^= (x << 7);
		return x;
	}
}