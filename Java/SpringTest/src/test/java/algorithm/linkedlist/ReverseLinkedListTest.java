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
		ListNode node1 = new ListNode(1);
		node1.next = null;
		
		ListNode p = obj.reverseList2(node1);
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
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		node1.next = node2;
		node2.next = null;

		ListNode p = obj.reverseList2(node1);
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

		
		ListNode p = obj.reverseList2(node1);
		List<Integer> list = new ArrayList<Integer>();
		while (p != null) {
			list.add(p.val);
			p = p.next;
		}

		Object[] expected = { 5, 4, 3, 2, 1 };

		assertArrayEquals(expected, list.toArray());

	}
}
