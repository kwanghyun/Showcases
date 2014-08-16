package concurrency.advance;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import concurrency.annotations.ThreadSafe;

public class RandomNumberGenerator {

	public class PseudoRandom {
		Random rd = new Random();

		public int calculateNext(int i) {
			return rd.nextInt(i);
		}
	}

	@ThreadSafe
	public class ReentrantLockPseudoRandom extends PseudoRandom {

		private final Lock lock = new ReentrantLock(false);
		private int seed;

		ReentrantLockPseudoRandom(int seed) {
			this.seed = seed;
		}

		public int nextInt(int n) {
			lock.lock();
			try {
				int s = seed;
				seed = calculateNext(s);
				int remainder = s % n;
				return remainder > 0 ? remainder : remainder + n;
			} finally {
				lock.unlock();
			}
		}
	}

	@ThreadSafe
	public class AtomicPseudoRandom extends PseudoRandom {
		private AtomicInteger seed;

		AtomicPseudoRandom(int seed) {
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
