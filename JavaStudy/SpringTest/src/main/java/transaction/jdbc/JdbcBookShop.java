package transaction.jdbc;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import transaction.jdbc.BookShop;
import transaction.util.DBException;

public class JdbcBookShop extends JdbcDaoSupport implements BookShop {

	@Transactional
	public void purchase(long bookId, String username){
		
		int count = getStockCountOfBook(bookId);
		if(count<=0) 
			throw new RuntimeException("Not Enough Stock");
		int stockId = getStockId(bookId);
		getJdbcTemplate().update(
				"UPDATE STOCK SET COUNT = COUNT -1 WHERE ID = ?",
				new Object[] { stockId });

		int balance = getAccount(username);
		int price = getBookPrice(bookId);
		if(balance - price <0) 
			throw new RuntimeException("Not Enough Money");

		getJdbcTemplate().update(
				"UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE NAME = ?",
				new Object[] { price, username });
	}
	
	@Transactional
	public void checkout(List<Long> bookIds, String userName){
		for(long bookId : bookIds){
			purchase(bookId, userName);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW) 
	public void purchase_requriedNew(long bookId, String username)  throws DBException{
		
		int count = getStockCountOfBook(bookId);
		if(count<=0) 
			throw new DBException("Not Enough Stock");
		int stockId = getStockId(bookId);
		getJdbcTemplate().update(
				"UPDATE STOCK SET COUNT = COUNT -1 WHERE ID = ?",
				new Object[] { stockId });

		int balance = getAccount(username);
		int price = getBookPrice(bookId);
		if(balance - price <0) 
			throw new DBException("Not Enough Money");

		getJdbcTemplate().update(
				"UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE NAME = ?",
				new Object[] { price, username });
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = DBException.class)
	public void checkout_requiredNew(List<Long> bookIds, String userName) throws DBException {
		for(long bookId : bookIds){
			purchase_requriedNew(bookId, userName);
		}
	}
	
	public int getBookPrice(long bookId){
		return getJdbcTemplate().queryForInt(
				"SELECT PRICE FROM BOOK WHERE ID=?", 
				new Object[] { bookId });
	}
	
	public int getStockId(long bookId){
		return getJdbcTemplate()
				.queryForInt("SELECT STOCK_ID FROM BOOK WHERE ID=?",
				new Object[] { bookId });
	}
	
	public int getStockCount(long stockId){
		return getJdbcTemplate()
				.queryForInt("SELECT COUNT FROM STOCK WHERE ID=?",
				new Object[] { stockId });
	}

	public int getStockCountOfBook(long bookId){
		return getJdbcTemplate()
				.queryForInt("SELECT st.COUNT FROM BOOK bk JOIN STOCK st ON bk.STOCK_ID = st.ID WHERE bk.ID=?",
				new Object[] { bookId });
	}
	
	public int getAccount(String username){
		return getJdbcTemplate()
				.queryForInt("SELECT BALANCE FROM ACCOUNT WHERE NAME=?",
				new Object[] { username });
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void setAccount(String username, int amount){
		getJdbcTemplate().update(
				"UPDATE ACCOUNT SET BALANCE = ? WHERE NAME = ?",
				new Object[] { amount, username });
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void setStock(long stockId, int amount){
		getJdbcTemplate().update(
				"UPDATE STOCK SET COUNT = ? WHERE ID = ?",
				new Object[] { amount, stockId });
	}
	
	@Transactional
	public void setStock4Isolation(long stockId, int amount){
		String threadName = Thread.currentThread().getName();
		System.out.println("["+threadName + "] - Prepare to Set book stock");
		getJdbcTemplate().update(
				"UPDATE STOCK SET COUNT = ? WHERE ID = ?",
				new Object[] { amount, stockId });
		
		System.out.println("["+threadName + "] - Stock updated but not committed, count is : " + amount);
		sleep(threadName);
		
		System.out.println("["+threadName + "] - "+"Transaction Rollback");
		throw new RuntimeException("Transaction Rollback");
	}
	
	@Transactional
	public void setStock4IsolationCommitMode(long stockId, int amount){
		String threadName = Thread.currentThread().getName();
		System.out.println("["+threadName + "] - Prepare to Set book stock");
		getJdbcTemplate().update(
				"UPDATE STOCK SET COUNT = ? WHERE ID = ?",
				new Object[] { amount, stockId });
		
		System.out.println("["+threadName + "] - Stock updated but not committed, count is : " + amount);
		System.out.println("["+threadName + "] - "+"Transaction Commit successful");
	}
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public int getStockCount4Isolation_READ_UNCOMMITTED(long stockId){
		String threadName = Thread.currentThread().getName();
		System.out.println("["+threadName + "] - Prepare to Check book stock");
		
		int count = getJdbcTemplate()
				.queryForInt("SELECT COUNT FROM STOCK WHERE ID=?",
				new Object[] { stockId });
		
		System.out.println("["+threadName + "] - current Stock count is : " + count);
		System.out.println("@@Oops!!!@@ Dirty Read - Thread 1 change didn't commited yet");
		sleep(threadName);
		
		return count;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int getStockCount4Isolation_READ_COMMITTED(long stockId){
		String threadName = Thread.currentThread().getName();
		System.out.println("["+threadName + "] - Prepare to Check book stock");
		
		int count = getJdbcTemplate()
				.queryForInt("SELECT COUNT FROM STOCK WHERE ID=?",
				new Object[] { stockId });
		
		System.out.println("["+threadName + "] - current Stock count is : " + count);
		sleep(threadName);
		
		return count;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public int getStockCount4Isolation_REPEATABLE_READ(long stockId){
		String threadName = Thread.currentThread().getName();
		System.out.println("["+threadName + "] - Prepare to Check book stock");
		
		int count = getJdbcTemplate()
				.queryForInt("SELECT COUNT FROM STOCK WHERE ID=?",
				new Object[] { stockId });
		
		System.out.println("["+threadName + "] - current Stock count is : " + count);
		shortSleep(threadName);
		
		return count;
	}
	
	private void sleep(String threadName) {
		System.out.println("[" + threadName+ "] - Sleeping");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		System.out.println("[" + threadName+ "] - Wake up");
	}
	
	private void shortSleep(String threadName) {
		System.out.println("[" + threadName+ "] - Short Sleeping");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.out.println("[" + threadName+ "] - Short Sleep Wake up");
	}
}
