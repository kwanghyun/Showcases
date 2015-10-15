package designpattern.mycode.strategy;

public abstract class Locker implements ILocker{
	String doorState = "CLOSE";
	String pkgState = "OUT";
	
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
