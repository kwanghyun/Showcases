package concurrency.book.composing_objects;

import net.jcip.annotations.*;

/**
 * SafePoint
 *
 * Using SafePoint, we can construct a vehicle tracker that publishes the
 * underlying mutable state without undermining thread safety.
 */
@ThreadSafe
public class I10_SafePoint {
	@GuardedBy("this")
	private int x, y;

	private I10_SafePoint(int[] a) {
		this(a[0], a[1]);
	}

	public I10_SafePoint(I10_SafePoint p) {
		this(p.get());
	}

	public I10_SafePoint(int x, int y) {
		this.set(x, y);
	}

	public synchronized int[] get() {
		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}