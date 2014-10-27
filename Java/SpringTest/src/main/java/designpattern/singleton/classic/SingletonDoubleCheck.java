package designpattern.singleton.classic;

//
// Danger!  This implementation of Singleton not
// guaranteed to work prior to Java 5
//

public class SingletonDoubleCheck {
	private volatile static SingletonDoubleCheck uniqueInstance;

	private SingletonDoubleCheck() {
	}

	public static SingletonDoubleCheck getInstance() {
		if (uniqueInstance == null) {
			synchronized (SingletonDoubleCheck.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new SingletonDoubleCheck();
				}
			}
		}
		return uniqueInstance;
	}
}
