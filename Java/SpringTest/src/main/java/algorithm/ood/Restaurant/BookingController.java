package algorithm.ood.Restaurant;

import java.util.Date;

public class BookingController {
	
	Restaurant restaurant = new Restaurant();
	
	public String bookRestaurant(String tableId, int noOfCustomers, String bookingId, Date startTime, Date endTime){
		
		Table table = restaurant.getTable(tableId);
		Booking booking = new Booking(); 
		booking.setId(bookingId);
		booking.getStartTime();
		booking.setEndTime(endTime);
		
		if(restaurant.getWatingList().isEmpty()){
			if(table.isBookingAvailbale(noOfCustomers, startTime, endTime));
				table.bookTable(booking);			
		}else{
			restaurant.putWaitingList(booking);
		}
		return bookingId;
	}
	
	public String cancelBooking(String tableId){
		Table table = restaurant.getTable(tableId);
		table.cancelBooking(tableId);
		return table.getId();
	}
}
