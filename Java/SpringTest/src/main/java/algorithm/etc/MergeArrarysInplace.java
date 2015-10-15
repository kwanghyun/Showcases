package algorithm.etc;

/*The key to solve this problem is moving element of A and B backwards. If B has some
 elements left after A is done, also need to handle that case.
 The takeaway message from this problem is that the loop condition. This kind of
 condition is also used for merging two sorted linked list.*/
public class MergeArrarysInplace {
	
	public void merge(int arrA[], int idxA, int arrB[], int idxB) {
		
		while (idxA > 0 && idxB > 0) {
			if (arrA[idxA - 1] > arrB[idxB - 1]) {
				arrA[idxA + idxB - 1] = arrA[idxA - 1];
				idxA--;
			} else {
				arrA[idxA + idxB - 1] = arrB[idxB - 1];
				idxB--;
			}
		}
		
		while (idxB > 0) {
			arrA[idxA + idxB - 1] = arrB[idxB - 1];
			idxB--;
		}
	}
}
