package spring.config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		SequenceGenerator sg = (SequenceGenerator) context.getBean("sequenceGenerator");
		System.out.println(sg.getSequence());
		System.out.println(sg.getSequence());
	}

}
