package concurrency.reservation.bus;

public class Main {
	public static void main(String[] args) {
		BusReservation br = new BusReservation();
		PassengerThread pt1 = new PassengerThread(1, br, "SAM");
		PassengerThread pt2 = new PassengerThread(1, br, "Jack");
		PassengerThread pt3 = new PassengerThread(1, br, "JANG");
		PassengerThread pt4 = new PassengerThread(1, br, "HYUN");
		PassengerThread pt5 = new PassengerThread(1, br, "HYUN");
		PassengerThread pt6 = new PassengerThread(1, br, "HYUN");
		PassengerThread pt7 = new PassengerThread(2, br, "HYUN");
		PassengerThread pt8 = new PassengerThread(2, br, "HYUN");
		pt1.start();
		pt2.start();
		pt3.start();
		pt4.start();
		pt5.start();
		pt6.start();
//		pt7.start();
//		pt8.start();
	}
}
