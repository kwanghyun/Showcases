package concurrency.advance;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import concurrency.Basic.ReadWriteLock;

/*Java Reentrant Locks*/

//Lock – the simplest case of a lock which can be acquired and released
//ReadWriteLock – a lock implementation that has both read and write lock types 
//– multiple read locks can be held at a time unless the exclusive write lock is held

//Java provides two implementations of these locks that we care about 
//– both of which are reentrant (this just means a thread can reacquire 
//the same lock multiple times without any issue).
//
//ReentrantLock – as you’d expect, a reentrant Lock implementation
//ReentrantReadWriteLock – a reentrant ReadWriteLock implementation


/*
 * In the example above, we can have hundreds of threads reading the same value 
 * at once with no issue, and we only block readers when we acquire the write lock.
 *  
 * Remember that: many readers can acquire the read lock at the same time, 
 * but there are no readers OR writers allowed when acquiring the write lock.*/

/*
 * Whenever you’re dealing with concurrency, there are dangers. Always remember the following:
 *
 * Release all locks in finally block. This is rule 1 for a reason.
 *
 * Beware of thread starvation! The fair setting in ReentrantLocks may be useful if you have 
 * many readers and occasional writers that you don’t want waiting forever. It’s possible 
 * a writer could wait a very long time (maybe forever) if there are constantly read locks held by other threads.
 * 
 * Use synchronized where possible. You will avoid bugs and keep your code cleaner.
 * 
 * Use tryLock() if you don’t want a thread waiting indefinitely to acquire a lock – 
 * this is similar to wait lock timeouts that databases have.*/

public class Calculator {
	private int calculatedValue;
	private int value;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public void calculate(int value) {
		lock.writeLock().lock();
		try {
			this.value = value;
			this.calculatedValue = doMySlowCalculation(value);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public int getCalculatedValue() {
		lock.readLock().lock();
		try {
			return calculatedValue;
		} finally {
			lock.readLock().unlock();
		}
	}

	public int getValue() {
		lock.readLock().lock();
		try {
			return this.value;
		} finally {
			lock.readLock().unlock();
		}
	}

	private int doMySlowCalculation(int value2) {
		try {
			Thread.sleep(value2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value2 * 2;
	}
}