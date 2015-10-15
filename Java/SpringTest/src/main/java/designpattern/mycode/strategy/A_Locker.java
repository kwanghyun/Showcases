package designpattern.mycode.strategy;

public class A_Locker extends Locker implements ILocker{
	String doorState = "CLOSE";
	String pkgState = "OUT";

	public void open() {
		setDoorState("OPEN");
		System.out.println("{A_locker} Door status =>" + getDoorState());
		
	}

	public void close() {
		setDoorState("CLOSE");
		System.out.println("{A_locker} Door status =>" + getDoorState());
	}

	public void putPackage() {
		setPkgState("IN");
		System.out.println("{A_locker} Package status => " + getPkgState());
		
	}

	public void takePackage() {
		setPkgState("OUT");
		System.out.println("{A_locker} Package status => " + getPkgState());
	}
	
	public String getDoorState() {
		return doorState;
	}

	public void setDoorState(String doorState) {
		this.doorState = doorState;
	}

	public String getPkgState() {
		return pkgState;
	}

	public void setPkgState(String pkgState) {
		this.pkgState = pkgState;
	}
	
	
}
