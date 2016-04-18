package concurrency.book.atomic;

import java.util.concurrent.atomic.*;

import net.jcip.annotations.*;

/**
 * AtomicPseudoRandom
 * <p/>
 * Random number generator using AtomicInteger
 *
 */
@ThreadSafe
public class I05_AtomicPseudoRandom extends PseudoRandom {
	private AtomicInteger seed;

	I05_AtomicPseudoRandom(int seed) {
		this.seed = new AtomicInteger(seed);
	}

	public int nextInt(int n) {
		while (true) {
			int s = seed.get();
			int nextSeed = calculateNext(s);
			if (seed.compareAndSet(s, nextSeed)) {
				int remainder = s % n;
				return remainder > 0 ? remainder : remainder + n;
			}
		}
	}
}