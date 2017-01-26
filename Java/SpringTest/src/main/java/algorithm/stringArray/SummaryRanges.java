package algorithm.stringArray;

import java.util.ArrayList;
import java.util.List;

/*
 * Given a sorted integer array without duplicates, return the summary of
 * its ranges.
 * 
 * For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].
 */
public class SummaryRanges {
	public List<String> summaryRanges(int[] nums) {

		List<String> result = new ArrayList<>();
		if (nums == null || nums.length == 0)
			return result;

		int len = nums.length;
		int prev = nums[0];

		if (len == 1) {
			result.add("" + nums[0]);
			return result;
		}

		boolean isContinuous = false;

		for (int i = 1; i < len; i++) {
			int diff = nums[i] - nums[i - 1];
			if (diff == 1) {
				isContinuous = true;
			} else {
				if (prev == nums[i - 1]) {
					result.add("" + prev);
				} else {
					result.add(prev + "->" + nums[i - 1]);
				}
				prev = nums[i];
				isContinuous = false;
			}
		}

		if (isContinuous) {
			result.add(prev + "->" + nums[len - 1]);
		} else {
			result.add("" + nums[len - 1]);
		}

		return result;
	}

	public static void main(String[] args) {
		SummaryRanges ob = new SummaryRanges();
		int[] nums = { 0, 1, 2, 4, 5, 7 };
		System.out.println(ob.summaryRanges(nums));
	}
}
