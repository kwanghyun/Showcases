package concurrency.book.sharing_object;

/*
 * Listing 3.4 illustrates a typical use of volatile variables:checking a status flag to determine when to exit a loop.
 * In this example,our anthropomorphized thread is trying to get to sleep by the time-honored method 
 * of counting sheep.For this example to work,the asleep flag must be volatile.
 * 
 * Otherwise,the thread might not notice when asleep has been set by another thread
 * */
public class I03_CountingSheep {
	volatile boolean asleep;

	void tryToSleep() {
		while (!asleep)
			countSomeSheep();
	}

	void countSomeSheep() {
		// One, two, three...
	}
}
