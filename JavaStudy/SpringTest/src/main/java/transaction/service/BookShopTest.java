package transaction.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import transaction.entity.Account;

public class BookShopTest {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		final BookService bookService = (BookService) context.getBean("bookService");

//		bookService.createAccount("JANG", 50);
		bookService.withdraw(131072, 20);
//		Book book = bookService.getBook(4);
//		System.out.println(book.getName());
		
//		Account account = bookService.getAccount(1);
//		System.out.println(account.getName());
//		bookService.createAccount("JANG",50);
	}

}
