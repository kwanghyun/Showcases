package designpattern.mycode.strategy;

public class LockerFactory {
	public static Locker getLocker(String lockerName){
		Locker locker;
		if(lockerName.equalsIgnoreCase("A_LOCKER")){
			locker = new A_Locker();
		}else if(lockerName.equalsIgnoreCase("B_LOCKER")){
			locker = new B_Locker();
		}else{
			throw new RuntimeException();
		}
		return locker;
	}
}
