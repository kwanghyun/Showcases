package algorithm.stringArray;

import java.util.ArrayList;
import java.util.List;

/*
 * Given a sorted integer array where the range of elements are in the
 * inclusive range [lower, upper], return its missing ranges.
 * 
 * For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99, return
 * ["2", "4->49", "51->74", "76->99"].
 */
public class MissingRanges {
	public List<String> findMissingRanges(int[] nums, int lower, int upper) {
		ArrayList<String> result = new ArrayList<>();
		if (nums == null)
			return result;

		if (nums.length == 0) {
			if (upper - lower >= 1) {
				result.add(lower + "->" + upper);
			} else if (upper - lower == 0) {
				result.add("" + upper);
			}
			return result;
		}

		int prev = lower;
		for (int i = 0; i < nums.length; i++) {
			long diff = ((long) nums[i] - prev);

			if (diff >= 2) {
				result.add(prev + "->" + (nums[i] - 1));
			} else if (diff == 1) {
				result.add("" + prev);
			}
			prev = nums[i] + 1;
		}

		if (upper - prev >= 1) {
			result.add(prev + "->" + upper);
		} else if (upper - prev == 0) {
			result.add("" + upper);
		}

		return result;
	}

	public static void main(String[] args) {

		int[] nums = { -2147483648, 2147483647 };
		int min = -2147483648;
		int max = 2147483647;

		System.out.println((long) 2147483647 - (-2147483647));
		MissingRanges ob = new MissingRanges();
		System.out.println(ob.findMissingRanges(nums, min, max));
	}
}
