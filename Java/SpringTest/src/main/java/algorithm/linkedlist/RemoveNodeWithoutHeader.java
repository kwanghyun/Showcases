package algorithm.linkedlist;

/*Remove given reference of node, without know for header 
 * 
 * Copy the next data and reference to current node.
 * */
public class RemoveNodeWithoutHeader {

	void removeNode(Node node){
		Node next = node.next;
		node.val =node.next.val;
		node.next = next;
	}
}
