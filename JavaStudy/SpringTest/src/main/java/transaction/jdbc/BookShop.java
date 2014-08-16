package transaction.jdbc;

import java.util.List;

import transaction.util.DBException;

public interface BookShop {
	public void purchase(long bookId, String username);
	public int getBookPrice(long bookId);
	public int getStockId(long bookId);
	public int getStockCount(long stockId);
	public int getStockCountOfBook(long bookId);
	public int getAccount(String username);
	public void checkout(List<Long> bookIds, String userName);
	public void setAccount(String username, int amount);
	public void setStock(long stockId, int amount);
	
	public void purchase_requriedNew(long bookId, String username) throws DBException ;
	public void checkout_requiredNew(List<Long> bookIds, String userName) throws DBException ;
	
	public void setStock4Isolation(long stockId, int amount);
	public void setStock4IsolationCommitMode(long stockId, int amount);
	public int getStockCount4Isolation_READ_UNCOMMITTED(long bookId);
	public int getStockCount4Isolation_READ_COMMITTED(long stockId);
	public int getStockCount4Isolation_REPEATABLE_READ(long stockId);
	
}
