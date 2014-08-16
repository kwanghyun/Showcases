package concurrency.showcases;

//PossibleReordering could print (1, 0), or (0, 1), or (1, 1): 
//thread A could run to completion before B starts, B could run to 
//completion before A starts, or their actions could be interleaved. 
//But, strangely, PossibleReordering can also print (0, 0)! 

//The JMM defines a partial ordering [2] called happens-before on all actions 
//within the program. To guarantee that the thread executing action B can see 
//the results of action A (whether or not A and B occur in different threads), 
//there must be a happens-before relationship between A and B. In the absence of a 
//happens-before ordering between two operations, the JVM is free to reorder them as it pleases.

public class PossibleReordering {
	static int x = 0, y = 0;
	static int a = 0, b = 0;

	public static void main(String[] args) throws InterruptedException {
		Thread one = new Thread(new Runnable() {
			public void run() {
				a = 1;
				x = b;
			}
		});
		Thread other = new Thread(new Runnable() {
			public void run() {
				b = 1;
				y = a;
			}
		});
		one.start();
		other.start();
		one.join();
		other.join();
		
		System.out.println("( " + x + "," + y + ")");
	}
}