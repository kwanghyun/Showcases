package transaction;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import transaction.jdbc.BookShop;
import transaction.service.BookService;

public class PropagationExample{
	private static final Logger log = LoggerFactory
			.getLogger(PropagationExample.class);

	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"beans.xml");
			final BookShop bookShop = (BookShop) context.getBean("bookShop");
			
			/* 
			 * JDBC Transaction Propagation Test 
			 * Key Points
			 * 1. "persistence.xml"'s transaction-type have 2 types "RESOURCE_LOCAL", "JTA", "JTA" is for distribute system.
			 * 2. Transaction Propagation not available in application-managed transaction mode
			 * 3. @Transactional rolled back by default RuntimeExcetion only.
			 * 4. Propagation mode is how to handle vertical @Transationals' of different layers , whether treat as one or not.
			 * 5. Refer (Pro JPA - Chatper6, Spring Recipe - Chatper 16)
			 * */
			

			/* DB Preset */
			String testUserName = "Jang";
			long teststockId = 1L;
			
			/*Initialization*/
			bookShop.setAccount(testUserName, 50);
			bookShop.setStock(teststockId, 5);
			
			/*Condition : JDBC Transaction DEFAULT(REQUIRES) Propagation Test 
			 * Result : All changes Roll backed because RuntimeExceptipon
			 * */
			List<Long> bookIds = new ArrayList<Long>();
			bookIds.add(new Long(4));
			bookIds.add(new Long(4));
//			bookShop.checkout(bookIds, testUserName);

			
			/*Condition : JDBC Transaction REQUIRES_NEW Propagation Test 
			 * Result : All changes Roll backed because RuntimeExceptipon
			 * */
			//TODO : Not throw exception explicitly, need to make DB throw exception like "Spring Recipe Chapter 16.7" 
			bookShop.checkout_requiredNew(bookIds, testUserName);		
			
			/*Result Check*/
			System.out.println("Remaining account ::: "+ bookShop.getAccount(testUserName));
			System.out.println("Remaining stock ::: " + bookShop.getStockCountOfBook(4));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
