package transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import transaction.jdbc.BookShop;

public class NON_REPEATABLE_READ_Example {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		final BookShop bookShop = (BookShop) context.getBean("bookShop");
		
		/* Isolation : READ_UNCOMMITTED 
		 * Problem : Dirty Read - T1 read a filed that has been updated by T2 but not yet committed.
		 * 
		 * Isolation : READ_COMMITTED - Read only committed, acquire update lock 
		 * 	on a row that was updated but not yet committed.(Default most Database) 
		 * Problem : Non-repeatable read - T1 read a filed and then T2 updated filed. Later, 
		 * 	T1 read the same filed again. the value will be different
		 * 
		 * Isolation : REPEATABLE_READ - For the duration of this transaction, updates made by 
		 * 	other transactions to this field are prohibited, acquire a read lock on a row that was 
		 * 	read but not yet committed.
		 * Problem : Phantom read - T1 reads some rows from table and then T2 insert new rows 
		 * 	into the table. Later, T1 reads the same table again, there will be additional rows.
		 * Problem : Lost updates - T1 and T2, they both select a row for update, 
		 * 	and based on the state of that row, make an update to it. Thus, one overwrites the 
		 * 	other when the second transaction to commit should have waited until the first one
		 * 	committed before performing its selection.
		 * 
		 * Isolation : SERIALIZABLE - For the duration of this transaction, inserts,updates, and 
		 * 	deletes made by other transactions to this table are prohibited.
		 * Problem : concurrency problems can be avoided, but the performance will be low.
		 * */
		
		/*Stock count Initialization*/
		bookShop.setStock(1, 5);
		
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				bookShop.getStockCount4Isolation_READ_COMMITTED(1);
				System.out.println("[Thread 1] End - final count : " + bookShop.getStockCount(1));
				System.out.println("@@Oops!!!@@ Non-repeatable read");			
			}
		}, "Thread 1");

		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				try {
					bookShop.setStock4IsolationCommitMode(1, 10);
				} catch (RuntimeException e) {}
			}
		}, "Thread 2");

		thread1.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		thread2.start();
	}
}
