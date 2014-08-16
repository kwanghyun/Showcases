package hibernate.query;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import transaction.entity.Book;
import transaction.service.BookService;

public class Hibernate_Criteria_Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		final BookService bookService = (BookService) context.getBean("bookServiceJpa");
		
		List<Book> bookList = bookService.searchBookWithCriteriaAPI();
//		Object obj = bookService.getAggregateFunctionResult();
//		System.out.println("Return Value is : "+obj);
		int count =0;		
		
		for(Book book1 : bookList){
			count++;
			System.out.println(count+"." + book1.toString());
			System.out.println(count+"."+"NAME : "+book1.getName() + " / Published Date : " + book1.getPublishedDate());
		}
	}
}
