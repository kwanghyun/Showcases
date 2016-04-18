package algorithm.linkedlist;

public class LRUNode {

	int key;
	int value;
	LRUNode pre;
	LRUNode next;

	public LRUNode(int key, int value) {
		this.key = key;
		this.value = value;
	}

}
