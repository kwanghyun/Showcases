package algorithm.stringArray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * Given an array nums, there is a sliding window of size k which is moving
 * from the very left of the array to the very right. You can only see the k
 * numbers in the window. Each time the sliding window moves right by one
 * position.
	Window position                Max
	---------------               -----
	[1  3  -1] -3  5  3  6  7       3
	 1 [3  -1  -3] 5  3  6  7       3
	 1  3 [-1  -3  5] 3  6  7       5
	 1  3  -1 [-3  5  3] 6  7       5
	 1  3  -1  -3 [5  3  6] 7       6
	 1  3  -1  -3  5 [3  6  7]      7
 */
public class SlidingWindowMaximum {
	// public List<Integer> maxSlidingWindowI(int[] nums, int k){
	// if (nums == null || nums.length == 0)
	// return null;
	// List<Integer> list = new ArrayList<>();
	// int start = 0;
	// Queue<Integer> q = new LinkedList<>();
	//
	// for(int i = 0; i < nums.length; i++){
	// if(!q.isEmpty() && q.peek() >= nums[i]){
	// q.offer(nums[i]);
	// }else{
	// while(!q.isEmpty() && q.peek() >= nums[i])
	// }
	// if(i - start == 3){
	// list.add(q.peek());
	// }
	// }
	//
	// }
	public int[] maxSlidingWindow(int[] nums, int k) {
		if (nums == null || nums.length == 0)
			return new int[0];

		int[] result = new int[nums.length - k + 1];

		LinkedList<Integer> deque = new LinkedList<Integer>();
		for (int i = 0; i < nums.length; i++) {
			if (!deque.isEmpty() && deque.peekFirst() == i - k)
				deque.poll();

			while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
				deque.removeLast();
			}

			deque.offer(i);

			if (i + 1 >= k)
				result[i + 1 - k] = nums[deque.peek()];
		}
		return result;
	}
}
