package transaction.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import transaction.dao.jpa.AccountJpaDao;
import transaction.dao.jpa.BookJpaDao;
import transaction.dao.jpa.DepartmentDao;
import transaction.dao.jpa.EmployeeDao;
import transaction.dao.jpa.EmployeeDaoNoVer;
import transaction.dao.jpa.StockJpaDao;
import transaction.entity.Account;
import transaction.entity.Book;
import transaction.entity.Department;
import transaction.entity.Employee;
import transaction.entity.EmployeeNoVer;
import transaction.entity.Stock;

@Service
//@Transactional
public class BookServiceJpaImpl implements BookService {
	
	private BookJpaDao bookDao;
	private AccountJpaDao accountDao;
	private StockJpaDao stockDao;
	private EmployeeDao employeeDao;
	private EmployeeDaoNoVer employeeDaoNoVer;
	private DepartmentDao departmentDao;
	
	private PlatformTransactionManager transactionManager;
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}
	
	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Transactional(readOnly=true)
	public Book getBook(long id) {
		return bookDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Stock getStock(long id) {
		return stockDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Account getAccount(long id) {
		return accountDao.get(id);
	}
	
	@Transactional(readOnly=false)
	public void createAccount(String name, int initialBalance) {
		Account account = new Account();
		account.setName(name);
		account.setBalance(initialBalance);
		accountDao.save(account);
	}
	
	public void deleteAccount(Account account) {
		accountDao.delete(account);
	}
	
	@Transactional(readOnly=true)
	public long findAccountIdbyName(String name) {
		return accountDao.findByName(name).getId();
	}
	
	public void deposit(long accountId, int amount) {
		Account account = accountDao.get(accountId);
		account.setBalance(account.getBalance()+amount);
		accountDao.save(account);
	}
	
	public void withdraw(long accountId, int amount) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional(readOnly=false)
	public void addBook(Book book) {
		bookDao.save(book);
	}
	
	public void addBookWithStock(String name, int price, int stockCount) {
		// TODO Auto-generated method stub
		
	}
	
	public void purchase(long accountId, long bookId) {
		// TODO Auto-generated method stub
		
	}
	
	public void purchaseBook(long accountId, long bookId) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional(readOnly=true)
	public List<Book> searchBookWithCriteriaAPI(){
		return bookDao.criteriaSearch();
	}
	
	@Transactional(readOnly=true)
	public Object getAggregateFunctionResult(){
		return bookDao.getAggregateFunctionResult();
	}
	
	@Transactional(readOnly=false)
	public void deleteBook(long id){
		Book book = getBook(id);
		bookDao.delete(book);
	}
	
	public Stock createStock(Stock stock) {
		return stockDao.merge(stock);
	}

	public int checkStock(long bookId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Transactional(readOnly=false)
	public void deleteStock(long id){
		Stock stock = getStock(id);
		stockDao.delete(stock);
	}
	
	public LockModeType checkLockMode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public BookJpaDao getBookDao() {
		return bookDao;
	}
	
	@Transactional(readOnly=true)
	public Employee getEmployee(String name) {
		Employee employee = employeeDao.getEmployeeByName(name);
		return employee;
	}
	
	@Transactional(readOnly=true)
	public Employee getEmployeeWithDelay(String name, int milisecond) {
		Employee employee = employeeDao.getEmployeeByName(name);
//		Employee employee = employeeDao.getEmployeeByNameWithReadLock(name);
		
		try {
			if(milisecond>0) Thread.sleep(milisecond);
		} catch (InterruptedException e) {}
		return employee;
	}
	
	
	@Transactional
	public void updateEmployee(Employee emp){
		Employee employee = employeeDao.getEmployeeByName(emp.getUsername());
		employee.setVacation(emp.getVacation());
		employee.setPassword(emp.getPassword());
		employee.setUsername(emp.getUsername());
		String threadName = Thread.currentThread().getName();
		System.out.println("["+threadName + "] -  updateEmployee Version is : " + employee.getVersion());
		employeeDao.save(employee);
	}
	
	@Transactional
	public Employee updateEmployeeAndDepartment(Employee emp, int sec, LockModeType lockMode) {
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "] -  updateEmployeeAndDepartment");
		employeeDao.updateEmployeeAndDepartment(emp, sec, lockMode);
		return null;
	}

	@Transactional
	public void registerDepartment(Department dept) {
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "] -  register Department");
		departmentDao.save(dept);
	}
	
	@Transactional(readOnly=true)
	public Department findDepartment(long id){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "] -  findDepartment");
		return departmentDao.get(id);
	}

	@Transactional
	public void deleteDepartment(long id) {
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "] - deleteDepartment");
		departmentDao.delete(findDepartment(id));
	}
	
	@Transactional
	public void updateDepartmentWithDelay(Department dept, int sec) {
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "] - updateDepartment");
		departmentDao.updateDepartmentWithDelay(dept, sec);
	}
	
	@Transactional
	public void registerEmployee(Employee emp) {
		String threadName = Thread.currentThread().getName();

		Employee employee = new Employee();
		employee.setVacation(emp.getVacation());
		employee.setPassword(emp.getPassword());
		employee.setUsername(emp.getUsername());
		System.out.println("[" + threadName + "] -  registerEmployee Version is : " + employee.getVersion());
		employeeDao.save(employee);
	}

