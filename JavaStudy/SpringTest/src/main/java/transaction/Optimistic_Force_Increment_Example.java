package transaction;

import javax.persistence.LockModeType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import transaction.entity.Department;
import transaction.entity.Employee;
import transaction.service.BookService;

public class Optimistic_Force_Increment_Example {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		final BookService bookService = (BookService) context.getBean("bookServiceJpa");
		
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				Department dept = new Department();
				dept.setId(2523136);
				dept.setName("SDP Solutions");
				dept.setBudget(1);
				bookService.updateDepartmentWithDelay(dept, 2);
				
				System.out.println("[Thread 1] End of Transaction ");			
			}
		}, "Thread 1");
		
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				String testName = "JANG";
				Employee emp = new Employee();//bookService.getEmployee("JANG");
				emp.setId(2555904);
				emp.setUsername(testName);
				emp.setPassword("paasword");
				emp.setVacation(1);
				Department dept = new Department();
				dept.setBudget(2);
				emp.setDepartment(dept);
				try{
					
//					bookService.updateEmployeeAndDepartment(emp, 2, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					bookService.updateEmployeeAndDepartment(emp, 2, LockModeType.PESSIMISTIC_READ);
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
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		thread2.start();

	}
}
