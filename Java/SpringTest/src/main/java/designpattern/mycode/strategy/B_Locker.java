package designpattern.mycode.strategy;

public class B_Locker extends Locker implements ILocker{


	public void open() {
		setDoorState("OPEN");
		System.out.println("[B_locker] Door status =>" + getDoorState());
		
	}

	public void close() {
		setDoorState("CLOSE");
		System.out.println("[B_locker] Door status =>" + getDoorState());
	}

	public void putPackage() {
		setPkgState("IN");
		System.out.println("[B_locker] Package status => " + getPkgState());
		
	}

	public void takePackage() {
		setPkgState("OUT");
		System.out.println("[B_locker] Package status => " + getPkgState());
	}

	
}
