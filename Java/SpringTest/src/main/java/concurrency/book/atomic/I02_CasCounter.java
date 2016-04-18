package concurrency.book.atomic;

import net.jcip.annotations.*;

/**
 * CasCounter
 * <p/>
 * Nonblocking counter using CAS
 *
 * CasCounter in Listing 15.2 implements a thread-safe counter using CAS. The
 * increment operation follows the canonical form—fetch the old value, transform
 * it to the new value (adding one), and use CAS to set the new value. If the
 * CAS fails, the operation is immediately retried. Retrying repeatedly is
 * usually a reasonable strategy, although in cases of extreme contention it
 * might be desirable to wait or back off before retrying to avoid livelock.
 * 
 * CasCounter does not block, though it may have to retry several[4] times if
 * other threads are updating the counter at the same time. (In practice, if all
 * you need is a counter or sequence generator, just use AtomicInteger or
 * AtomicLong, which provide atomic increment and other arithmetic methods.)
 */
@ThreadSafe
public class I02_CasCounter {
	private I01_SimulatedCAS value;

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