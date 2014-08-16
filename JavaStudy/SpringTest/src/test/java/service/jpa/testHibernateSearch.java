package service.jpa;

import java.util.Date;
import java.util.List;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import transaction.dao.BookDao;
import transaction.dao.jpa.BookJpaDao;
import transaction.entity.Book;
import transaction.entity.Stock;
import transaction.service.BookService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
@TransactionConfiguration(defaultRollback = true)
public class testHibernateSearch {
	private ApplicationContext applicationContext;
	private BookService bookService;
//	private BookJpaDao BookJpaDao;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Before
	public void init() {
		bookService = (BookService) applicationContext.getBean("bookServiceJpa");
		BookJpaDao bookJpaDao = (BookJpaDao) applicationContext.getBean("bookJpaDao");
		Book book = new Book();
		book.setName("Spring Recipe");
		book.setPrice(50);
		Stock stock = new Stock();
		stock.setCount(10);
		book.setStock(stock);
		bookService.addBook(book);
		bookJpaDao.indexBookEntity(book);
	}

	@Test
	public void createBook() {
		Book book = bookService.getBook(1048576);
		assertEquals("Spring Recipe", book.getName());
	}

	@Test
	public void testfreeTextBookSearch(){
		String columns[] = new String[] {"name", "author", "publishedCompany", "description"};
		List<Book> bookList = bookService.freeTextBookSearch("Spring", columns, "name", false, 0, 0);
		for(Book book : bookList){
			System.out.println("NAME : "+book.getName());
		}
	}

	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

//	@Autowired
//	public void setBookDao(BookDao bookDao) {
//		this.bookDao = bookDao;
//	}

}
