package designpattern.singleton.classic;

public class SingletonSync {
	private static SingletonSync uniqueInstance;
 
	// Note : private constructor
	private SingletonSync() {}
 
	public static synchronized SingletonSync getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new SingletonSync();
		}
		return uniqueInstance;
	}
 
	// other useful methods here
}
