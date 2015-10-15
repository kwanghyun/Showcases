package designpattern.mycode.strategy;

public class Client {

	public static void main(String[] args) {
		Locker locker = LockerFactory.getLocker("A_LOCKER");
		locker.open();
		locker.putPackage();
		locker.takePackage();
		locker.close();
		
		Locker locker2 = LockerFactory.getLocker("B_LOCKER");
		locker2.open();
		locker2.putPackage();
		locker2.takePackage();
		locker2.close();
	}
}
