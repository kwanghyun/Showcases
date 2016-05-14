package algorithm.stringArray;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * Find the kth largest element in an unsorted array. Note that it is the
 * kth largest element in the sorted order, not the kth distinct element.
 * 
 * For example, given [3,2,1,5,6,4] and k = 2, return 5.
 * 
 * Note: You may assume k is always valid, 1 ≤ k ≤ array's length.
 * 
 * 
 */
public class KthLargestElements {

	/* Time is O(nlog(n)) */
	public int findKthLargestI(int[] nums, int k) {
		Arrays.sort(nums);
		return nums[nums.length - k];
	}

	public int findKthLargestII(int[] nums, int k) {
		if (k < 1 || nums == null) {
			return 0;
		}

		return getKth(nums.length - k + 1, nums, 0, nums.length - 1);
	}

	/*
	 * This problem can also be solve by using the quickselect approach, which
	 * is similar to quicksort.
	 * 
	 * Average case time is O(n), worst case time is O(n^2).
	 */
	public int getKth(int k, int[] nums, int start, int end) {

		int pivot = nums[end];

		int left = start;
		int right = end;

		while (true) {

			while (nums[left] < pivot && left < right) {
				left++;
			}

			while (nums[right] >= pivot && right > left) {
				right--;
			}

			if (left == right) {
				break;
			}

			swap(nums, left, right);
		}

		swap(nums, left, end);

		if (k == left + 1) {
			return pivot;
		} else if (k < left + 1) {
			return getKth(k, nums, start, left - 1);
		} else {
			return getKth(k, nums, left + 1, end);
		}
	}

	public void swap(int[] nums, int n1, int n2) {
		int tmp = nums[n1];
		nums[n1] = nums[n2];
		nums[n2] = tmp;
	}

	public int findKthLargestIII(int[] nums, int k) {
		PriorityQueue<Integer> q = new PriorityQueue<Integer>(k);
		for (int i : nums) {
			q.offer(i);
			System.out.println("Q => " + q);
			if (q.size() > k) {
				q.poll();
			}
		}

		return q.peek();
	}
	
	public static void main(String[] args) {
		KthLargestElements ob = new KthLargestElements();
		int[] nums = {9,3,4,6,3,7,2,1};
		System.out.println(ob.findKthLargestIII(nums, 2));
	}
}
