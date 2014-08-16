package concurrency.semaphore;

import java.util.concurrent.Semaphore;

//The classic synchronization problem of protecting critical code execution 
//can be solved using semaphores. In this situation, you have some critical 
//code section that must not be interrupted once it starts. Also, the code must be 
//executed simultaneously by multiple threads only a set number of times. 
//(By simultaneously, I'm referring to execution inside multiple parallel threads of execution.)
public class MySemaphoreTest2 extends Thread {

	private int threadId;
	private Semaphore semaphore;

	public MySemaphoreTest2(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	private int random(int n) {
		return (int) Math.round(n * Math.random());
	}

	private void busyCode() {
		int sleepPeriod = random(500);
		try {
			sleep(sleepPeriod);
		} catch (InterruptedException e) {
		}
	}

	private void noncriticalCode() {
		System.out.println("[" + Thread.currentThread().getName()
				+ "] noncritical - START");
		busyCode();
		System.out.println("[" + Thread.currentThread().getName()
				+ "] noncritical - END");
	}

	private void criticalCode() {
		System.out.println("[" + Thread.currentThread().getName()
				+ "] critical - START");
		busyCode();
		System.out.println("[" + Thread.currentThread().getName()
				+ "] critical - END");
	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			try {
				semaphore.acquire();
				criticalCode();
				semaphore.release();
			} catch (InterruptedException e) {
				System.out.println("Exception " + e.toString());
			}
		}
		for (int i = 0; i < 3; i++) {
			noncriticalCode();
		}
	}

	public static void main(String[] args) {
		final int numberOfProcesses = 3;
		final int numberOfPermits = 2;

		Semaphore semaphore = new Semaphore(numberOfPermits, true);
		MySemaphoreTest2 pool[] = new MySemaphoreTest2[numberOfProcesses];

		for (int i = 0; i < numberOfProcesses; i++) {
			pool[i] = new MySemaphoreTest2(semaphore);
			pool[i].setThreadId(pool[i].hashCode());
			pool[i].start();
		}
	}

}
