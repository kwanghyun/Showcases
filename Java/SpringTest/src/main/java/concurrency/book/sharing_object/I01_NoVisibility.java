package concurrency.book.sharing_object;

/**
 * NoVisibility
 * <p/>
 * Sharing variables without synchronization
 *
 * NoVisibility could loop forever because the value of ready might never become
 * visible to the reader thread. Even more strangely, NoVisibility could print
 * zero because the write to ready might be made visible to the reader thread
 * before the write to number, a phenomenon known as reordering. There is no
 * guarantee that operations in one thread will be performed in the order given
 * by the program, as long as the reordering is not detectable from within that
 * thread—even if the reordering is apparent to other threads.[1] When the main
 * thread writes first to number and then to ready without synchronization, the
 * reader thread could see those writes happen in the opposite order—or not at
 * all.
 */

public class I01_NoVisibility {
	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {
		public void run() {
			while (!ready)
				Thread.yield();
			/* Thread.yield();
			 * A hint to the scheduler that the current thread is willing to
			 * yield its current use of a processor. The scheduler is free to
			 * ignore this hint.
			 * 
			 * Yield is a heuristic attempt to improve relative progression
			 * between threads that would otherwise over-utilise a CPU. Its use
			 * should be combined with detailed profiling and benchmarking to
			 * ensure that it actually has the desired effect.
			 * 
			 * It is rarely appropriate to use this method. It may be useful for
			 * debugging or testing purposes, where it may help to reproduce
			 * bugs due to race conditions. It may also be useful when designing
			 * concurrency control constructs such as the ones in the
			 * java.util.concurrent.locks package.
			 */
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		new ReaderThread().start();
		number = 42;
		ready = true;
	}
}