//	@Transactional
	public void registerEmployeeManual(Employee emp) {
		String threadName = Thread.currentThread().getName();
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		System.out.println("["+threadName+"] Propagation Behavior : " + def.getPropagationBehavior());
		System.out.println("["+threadName+"] Isolation Level : " + def.getIsolationLevel());
		System.out.println("["+threadName+"] New Transation : " + status.isNewTransaction());
		try {
			Employee employee = new Employee();
			employee.setVacation(emp.getVacation());
			employee.setPassword(emp.getPassword());
			employee.setUsername(emp.getUsername());
			System.out.println("[" + threadName +"] -  registerEmployee Version is : " + employee.getVersion());
			employeeDao.save(employee);
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			transactionManager.rollback(status);
			System.out.println(e.getMessage());
		}
	}
	
//	@Transactional
	public void registerEmployeeManualWithDelay(Employee emp, int sec) {
		String threadName = Thread.currentThread().getName();
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		System.out.println("["+threadName+"] Propagation Behavior : " + def.getPropagationBehavior());
		System.out.println("["+threadName+"] Isolation Level : " + def.getIsolationLevel());
		System.out.println("["+threadName+"] New Transation : " + status.isNewTransaction());
		try {
			Employee employee = new Employee();
			employee.setVacation(emp.getVacation());
			employee.setPassword(emp.getPassword());
			employee.setUsername(emp.getUsername());
			System.out.println("[" + threadName +"] -  registerEmployee Version is : " + employee.getVersion());
			employeeDao.save(employee);
			
			try {
				Thread.sleep(sec*1000);
			} catch (InterruptedException e) {}
			
			transactionManager.commit(status);
			
		} catch (DataAccessException e) {
			transactionManager.rollback(status);
			System.out.println(e.getMessage());
		}
	}
	
	public void updateEmployeeManual(Employee emp) {
		String threadName = Thread.currentThread().getName();
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		System.out.println("["+threadName+"] Propagation Behavior : " + def.getPropagationBehavior());
		System.out.println("["+threadName+"] Isolation Level : " + def.getIsolationLevel());
		System.out.println("["+threadName+"] New Transation : " + status.isNewTransaction());
//		print(def);
		try {
			Employee employee = employeeDao.getEmployeeByName(emp.getUsername());
			employee.setVacation(emp.getVacation());
			employee.setPassword(emp.getPassword());
			employee.setUsername(emp.getUsername());

			System.out.println("[" + threadName +"] -  updateEmployee Version is : " + employee.getVersion());
			employeeDao.save(employee);
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			transactionManager.rollback(status);
			System.out.println(e.getMessage());
		}
	}

	public void updateEmployeeWithDelayManual(Employee emp, int sec){
		String threadName = Thread.currentThread().getName();
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		System.out.println("["+threadName+"] Propagation Behavior : " + def.getPropagationBehavior());
		System.out.println("["+threadName+"] Isolation Level : " + def.getIsolationLevel());
		System.out.println("["+threadName+"] New Transation : " + status.isNewTransaction());

		try{
		Employee employee = employeeDao.getEmployeeByName(emp.getUsername());
		employee.setVacation(employee.getVacation()+emp.getVacation());
		employee.setPassword(emp.getPassword());
		employee.setUsername(emp.getUsername());
		
		System.out.println("["+threadName + "] -  updateEmployee Version is : " + employee.getVersion());
		employeeDao.save(employee);
		
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {}
		System.out.println("["+threadName + "] -  waked up");
		System.out.println("["+threadName + "] -  updateEmployee Version is : " + employee.getVersion());
		
		transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			System.out.println(e.getMessage());
		}
	}


	public PlatformTransactionManager lockEmployee(Employee emp){
		String threadName = Thread.currentThread().getName();
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		System.out.println("["+threadName+"] Propagation Behavior : " + def.getPropagationBehavior());
		System.out.println("["+threadName+"] Isolation Level : " + def.getIsolationLevel());
		System.out.println("["+threadName+"] New Transation : " + status.isNewTransaction());
		employeeDao.lockEmployee(getEmployee(emp.getUsername()));
		return transactionManager;
	}
	
	public void releaseEmployee(PlatformTransactionManager emp){

	}
	
	@Transactional(readOnly=true)
	public long getVacationTotal(){
		return employeeDao.getAllEmployeesVacation();
	}
	
	private void print(TransactionDefinition def ){
		System.out.println("[def.ISOLATION_DEFAULT] = " + def.ISOLATION_DEFAULT); // -1
		System.out.println("[def.ISOLATION_READ_COMMITTED] = " + def.ISOLATION_READ_COMMITTED); //2
		System.out.println("[def.ISOLATION_READ_UNCOMMITTED] = " + def.ISOLATION_READ_UNCOMMITTED); //1
		System.out.println("[def.PROPAGATION_MANDATORY] = " + def.PROPAGATION_MANDATORY); //2
		System.out.println("[def.PROPAGATION_REQUIRED] = " + def.PROPAGATION_REQUIRED); //0
		System.out.println("[def.PROPAGATION_REQUIRES_NEW] = " + def.PROPAGATION_REQUIRES_NEW); //3
	}
	
	@Transactional
	public void updateEmployeeWithDelay(Employee emp, int sec, LockModeType lockMode){
		String threadName = Thread.currentThread().getName();
		
//		Employee updatedEmp = employeeDao.updateEmployee(emp, sec, lockMode);
		employeeDao.updateEmployee(emp, sec, lockMode);
		
		System.out.println("["+threadName + "] -  waked up");
//		System.out.println("["+threadName + "] -  updateEmployee Version is : " + updatedEmp.getVersion());
	}

	@Transactional(readOnly=true)
	public EmployeeNoVer getEmployeeWithDelayNoVer(String name, int milisecond) {
		EmployeeNoVer employeeNV = employeeDaoNoVer.getEmployeeByName(name);
		try {
			if(milisecond>0) Thread.sleep(milisecond);
		} catch (InterruptedException e) {}
		return employeeNV;
	}
	
	@Transactional
	public void updateEmployeeNoVer(EmployeeNoVer emp){
		EmployeeNoVer employeeNV = employeeDaoNoVer.getEmployeeByName(emp.getUsername());
		employeeNV.setVacation(emp.getVacation());
		employeeNV.setPassword(emp.getPassword());
		employeeNV.setUsername(emp.getUsername());
		String threadName = Thread.currentThread().getName();
		System.out.println("["+threadName + "] -  updateEmployee");
		employeeDaoNoVer.save(employeeNV);
	}
	
	@Transactional
	public void updateEmployeeNoVerWithDelay(EmployeeNoVer emp, int sec){
		EmployeeNoVer employeeNV = employeeDaoNoVer.getEmployeeByName(emp.getUsername());
		employeeNV.setVacation(emp.getVacation());
		employeeNV.setPassword(emp.getPassword());
		employeeNV.setUsername(emp.getUsername());
		String threadName = Thread.currentThread().getName();
		System.out.println("["+threadName + "] -  updateEmployee");
		employeeDaoNoVer.save(employeeNV);
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {}
		System.out.println("["+threadName + "] -  waked up");
	}

	@Transactional(readOnly=true)
	public EmployeeNoVer getEmployeeNoVer(String name){
		EmployeeNoVer employee = employeeDaoNoVer.getEmployeeByName(name);
		return employee;
	}
	
	@Transactional(readOnly=true)
	public List<Book> freeTextBookSearch(String searchWord, String[] columns, String orderBy, 
			boolean reverseOrder, int startIndex, int maxResult) {
//		bookDao.indexAllItems();
		List<Book> bookList = bookDao.freeTextSeachEntities(searchWord, columns, orderBy, reverseOrder, startIndex, maxResult);
		return bookList;
	}

	@Autowired
	public void setBookDao(BookJpaDao bookDao) {
		this.bookDao = bookDao;
	}

	public AccountJpaDao getAccountDao() {
		return accountDao;
	}

	@Autowired
	public void setAccountDao(AccountJpaDao accountDao) {
		this.accountDao = accountDao;
	}

	public StockJpaDao getStockDao() {
		return stockDao;
	}

	@Autowired
	public void setStockDao(StockJpaDao stockDao) {
		this.stockDao = stockDao;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	@Autowired
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public EmployeeDaoNoVer getEmployeeDaoNoVer() {
		return employeeDaoNoVer;
	}

	@Autowired
	public void setEmployeeDaoNoVer(EmployeeDaoNoVer employeeDaoNoVer) {
		this.employeeDaoNoVer = employeeDaoNoVer;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	
	@Autowired
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}



	
}