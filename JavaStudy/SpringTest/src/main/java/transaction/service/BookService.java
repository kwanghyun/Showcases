package transaction.service;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.transaction.PlatformTransactionManager;

import transaction.entity.Account;
import transaction.entity.Book;
import transaction.entity.Department;
import transaction.entity.Employee;
import transaction.entity.EmployeeNoVer;
import transaction.entity.Stock;

public interface BookService{
	
	public Account getAccount(long id);
	public void createAccount(String name, int initialBalance);
	public void deleteAccount(Account account);
	public long findAccountIdbyName(String name);
	public void deposit(long accountId, int amount);
	public void withdraw(long accountId, int amount);
	
	public Book getBook(long id);
	public void addBook(Book book);
	public void addBookWithStock(String name, int price, int stockCount);
	public void deleteBook(long id);
	public void purchase(long accountId, long bookId);
	public void purchaseBook(long accountId, long bookId);
	public List<Book> searchBookWithCriteriaAPI();
	public Object getAggregateFunctionResult();
	
	public Stock getStock(long id);
	public int checkStock(long bookId);
	public Stock createStock(Stock stock);
	public void deleteStock(long id);
	
	public LockModeType checkLockMode();
	
	/*LockMode Test*/
	public PlatformTransactionManager lockEmployee(Employee emp);
	public Employee getEmployee(String name);
	public Employee getEmployeeWithDelay(String name, int milisecond);
	public void updateEmployee(Employee emp);
	public void updateEmployeeWithDelay(Employee emp, int sec, LockModeType lockMode);
	public void updateEmployeeManual(Employee emp);
	public void updateEmployeeWithDelayManual(Employee emp, int sec);
	public void registerEmployee(Employee emp);
	public void registerEmployeeManual(Employee emp);
	public void registerEmployeeManualWithDelay(Employee emp, int sec);
	public long getVacationTotal();
	public Employee updateEmployeeAndDepartment(Employee emp, int sec, LockModeType lockMode);
	
	public Department findDepartment(long id);
	public void registerDepartment(Department dept);
	public void updateDepartmentWithDelay(Department dept, int sec);
	public void deleteDepartment(long id);
	
	public EmployeeNoVer getEmployeeNoVer(String name);
	public EmployeeNoVer getEmployeeWithDelayNoVer(String name, int milisecond);
	public void updateEmployeeNoVer(EmployeeNoVer emp);
	public void updateEmployeeNoVerWithDelay(EmployeeNoVer emp, int sec);
	
	public List<Book> freeTextBookSearch(String searchWord, String[] columns, String orderby, 
			boolean reverseOrder, int startIndex, int maxIndex); 
}
