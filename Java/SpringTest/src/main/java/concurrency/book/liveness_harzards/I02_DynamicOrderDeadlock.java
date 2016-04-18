package concurrency.book.liveness_harzards;

import java.util.concurrent.atomic.*;

/**
 * DynamicOrderDeadlock
 * <p/>
 * Dynamic lock-ordering deadlock
 *
 * Sometimes it is not obvious that you have sufficient control over lock
 * ordering to prevent deadlocks. Consider the harmless-looking code in Listing
 * 10.2 that transfers funds from one account to another. It acquires the locks
 * on both Account objects before executing the transfer, ensuring that the
 * balances are updated atomically and without violating invariants such as “an
 * account cannot have a negative balance”.
 * 
 * How can transferMoney deadlock? It may appear as if all the threads acquire
 * their locks in the same order, but in fact the lock order depends on the
 * order of arguments passed to transferMoney, and these in turn might depend on
 * external inputs. Deadlock can occur if two threads call transferMoney at the
 * same time, one transferring from X to Y, and the other doing the opposite:
 * 
 * With unlucky timing, A will acquire the lock on myAccount and wait for the
 * lock on yourAccount, while B is holding the lock on yourAccount and waiting
 * for the lock on myAccount.
 */
public class I02_DynamicOrderDeadlock {
	// Warning: deadlock-prone!
	public static void transferMoney(Account fromAccount, Account toAccount, DollarAmount amount)
			throws InsufficientFundsException {
		synchronized (fromAccount) {
			synchronized (toAccount) {
				if (fromAccount.getBalance().compareTo(amount) < 0)
					throw new InsufficientFundsException();
				else {
					fromAccount.debit(amount);
					toAccount.credit(amount);
				}
			}
		}
	}

	static class DollarAmount implements Comparable<DollarAmount> {
		// Needs implementation

		public DollarAmount(int amount) {
		}

		public DollarAmount add(DollarAmount d) {
			return null;
		}

		public DollarAmount subtract(DollarAmount d) {
			return null;
		}

		public int compareTo(DollarAmount dollarAmount) {
			return 0;
		}
	}

	static class Account {
		private DollarAmount balance;
		private final int acctNo;
		private static final AtomicInteger sequence = new AtomicInteger();

		public Account() {
			acctNo = sequence.incrementAndGet();
		}

		void debit(DollarAmount d) {
			balance = balance.subtract(d);
		}

		void credit(DollarAmount d) {
			balance = balance.add(d);
		}

		DollarAmount getBalance() {
			return balance;
		}

		int getAcctNo() {
			return acctNo;
		}
	}

	static class InsufficientFundsException extends Exception {
	}
}