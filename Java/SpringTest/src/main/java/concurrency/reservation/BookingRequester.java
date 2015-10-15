package concurrency.reservation;

import java.util.Set;

//Defining Requester
class BookingRequester {
	private CarBookings bookings;
	private BookingAdvices advices;

	public void requestBooking(CarBooking booking) {
		bookings.putCar(booking); // Line 1
		displayAdvice(advices.getAdvices()); // Line 2
	}

	private void displayAdvice(Set<BookingAdvice> advices) {
		// Displays the advices on the same screen
	}
}
