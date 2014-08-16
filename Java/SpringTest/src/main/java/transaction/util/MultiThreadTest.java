package transaction.util;

public class MultiThreadTest {
	static int count=0;
	static void doSomething1(){

		while(true){
			try {
				System.out.println("Thread1 added count : " + count++ );
				Thread.sleep(1000);
				if(count==25) {
					System.out.println("Ending Thread1");
					break;
				}
			} catch (InterruptedException e) {
				System.out.println("InterruptedException1 Interupted");
			}
		}
//		throw new RuntimeException();
	}
	
	static void doSomething2(){
		while(true){
			try {
				System.out.println("Thread2 added count  : " + count++ );
				Thread.sleep(1000);
				if(count==25) {
					System.out.println("Ending Thread2");
					break;
				}
			} catch (InterruptedException e) {
				System.out.println("InterruptedException1 Interupted");
			}
		}
	}
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("Threa1 Started");
					doSomething1();
				} catch (RuntimeException e) {
				}
			}
		}, "Thread One");

		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				System.out.println("Threa2 Started");
				doSomething2();
			}
		}, "Thread Two");

		thread1.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException3 Interupted");
		}
		thread2.start();
	}
}
