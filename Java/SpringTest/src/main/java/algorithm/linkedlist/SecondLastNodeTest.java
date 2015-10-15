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

	public Node generateOneList() {
		Node node1 = new Node(1);
		node1.next = null;
		return node1;
	}

	public Node generateTwoList() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		node1.next = node2;
		node2.next = null;
		return node1;
	}

	public Node generateOddList() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = null;
		return node1;
	}

	public Node generateEvenList() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = null;
		return node1;
	}

}
