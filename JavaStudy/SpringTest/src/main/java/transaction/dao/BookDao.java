package transaction.dao;

import javax.persistence.EntityManager;

import transaction.entity.Book;
import transaction.entity.Stock;

public class BookDao extends GenericDAO<Book>{
	public BookDao(){
		super();
	}
	/*object references an unsaved transient instance - save the transient 
	instance before flushing: transaction.Book.stock -> transaction.Stock*/
	public void addBookWithStock(String name, int price, int stockCount){
		EntityManager entityMangaer = this.getEntityManager();

		Stock stock = new Stock();
		stock.setCount(stockCount);

		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStock(stock);
		entityMangaer.getTransaction().begin();
		entityMangaer.persist(book);
		entityMangaer.getTransaction().commit();
	}
}
