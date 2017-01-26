package algorithm.stringArray;

import java.util.*;

import algorithm.stringArray.FindKPairsWithSmallestSums.QNode;

/*
 * Given a n x n matrix where each of the rows and columns are sorted in
 * ascending order, find the kth smallest element in the matrix.
 * 
 * Note that it is the kth smallest element in the sorted order, not the kth
 * distinct element.
 * 
 * Example:
 * 
 * matrix = [ 
 * [ 1, 5, 9], 
 * [10, 11, 13], 
 * [12, 13, 15] ], 
 * 
 * k = 8,
 * 
 * return 13.
 */
public class KthSmallestElementInSortedMatrix {
	class QNode implements Comparable<QNode> {
		int[] arr;
		int idx;

		QNode(int[] arr, int i) {
			this.idx = i;
			this.arr = arr;
		}

		@Override
		public int compareTo(QNode o) {
			return arr[idx] - o.arr[o.idx];
		}

		@Override
		public String toString() {
			return "idx = " + idx + ", arr = " + Arrays.toString(arr);
		}
	}

	public int kthSmallest(int[][] matrix, int k) {
		Queue<QNode> q = new PriorityQueue<>();
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
			q.offer(new QNode(matrix[i], 0));
		}

		int minNum = 0;
		int count = 0;
		while (!q.isEmpty()) {
			if (count == k)
				break;
			QNode minNode = q.poll();
			System.out.println(q);
			System.out.println("idx = " + minNode.idx + ", arr[i] =" + minNode.arr[minNode.idx]);
			minNum = minNode.arr[minNode.idx];
			if (minNode.idx < minNode.arr.length - 1)
				q.offer(new QNode(minNode.arr, minNode.idx + 1));
			count++;
		}

		return minNum;
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 5, 9 }, { 10, 11, 13 }, { 12, 13, 15 } };
		int k = 8;
		KthSmallestElementInSortedMatrix ob = new KthSmallestElementInSortedMatrix();
		System.out.println(ob.kthSmallest(matrix, k));
	}
}
