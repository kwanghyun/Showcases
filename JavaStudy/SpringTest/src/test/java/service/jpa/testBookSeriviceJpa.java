package service.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import transaction.entity.Employee;
import transaction.service.BookService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:beans-test.xml")
@TransactionConfiguration(defaultRollback=true)
public class testBookSeriviceJpa extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private ApplicationContext applicationContext;
	private BookService bookService;
	
	@Before
	public void init(){
//		executeSqlScript("classpath:/bank.sql",true);
		bookService = (BookService)applicationContext.getBean("bookServiceJpa");
		bookService.createAccount("JANG", 50);
	}

	@Test
	public void testCreateAccount(){
		long testAccountId = bookService.findAccountIdbyName("JANG");
		assertEquals(bookService.getAccount(testAccountId).getBalance(),50);
	}
	
	@Test 
	public void testDepositAccount(){
		long testAccountId = bookService.findAccountIdbyName("JANG");
		bookService.deposit(testAccountId, 150);
		assertEquals(bookService.getAccount(testAccountId).getBalance(),200);
	}
	
	@Test 
	public void testUpdateEmployee(){
		Employee emp = new Employee();
		emp.setUsername("JANG");
		emp.setVacation(10);
		bookService.updateEmployee(emp);
		assertEquals(bookService.getEmployeeWithDelay("JANG", 0).getVacation(),10);
	}
	
	@After
	public void after(){

	}
	
}
