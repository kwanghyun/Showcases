package concurrency.book.liveness_harzards;

import java.util.concurrent.Executors;

/**
 * LeftRightDeadlock
 *
 * Simple lock-ordering deadlock
 *
 * The deadlock in LeftRightDeadlock came about because the two threads
 * attempted to acquire the same locks in a different order. If they asked for
 * the locks in the same order, there would be no cyclic locking dependency and
 * therefore no deadlock. If you can guarantee that every thread that needs
 * locks L and M at the same time always acquires L and M in the same order,
 * there will be no deadlock.
 * 
 * A program will be free of lock-ordering deadlocks if all threads acquire the
 * locks they need in a fixed global order.
 * 
 * Verifying consistent lock ordering requires a global analysis of your
 * program's locking behavior. It is not sufficient to inspect code paths that
 * acquire multiple locks individually; both leftRight and rightLeft are
 * “reasonable” ways to acquire the two locks, they are just not compatible.
 * When it comes to locking, the left hand needs to know what the right hand is
 * doing.
 */
public class I01_LeftRightDeadlock {
	private final Object left = new Object();
	private final Object right = new Object();

	public void leftRight() {
		synchronized (left) {
			synchronized (right) {
				doSomething();
			}
		}
	}

	public void rightLeft() {
		synchronized (right) {
			synchronized (left) {
				doSomethingElse();
			}
		}
	}

	void doSomething() {
		System.out.println("1. doSomething ");
	}

	void doSomethingElse() {
		System.out.println("2. doSomethingElse ");
	}

	public static void main(String[] args) throws InterruptedException {
		I01_LeftRightDeadlock ob = new I01_LeftRightDeadlock();
		for (int i = 0; i < 500; i++) {
			System.out.println("Iteration : " + i);
			Thread left = new Thread(new Runnable() {
				@Override
				public void run() {
					ob.leftRight();

				}
			});

			Thread right = new Thread(new Runnable() {
				@Override
				public void run() {
					ob.rightLeft();

				}
			});

			left.start();
			right.start();
		}
	}
}