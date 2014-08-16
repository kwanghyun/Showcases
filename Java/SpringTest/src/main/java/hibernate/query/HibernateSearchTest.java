package hibernate.query;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import transaction.dao.jpa.BookJpaDao;
import transaction.entity.Book;
import transaction.entity.Employee;
import transaction.entity.EmployeeNoVer;
import transaction.entity.Stock;
import transaction.service.BookService;

/*
 * Caution : Changing database directly for testing purpose won't work
 * Every entities Need to indexed by fullTextEntityManager.index() call 
*/
public class HibernateSearchTest {
	
	private static void  addSeedData(BookService bookService){
		/*Create seed data*/
		Book book = new Book();
		book.setName("Spring Hibernate1");
		book.setPrice(40);
		book.setAuthor("author1");
		book.setDescription("JAVA");
		book.setPublishedDate(new Date());
		book.setPublishedCompany("Apress");
		Stock stock = new Stock();
		stock.setCount(10);
		book.setStock(stock);
		bookService.addBook(book);

//		Book book1 = new Book();
//		book1.setName("Spring Integration");
//		book1.setPrice(35);
//		book1.setAuthor("author3");
//		book1.setDescription("Great Book");
//		book1.setPublishedDate(new Date());
//		book1.setPublishedCompany("Apress");
//		Stock stock1 = new Stock();
//		stock1.setCount(10);
//		book1.setStock(stock1);
//		bookService.addBook(book1);
	}
	
	private static void  deleteSeedData(BookService bookService){
//		bookService.deleteStock(2883584);
		bookService.deleteBook(2981888 );
//		bookService.deleteBook(2654208);
//		bookService.deleteBook(2588672);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		final BookService bookService = (BookService) context.getBean("bookServiceJpa");

		String searchWord = "spring";
		String searchWord1 = "name:spring~ AND author:author~"; //And operation
		String searchWord2 = "+name:spring~ +author:author~"; //The + prefix or by using the AND keyword
		String searchWord3 = "name:spring author:author"; //In Lucene, a Boolean OR is the default
		String searchWord4 = "name:spring OR author:author"; //Or you can explicitly include the OR keyword.
		//A search should not include a particular term, you can prefix it with a minus (-) character
		String searchWord5 = "name:spring~ AND author:author~ -description:java"; 
		//You can use a tilde (˜) character, which lets Lucene know that you want to allow a fuzzy match.
		String searchWord6 = "name:spring~"; 
		//Lucene's date-range querying capability:
		String searchWord7 = "name:spring~ AND -publishedDate:[4/18/2012 TO 4/18/2013]";  //DATE NOT WORKING NOW
		
//		addSeedData(bookService);
		deleteSeedData(bookService);
		
		String columns[] = new String[] {"name", "author", "publishedCompany", "description"};
		List<Book> bookList = bookService.freeTextBookSearch(searchWord6 , columns, "name", false, 0, 0);
		int count =0;
		for(Book book1 : bookList){
			count++;
			System.out.println(count+"."+"NAME : "+book1.getName());
		}
	}
}
