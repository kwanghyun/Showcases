package algorithm.ood.Restaurant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Table {

	private String id;
	private int capacity;

	/*
	 * Best choice is Ordered by time, first think about Treemap becuase it
	 * ordered map. But we need start, end time comparison for available booking
	 */
	private Map<String, Booking> bookings = new HashMap<String, Booking>(); 

	public Map<String, Booking> getBooking() {
		return bookings;
	}

	public void setBooking(Map<String, Booking> booking) {
		this.bookings = booking;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isBookingAvailbale(int noOfCustomers, Date startTime,
			Date endTime) {
		if (noOfCustomers > capacity)
			return false;
		// if(SQL Qeuery Between 'startTime and 'endTime' )
		return false;
	}

	public String bookTable(Booking booking) {
		bookings.put(booking.getId(), booking);
		return booking.getId();
	}

	public String cancelBooking(String Id) {
		if(bookings.containsKey(id))
			bookings.remove(id);
		else
			new RuntimeException("No such Booking Exist");
		return id;
	}

}
