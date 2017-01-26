package algorithm.stringArray;

import java.util.*;

/*
 * You are given two integer arrays nums1 and nums2 sorted in ascending
 * order and an integer k.
 * 
 * Define a pair (u,v) which consists of one element from the first array
 * and one element from the second array.
 * 
 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
 * 
 * Example 1: 
 * 
 * Given 
 * nums1 = [1,7,11], 
 * nums2 = [2,4,6], 
 * k = 3
 * 
 * Return: [1,2],[1,4],[1,6]
 * 
 * The first 3 pairs are returned from the sequence:
 * [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6] 
 * 
 * Example 2: 
 * Given
 * nums1 = [1,1,2], 
 * nums2 = [1,2,3], 
 * k = 2
 * 
 * Return: [1,1],[1,1]
 * 
 * The first 2 pairs are returned from the sequence:
 * [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3] 
 * 
 * Example 3: Given
 * nums1 = [1,2], 
 * nums2 = [3], 
 * k = 3
 * 
 * Return: [1,3],[2,3]
 * 
 * All possible pairs are returned from the sequence: [1,3],[2,3]
 */

public class FindKPairsWithSmallestSums {

	class QNode implements Comparable<QNode> {

		int i;
		int j;

		QNode(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public int compareTo(QNode o) {

			return (o.i + o.j) - (this.i + this.j);
		}
	}

	public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
		List<int[]> result = new ArrayList<>();
		if (k <= 1)
			return result;

		PriorityQueue<QNode> q = new PriorityQueue<>();
		for (int i = 0; i < nums1.length; i++) {
			for (int j = 0; j < nums2.length; j++) {
				q.offer(new QNode(nums1[i], nums2[j]));
				if (q.size() > k)
					q.poll();
			}
		}
		Iterator<QNode> it = q.iterator();
		while (it.hasNext()) {
			int[] pair = new int[2];
			QNode n = it.next();
			pair[0] = n.i;
			pair[1] = n.j;
			result.add(pair);
		}
		
		return result;
	}

	public static void main(String[] args) {
		FindKPairsWithSmallestSums ob = new FindKPairsWithSmallestSums();
		int[] nums1 = { 1, 7, 11 };
		int[] nums2 = { 2, 4, 6 };
		int k = 3;
		// int[] nums1 = { 1, 1, 2 };
		// int[] nums2 = { 1, 2, 3 };
		// int k = 3;
		// int[] nums1 = { 1, 2 };
		// int[] nums2 = { 3 };
		// int k = 3;

		List<int[]> result = ob.kSmallestPairs(nums1, nums2, k);
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
}
