package transaction;

import javax.persistence.OptimisticLockException;

import org.hibernate.StaleObjectStateException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import transaction.entity.Department;
import transaction.entity.Employee;
import transaction.service.BookService;

/***********************************************************
 * Problem : (If entity is not Relational mode, OptimisticLockException won't be throwed by entityManager) 
 * 1. LockModeType.OPTIMISTIC(READ) is set when updating a row with entityMangaer.persist()
 * 2. LockModeType.OPTIMISTIC_FORCE_INCREMENT(WRITE) is set when inserting a row with entityMangaer.persist()
 * 3. entityMnager.merge() needs entity's Id to be persisted. but not persist()
 *  
 *  Key Points 
 * 1. Optimistic Locking : Two transactions can access the same data simultaneously, but to prevent collisions, 
 *  	a check is made to detect any changes made to the data since the data was last read.
 *   	1.1 Pros : No database lock are held. This can result in better scalability than for pessimistic locking.
 *   	1.2 Cons : User or application must refresh and retry failed updates.
 *      
 * 2. Pessimistic Locking : A transaction that reads the data lock it. Another transaction cannot change the data
 * 	until the first transaction commits the read.
 * 	This ensure that transaction do not update the same entity at the same time. This can simplify application
 * 	code, but it limits concurrent access to the data, something that can cause poor scalability and may cause
 * 	deadlocks.
 * 
 * 3. An entity represents a table in a relational database, with each entity instance corresponding to
 * 	a row in that table.
 * 
 * 4. Lock Modes
 * 	4.1 OPTIMISTIC(READ) - Locks the entity and before transaction commits, check the entity's version to
 * 		determine if it has been updated since the entity was last read. If the version has been updated,
 * 		the entity manager throws an OptimisticLockException and roll back the transaction.
 * 	4.2 OPTIMISTIC_FORCE_INCREMENT(WRITE) - Same as OPTIMISTIC(READ) mode. But it also 
 * 		updates the entity's version column.
 * 	4.3 PESSIMISTIC_READ - The entity manager locks the entity as soon as a transaction reads it. 
 * 		The lock is held until the transaction completes. This lock mode is used when you want to
 * 		query data using repeatable-read semantics. In other words, you want to ensure that the data
 * 		is not updated between successive reads. This lock mode does not block other transaction from 
 * 		reading the data
 * 	4.4 PESSMISTIC_WRITE - The entity manager locks the entity as soon as a transaction update it.
 * 	4.5 PESSIMISTIC_FORCE_INCREMENT - The entity manager locks the entity when a transaction
 * 		reads it. It also increments the entity's version when the transaction ends, even if the entity is
 * 		not modifed. 
 ***********************************************************/ 
public class OptimisticLockException_Example {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		final BookService bookService = (BookService) context.getBean("bookServiceJpa");
		
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				String testName = "JANG";
				long testId = 2555904;
				Employee emp = new Employee();
				emp.setId(testId);
				emp.setUsername(testName);
				emp.setPassword("paasword");
				emp.setVacation(1);
				
				bookService.updateEmployeeWithDelay(emp,2, null);
				
				Employee afterEmp= bookService.getEmployee(testName);
				System.out.println("[Thread 1] After Vaction : " + afterEmp.getVacation());
				System.out.println("[Thread 1] After Version : " + afterEmp.getVersion());
				System.out.println("[Thread 1] End of Transaction ");				
			}
		}, "Thread 1");
		
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				String testName = "JANG";
				long testId = 2555904;
				Employee emp = new Employee();
				emp.setId(testId);
				emp.setUsername(testName);
				emp.setPassword("paasword");
				emp.setVacation(2);
				
				try{
//					bookService.updateEmployeeManual(emp);
					bookService.updateEmployeeWithDelay(emp,2, null);
//					bookService.updateEmployeeWithDelayManual(emp,2);
				}catch(HibernateOptimisticLockingFailureException e){
					System.out.println("@@@@OptimisticLockingFailureException : "+ e.getMessage());
				}
				
				Employee afterEmp= bookService.getEmployee(testName);
				System.out.println("[Thread 2] After Vaction : " + afterEmp.getVacation());
				System.out.println("[Thread 2] After Version : " + afterEmp.getVersion());
				System.out.println("[Thread 2] End of Transaction ");			
			}
		}, "Thread 2");
		
		thread1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		thread2.start();

	}

}
