package algorithm.linkedlist;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * This is a classic interview question. Another similar problem is
 * "merge k sorted lists".
 * 
 * This problem can be solved by using a heap. The time is O(nlog(n)).
 * 
 * Given m arrays, the minimum elements of all arrays can form a heap. It
 * takes O(log(m)) to insert an element to the heap and it takes O(1) to
 * delete the minimum element.
 */
public class MergeKSortedLists {

	public static int[] mergeKSortedArray(int[][] arr) {
		// PriorityQueue is heap in Java
		PriorityQueue<ArrayContainer> queue = new PriorityQueue<>();
		int total = 0;

		// add arrays to heap
		for (int i = 0; i < arr.length; i++) {
			queue.add(new ArrayContainer(arr[i], 0));
			total = total + arr[i].length;
		}

		int resultIdx = 0;
		int result[] = new int[total];

		// while heap is not empty
		while (!queue.isEmpty()) {
			ArrayContainer ac = queue.poll();
			result[resultIdx++] = ac.arr[ac.index];

			if (ac.index < ac.arr.length - 1) {
				queue.add(new ArrayContainer(ac.arr, ac.index + 1));
			}
		}

		return result;
	}

	public static void main(String[] args) {
		int[] arr1 = { 1, 3, 5, 7 };
		int[] arr2 = { 2, 4, 6, 8 };
		int[] arr3 = { 0, 9, 10, 11 };

		int[] result = mergeKSortedArray(new int[][] { arr1, arr2, arr3 });
		System.out.println(Arrays.toString(result));
	}

}
