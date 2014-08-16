package transaction.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;

import org.springframework.transaction.PlatformTransactionManager;

import transaction.dao.AccountDao;
import transaction.dao.BookDao;
import transaction.dao.StockDao;
import transaction.entity.Account;
import transaction.entity.Book;
import transaction.entity.Department;
import transaction.entity.Employee;
import transaction.entity.EmployeeNoVer;
import transaction.entity.Stock;
import transaction.util.DBException;

public class BookServiceImpl /*implements BookService */{

	private BookDao bookDao;
	private AccountDao accountDao;
	private StockDao stockDao;
	private EntityManagerFactory entityManagerFactory;
	
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	public EntityManager getEntityManager(){
		return entityManagerFactory.createEntityManager();
		
	}

	public Book getBook(long id) {
		try {
			return bookDao.findById(id);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Stock getStock(long id) {
		try {
			return stockDao.findById(id);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Account getAccount(long id) {
		try {
			return accountDao.findById(id);
		} catch (DBException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void createAccount(String name, int initailBalance){
		try {
			Account account = new Account();
			account.setName(name);
			account.setBalance(initailBalance);
			accountDao.executePersist(account);
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteAccount(Account account){
		accountDao.executeRemove(account);
	}
	
	public long findAccountIdbyName(String name){
		StringBuffer query = new StringBuffer("Select id from Account a where a.name= :name");
		EntityManager em = this.getEntityManager();
		return (Long)em.createQuery(query.toString()).setParameter("name", name).getSingleResult();
	}
	
	public void addBook(String name, int price, int stockCount){
		try {
			Stock stock = new Stock();
			stock.setCount(stockCount);
			stock = stockDao.executePersist(stock);

			Book book = new Book();
			book.setName(name);
			book.setPrice(price);
			book.setStock(stockDao.findById(stock.getId()));
			book.setStock(stock);
			
			bookDao.executePersist(book);
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void addBookWithStock(String name, int price, int stockCount) {
		bookDao.addBookWithStock( name, price, stockCount);
	}
	
	public void deposit(long accountId, int amount) {
		try {
			Account account = accountDao.findById(accountId);
			account.setBalance(amount);
			accountDao.executePersist(account);
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}
	

	public void withdraw(long accountId, int amount) {
		try {
			Account account = accountDao.findById(accountId);
			int remain = account.getBalance() - amount;
			account.setBalance(remain);
			accountDao.executePersist(account);
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void addStock(Book book){
		try{
			bookDao.executePersist(book);
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}

	
	public void purchase(long accountId, long bookId) {
		try {
			Book book = bookDao.findById(bookId);
			book.getStock().setCount(book.getStock().getCount() - 1);
			bookDao.executePersist(book);

			Account account = accountDao.findById(accountId);
			account.setBalance(account.getBalance() - book.getPrice());
			accountDao.executePersist(account);
			
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}
	private Object returnNull(){
		return null;
	}
	
//	@Transactional
	public void purchaseBook(long accountId, long bookId) {
		try {
			EntityManager entityManager = this.getEntityManager();
			entityManager.getTransaction().begin();
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + "- [purchaseBook] Start");

			Book book = bookDao.findById(bookId);
			book.getStock().setCount(book.getStock().getCount() - 1);
			bookDao.executePersist(book);

			System.out.println(threadName
					+ "- Stock Updated, remaining stock is : "
					+ this.getBook(bookId).getStock().getCount());

			Object obj = returnNull();
			obj.toString();

			Account account = accountDao.findById(accountId);
			account.setBalance(account.getBalance() - book.getPrice());
			accountDao.executePersist(account);

			System.out.println(threadName + "- purchaseBook rolled back");
			entityManager.getTransaction().commit();
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public LockModeType checkLockMode(){
		EntityManager entityManager = this.getEntityManager();
		try {
			return entityManager.getLockMode(bookDao.findById(4));
		} catch (DBException e) {
			return null;
		}
	}
	
//	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public int checkStock(long bookId){
		EntityManager entityManager = this.getEntityManager();
		
		entityManager.getLockMode(entityManager);
		
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + "- [checkStock] Start");
		
		int count = this.getBook(bookId).getStock().getCount();
		
		System.out.println(threadName + "- Book stock is "+ count);
        this.sleep(threadName);
		entityManager.getTransaction().commit();
		return count;
	}

	private void sleep(String threadName) {
		System.out.println(threadName + "- Sleeping");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		System.out.println(threadName + "- Wake up");
	}
	
	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public StockDao getStockDao() {
		return stockDao;
	}

	public void setStockDao(StockDao stockDao) {
		this.stockDao = stockDao;
	}

	public Employee getEmployeeWithDelay(String name, int milisecond) {
			// TODO Auto-generated method stub
		return null;
	}

	public void updateEmployee(Employee emp) {
		// TODO Auto-generated method stub
		
	}

	public EmployeeNoVer getEmployeeWithDelayNoVer(String name, int milisecond) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateEmployeeNoVer(EmployeeNoVer emp) {
		// TODO Auto-generated method stub
		
	}

	public Employee getEmployee(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateEmployeeWithDelay(Employee emp, int sec) {
		// TODO Auto-generated method stub
		
	}

	public void updateEmployeeManual(Employee emp) {
		// TODO Auto-generated method stub
		
	}

	public void updateEmployeeWithDelayManual(Employee emp, int sec) {
		// TODO Auto-generated method stub
		
	}

	public void updateEmployeeNoVerWithDelay(EmployeeNoVer emp, int sec) {
		// TODO Auto-generated method stub
		
	}

	public EmployeeNoVer getEmployeeNoVer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerEmployeeManual(Employee emp) {
		// TODO Auto-generated method stub
		
	}

	public void registerEmployeeManualWithDelay(Employee emp, int sec) {
		// TODO Auto-generated method stub
		
	}

	public Employee getEmployeeWithLock(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public PlatformTransactionManager lockEmployee(Employee emp) {
		// TODO Auto-generated method stub
		return null;
	}

}
