package algorithm.stringArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Given an integer array of size n, find all elements that appear more than
 * ⌊ n/3 ⌋ times. The algorithm should run in linear time and in O(1) space.
 */

public class MajorityElementsII {

	public List<Integer> getMajority(int[] arr) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			if (map.containsKey(arr[i])) {
				map.put(arr[i], map.get(arr[i]) + 1);
			} else {
				map.put(arr[i], 1);
			}
		}

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (Map.Entry<Integer, Integer> item : map.entrySet()) {
			if (item.getValue() >= arr.length / 3)
				list.add(item.getKey());
		}

		return list;
	}

	public static void main(String[] args) {
		MajorityElementsII ob = new MajorityElementsII();
		int[] arr = { 1, 1, 1, 2, 3, 7, 7, 7, 7 };
		System.out.println(ob.getMajority(arr));
	}
}
