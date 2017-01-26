package algorithm.stringArray;

import java.util.HashMap;

/*
 * Input: arr[] = {1, 2, 1, 3, 4, 2, 3};
 * k = 4
 * 
 * Output:
 * 3
 * 4
 * 4
 * 3
 * 
 * Explanation:
 * First window is {1, 2, 1, 3}, count of distinct numbers is 3
 * Second window is {2, 1, 3, 4} count of distinct numbers is 4
 * Third window is {1, 3, 4, 2} count of distinct numbers is 4
 * Fourth window is {3, 4, 2, 3} count of distinct numbers is 3
 */
public class CountDistinctElementInWindow {
	public static void countDistinct(int arr[], int k) {
		HashMap<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < k; i++) {
			map.compute(arr[i], (key, v) -> {
				if (v == null)
					return 1;
				return v + 1;
			});
		}

		System.out.println(map.size());

		for (int i = k; i < arr.length; i++) {

			map.compute(arr[i - k], (key, v) -> {
				if (v == 1)
					return null;
				return v - 1;
			});

			map.compute(arr[i], (key, v) -> {
				if (v == null)
					return 1;
				return v + 1;
			});

			System.out.println(map.size());
		}
	}

	// Driver method
	public static void main(String arg[]) {
		int arr[] = { 1, 2, 1, 3, 4, 2, 3 };
		int k = 4;
		countDistinct(arr, k);
	}
}
