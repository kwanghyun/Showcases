package concurrency.book.atomic;

import java.util.concurrent.atomic.*;

import net.jcip.annotations.*;

/**
 * CasNumberRange
 * <p/>
 * Preserving multivariable invariants using CAS
 *
 * In Section 3.4.2, we used a volatile reference to an immutable object to
 * update multiple state variables atomically. That example relied on
 * check-then-act, but in that particular case the race was harmless because we
 * did not care if we occasionally lost an update. In most other situations,
 * such a check-then-act would not be harmless and could compromise data
 * integrity. For example, NumberRange on page 67 could not be implemented
 * safely with a volatile reference to an immutable holder object for the upper
 * and lower bounds, nor with using atomic integers to store the bounds. Because
 * an invariant constrains the two numbers and they cannot be updated
 * simultaneously while preserving the invariant, a number range class using
 * volatile references or multiple atomic integers will have unsafe
 * check-then-act sequences.
 * 
 * We can combine the technique from OneValueCache with atomic references to
 * close the race condition by atomically updating the reference to an immutable
 * object holding the lower and upper bounds. CasNumberRange in Listing 15.3
 * uses an AtomicReference to an IntPair to hold the state; by using
 * compareAndSet it can update the upper or lower bound without the race
 * conditions of NumberRange.
 */
@ThreadSafe
public class I03_CasNumberRange {
	@Immutable
	private static class IntPair {
		// INVARIANT: lower <= upper
		final int lower;
		final int upper;

		public IntPair(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}
	}

	private final AtomicReference<IntPair> values = new AtomicReference<IntPair>(new IntPair(0, 0));

	public int getLower() {
		return values.get().lower;
	}

	public int getUpper() {
		return values.get().upper;
	}

	public void setLower(int i) {
		while (true) {
			IntPair oldv = values.get();
			if (i > oldv.upper)
				throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
			IntPair newv = new IntPair(i, oldv.upper);
			if (values.compareAndSet(oldv, newv))
				return;
		}
	}

	public void setUpper(int i) {
		while (true) {
			IntPair oldv = values.get();
			if (i < oldv.lower)
				throw new IllegalArgumentException("Can't set upper to " + i + " < lower");
			IntPair newv = new IntPair(oldv.lower, i);
			if (values.compareAndSet(oldv, newv))
				return;
		}
	}
}