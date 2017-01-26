package algorithm.linkedlist;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SecondLastNodeTest {
	SecondLastNode obj = new SecondLastNode();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOne() {
		assertEquals(null, obj.solution(generateOneList()));
	}
	
	@Test
	public void testTwo() {
		assertEquals(1, obj.solution(generateTwoList()).val);
	}

	@Test
	public void testOdd() {
		assertEquals(4, obj.solution(generateOddList()).val);
	}

	@Test
	public void testEven() {
		assertEquals(5, obj.solution(generateEvenList()).val);
	}

	public ListNode generateOneList() {
		ListNode node1 = new ListNode(1);
		node1.next = null;
		return node1;
	}

	public ListNode generateTwoList() {
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		node1.next = node2;
		node2.next = null;
		return node1;
	}

	public ListNode generateOddList() {
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = null;
		return node1;
	}

	public ListNode generateEvenList() {
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		ListNode node6 = new ListNode(6);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = null;
		return node1;
	}

}
