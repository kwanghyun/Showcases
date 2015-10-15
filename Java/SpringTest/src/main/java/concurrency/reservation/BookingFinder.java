package concurrency.reservation;

import java.util.List;
import java.util.Set;

//Defining Finder
class CarFinder {
	private BookingAdvices advices;
	private CarBookings bookings;

	public void processAdvice() {
		List<BookingAdvice> adviceList = getGeneratedAdvices();
		for (BookingAdvice advice : adviceList)
			advices.putAdvice(advice); // Line 3

		findAndProcessBookings(bookings.getBookings()); // Line 4
	}

	private List<BookingAdvice> getGeneratedAdvices() {
		// Generate advice based on requests
		return null;
	}

	private void findAndProcessBookings(Set bookings) {
		// Finds advice for the booking if required
	}
}
