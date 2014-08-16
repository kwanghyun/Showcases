package concurrency.advance;

import concurrency.annotations.GuardedBy;
import concurrency.annotations.ThreadSafe;

/*Compare and Swap*/
public class CompareAndSwap {
	@ThreadSafe
	public class SimulatedCAS {
		@GuardedBy("this")
		private int value;

		public synchronized int get() {
			return value;
		}

		public synchronized int compareAndSwap(int expectedValue, int newValue) {
			int oldValue = value;
			if (oldValue == expectedValue)
				value = newValue;
			return oldValue;
		}

		public synchronized boolean compareAndSet(int expectedValue,
				int newValue) {
			return (expectedValue == compareAndSwap(expectedValue, newValue));
		}
	}

	/* Nonblocking Counter Using CAS. */
	@ThreadSafe
	public class CasCounter {
		private SimulatedCAS value;

		public int getValue() {
			return value.get();
		}

		public int increment() {
			int v;
			do {
				v = value.get();
			} while (v != value.compareAndSwap(v, v + 1));
			return v + 1;
		}
	}
}
