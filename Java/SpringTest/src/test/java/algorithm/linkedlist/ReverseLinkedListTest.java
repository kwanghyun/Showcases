package algorithm.linkedlist;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ReverseLinkedListTest {

	ReverseLinkedList obj = new ReverseLinkedList();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNull() {
		assertEquals(null, obj.reverseList2(null));
	}

	@Test
	public void testOK1() {
		Node node1 = new Node(1);
		node1.next = null;
		
		Node p = obj.reverseList2(node1);
		List<Integer> list = new ArrayList<Integer>();
		while (p != null) {
			list.add(p.val);
			p = p.next;
		}

		Object[] expected = { 1 };

		assertArrayEquals(expected, list.toArray());
	}

	@Test
	public void testOK2() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		node1.next = node2;
		node2.next = null;

		Node p = obj.reverseList2(node1);
		List<Integer> list = new ArrayList<Integer>();
		while (p != null) {
			list.add(p.val);
			p = p.next;
		}

		Object[] expected = { 2, 1 };

		assertArrayEquals(expected, list.toArray());
	}

	@Test
	public void testOK3() {

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

		
		Node p = obj.reverseList2(node1);
		List<Integer> list = new ArrayList<Integer>();
		while (p != null) {
			list.add(p.val);
			p = p.next;
		}

		Object[] expected = { 5, 4, 3, 2, 1 };

		assertArrayEquals(expected, list.toArray());

	}
}
