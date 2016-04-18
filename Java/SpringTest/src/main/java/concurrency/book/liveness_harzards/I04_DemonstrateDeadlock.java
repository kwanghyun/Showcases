package concurrency.book.liveness_harzards;

import java.util.*;

/**
 * DemonstrateDeadlock
 * <p/>
 * Driver loop that induces deadlock under typical conditions
 *
 * You may think we're overstating the risk of deadlock because locks are
 * usually held only briefly, but deadlocks are a serious problem in real
 * systems. A production application may perform billions of lock
 * acquire-release cycles per day. Only one of those needs to be timed just
 * wrong to bring the application to deadlock, and even a thorough load-testing
 * regimen may not disclose all latent deadlocks.[1] DemonstrateDeadlock in
 * Listing 10.4[2] deadlocks fairly quickly on most systems.
 * 
 */
public class I04_DemonstrateDeadlock {
	private static final int NUM_THREADS = 20;
	private static final int NUM_ACCOUNTS = 5;
	private static final int NUM_ITERATIONS = 1000000;

	public static void main(String[] args) {
		final Random rnd = new Random();
		final I02_DynamicOrderDeadlock.Account[] accounts = new I02_DynamicOrderDeadlock.Account[NUM_ACCOUNTS];

		for (int i = 0; i < accounts.length; i++)
			accounts[i] = new I02_DynamicOrderDeadlock.Account();

		class TransferThread extends Thread {
			public void run() {
				for (int i = 0; i < NUM_ITERATIONS; i++) {
					int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
					int toAcct = rnd.nextInt(NUM_ACCOUNTS);
					I02_DynamicOrderDeadlock.DollarAmount amount = new I02_DynamicOrderDeadlock.DollarAmount(
							rnd.nextInt(1000));
					try {
						I02_DynamicOrderDeadlock.transferMoney(accounts[fromAcct], accounts[toAcct], amount);
					} catch (I02_DynamicOrderDeadlock.InsufficientFundsException ignored) {
					}
				}
			}
		}
		for (int i = 0; i < NUM_THREADS; i++)
			new TransferThread().start();
	}
}