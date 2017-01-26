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
 * Time Complexity: The worst case time complexity of the above solution is
 * still O(n2). In worst case, the randomized function may always pick a
 * corner element. The expected time complexity of above randomized
 * QuickSelect is Θ(n), random number generator is equally likely
 * to generate any number in the input range.
 */

public class KthLargestElements {
	public int findKthLargestI(int[] nums, int k) {
		Arrays.sort(nums);
		return nums[nums.length - k];
	}

	public int findKthLargestII(int[] nums, int k) {
		if (k < 1 || nums == null) {
			return 0;
		}

		return getKth(nums.length - k, nums, 0, nums.length - 1);
	}

	/*
	 * This problem can also be solve by using the quickselect approach, which
	 * is similar to quicksort.
	 * 
	 * Average case time is O(n), worst case time is O(n^2).
	 */
	public int getKth(int k, int[] nums, int start, int end) {

		int pivot_val = nums[end];

		int left = start;
		int right = end;

		while (true) {

			while (nums[left] < pivot_val && left < right) {
				left++;
			}

			while (nums[right] >= pivot_val && right > left) {
				right--;
			}

			if (left == right) {
				break;
			}

			swap(nums, left, right);
		}

		swap(nums, left, end);

		if (k == left) {
			return pivot_val;
		} else if (k < left) {
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

	// quick select
	public int findKthLargest(int[] nums, int k) {
		if (nums == null || nums.length == 0 || nums.length < k)
			return -1;
		int l = 0, r = nums.length - 1;
		while (true) {
			int p = partition(nums, l, r);
			if (p > k - 1) {
				r = p - 1;
			} else if (p < k - 1) {
				l = p + 1;
			} else {
				break;
			}
		}
		return nums[k - 1];
	}

	public int partition(int[] nums, int low, int high) {
		int left = low, right = high + 1;

		while (true) {

			while (left < high && nums[left] > nums[low])
				left++;

			while (right > low && nums[right] < nums[low])
				right--;

			if (left >= right)
				break;
			_swap(nums, left, right);
		}
		_swap(nums, low, right);
		return right;
	}

	public void _swap(int[] nums, int l, int r) {
		int val = nums[l];
		nums[l] = nums[r];
		nums[r] = val;
	}

	public static void main(String[] args) {
		KthLargestElements ob = new KthLargestElements();
		int[] nums = { 9, 3, 4, 6, 3, 7, 2, 1 };
		System.out.println(ob.findKthLargestII(nums, 2));
	}
}
