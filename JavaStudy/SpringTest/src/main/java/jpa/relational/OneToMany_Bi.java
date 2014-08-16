package jpa.relational;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import transaction.entity.Department;
import transaction.entity.Employee;
import transaction.service.BookService;

/*
 * 1. Persist @OneToMany(Not owned) side, insert both row with one line.
 * 
 * 2. There are two important points to remember when defining bidirectional one-to-many (or many-to-one) relationships:		
 * 	2.1 The many-to-one side is the owning side, so the join column is defined on that side.	
 * 	2.2 The one-to-many mapping is the inverse side, so the mappedBy element must be used.
 * 	
 * 	Failing to specify the mappedBy element in the @OneToMany annotation will cause the provider to treat it as a unidirectional 		
 * 	one-to-many relationship that is defined to use a join table. This is an easy mistake to make and should be the 		
 * 	first thing you look for if you see a missing table error with a name that has two entity names concatenated together.	
 * 
 * 
*/
public class OneToMany_Bi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		final BookService bookService = (BookService) context.getBean("bookServiceJpa");
		
		/*********
		 * Create
		 *********/
		Employee emp = new Employee();
		Department dept = new Department();
		emp.setUsername("JANG");
		emp.setVacation(10);
		emp.setPassword("password");
		dept.setName("SDP Solutions");
		dept.addEmployee(emp);
		dept.setBudget(100);
		emp.setDepartment(dept);
		bookService.registerDepartment(dept); //persist @OneToMany(Not owned) side, insert both row.
		
		/*********
		 * Delete - Deleting Not owned side's cascade all delete both tables.
		 *********/
//		long id = 2293760;
//		bookService.deleteDepartment(id);
	}

}
