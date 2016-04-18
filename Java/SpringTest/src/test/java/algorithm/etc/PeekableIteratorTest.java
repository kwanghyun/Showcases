package algorithm.etc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PeekableIteratorTest {

	List <String> list = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		list.add("C");
		list.add("A");
		list.add("E");
		list.add("B");
		list.add("D");
	}

	@Test
	public void test() {
		Collections.sort(list);
		
		for(String str : list){
			System.out.println(str);
		}
		Iterator<String> iterator = list.iterator();
		PeekableIterator<String> pIterator = new PeekableIterator<>(iterator);
		assertEquals("A", pIterator.peek().toString());
		assertEquals("A", pIterator.peek().toString());
		assertEquals("A", pIterator.next().toString());
		assertEquals("B", pIterator.next().toString());
		assertEquals("C", pIterator.peek().toString());
		assertEquals("C", pIterator.peek().toString());
		assertEquals("C", pIterator.next().toString());
		assertEquals("D", pIterator.peek().toString());
		assertEquals("D", pIterator.next().toString());
		assertEquals("E", pIterator.next().toString());
		assertEquals(null, pIterator.peek());
		assertEquals(null, pIterator.next());
	}
}
