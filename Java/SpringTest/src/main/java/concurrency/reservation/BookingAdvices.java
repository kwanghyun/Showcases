package concurrency.reservation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class BookingAdvices {
	private final Set<BookingAdvice> advices = new HashSet<BookingAdvice>();

	public synchronized void putAdvice(BookingAdvice advice) {
		advices.add(advice);
	}

	public synchronized Set<BookingAdvice> getAdvices() {
		return Collections.unmodifiableSet(advices);
	}
}
