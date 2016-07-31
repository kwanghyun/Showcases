package algorithm.linkedlist;

public class LRUNode {

	int key;
	int value;
	LRUNode prev;
	LRUNode next;

	public LRUNode(int key, int value) {
		this.key = key;
		this.value = value;
	}

}
