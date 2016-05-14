package algorithm.stringArray;

/*
 * Given an array of non-negative integers, you are initially positioned at
 * the first index of the array. Each element in the array represents your
 * maximum jump length at that position. Determine if you are able to reach
 * the last index. For example: A = [2,3,1,1,4], return true. A =
 * [3,2,1,0,4], return false.
 * 
 * Analysis
 * 
 * We can track the maximum index that can be reached. The key to solve this
 * problem is to find: 
 * 1) when the current position can not reach next position (return false) , and 
 * 2) when the maximum index can reach the end (return true).
 * 
 * The largest index that can be reached is: i + A[i].
 */	
public class JumpGame {

	public boolean canJump(int[] arr) {
		if (arr.length <= 1)
			return true;

		int max = arr[0]; // max stands for the largest index that can be reached.

		for (int i = 0; i < arr.length; i++) {
			// if not enough to go to next
			if (max <= i && arr[i] == 0)
				return false;

			// update max
			if (i + arr[i] > max) {
				max = i + arr[i];
			}

			// max is enough to reach the end
			if (max >= arr.length - 1)
				return true;
		}

		return false;
	}
}
