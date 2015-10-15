package concurrency.reservation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//Defining first CarBookings
class CarBookings {
	private final Set<CarBooking> bookings = new HashSet<CarBooking>();

	public synchronized void putCar(CarBooking booking) {
		bookings.add(booking);
	}

	public synchronized Set<CarBooking> getBookings() {
		return Collections.unmodifiableSet(new HashSet<CarBooking>(bookings));
	}
}
