package algorithm.linkedlist;

import java.util.HashMap;

/*
 * Design and implement a data structure for Least Recently Used (LRU)
 * cache. It should support the following operations: get and set.
 * 
 * get(key) - Get the value (will always be positive) of the key if the key
 * exists in the cache, otherwise return -1. set(key, value) - Set or insert
 * the value if the key is not already present. When the cache reached its
 * capacity, it should invalidate the least recently used item before
 * inserting a new item.
 * 
 * Analysis
 * 
 * The key to solve this problem is using a double linked list which enables
 * us to quickly move LRUNodes.
 * 
 * The LRU cache is a hash table of keys and double linked LRUNodes. The hash
 * table makes the time of get() to be O(1). The list of double linked LRUNodes
 * make the LRUNodes adding/removal operations O(1).
 */
public class LRUCache {

	int capacity;
	HashMap<Integer, LRUNode> map = new HashMap<Integer, LRUNode>();
	LRUNode head = null;
	LRUNode end = null;

	public LRUCache(int capacity) {
		this.capacity = capacity;
	}

	public int get(int key) {
		if (map.containsKey(key)) {
			LRUNode n = map.get(key);
			remove(n);
			setHead(n);
			return n.value;
		}

		return -1;
	}

	public void remove(LRUNode n) {
		if (n.pre != null) {
			n.pre.next = n.next;
		} else {
			head = n.next;
		}

		if (n.next != null) {
			n.next.pre = n.pre;
		} else {
			end = n.pre;
		}

	}

	public void setHead(LRUNode n) {
		n.next = head;
		n.pre = null;

		if (head != null)
			head.pre = n;

		head = n;

		if (end == null)
			end = head;
	}

	public void set(int key, int value) {
		if (map.containsKey(key)) {
			LRUNode old = map.get(key);
			old.value = value;
			remove(old);
			setHead(old);
		} else {
			LRUNode created = new LRUNode(key, value);
			if (map.size() >= capacity) {
				map.remove(end.key);
				remove(end);
				setHead(created);

			} else {
				setHead(created);
			}

			map.put(key, created);
		}
	}
}
