package algorithm.etc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*Design an algorithm to find all pairs of integers within an array which sum to a specified value.
 * 
 */
public class CC_19_11_SumPairInArray {

	public List<String> findSumParis(int[] arr, int sum) {
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] + arr[j] == sum)
					list.add(arr[i] + " , " + arr[j]);
			}
		}

		return list;
	}

	// This only works for no duplicated array.
	public List<String> doBetter2(int[] arr, int sum) {
		Arrays.sort(arr);
		List<String> list = new ArrayList<String>();
		int head = 0;
		int tail = arr.length - 1;

		while (head < tail) {
			if (sum == arr[head] + arr[tail]) {
				list.add(arr[head] + " , " + arr[tail]);
				head++;
				tail--;
			} else if (sum > arr[head] + arr[tail]) {
				head++;
			} else {
				tail--;
			}
		}
		return list;
	}

	public int[] doBetter3(int[] arr, int sum) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int[] result = new int[2];
		for (int i = 0; i < arr.length; i++) {
			
			/* But hashmap is O(n) time complexity */
			if (map.containsKey(arr[i])) { //if the number necessary meet
				int index = map.get(arr[i]);
				result[0] = index + 1;
				result[1] = i + 1;
				break;
			} else {
				//Store what number need to be the target number.
				map.put(sum - arr[i], i);
			}
		}
		return result;
	}

	public static void main(String args[]) {
		// No duplicated element
		int[] arr = { 5, 1, 0, 3, 4, 7, 2, 6, 8, 10, 9 };
		// With duplicated elements
		// int[] arr = {5,1,0,3,8,2,4,7,2,6,8,10,9};
		CC_19_11_SumPairInArray spa = new CC_19_11_SumPairInArray();
		List<String> list = spa.findSumParis(arr, 10);
		List<String> list3 = spa.doBetter2(arr, 10);
		for (String str : list) {
			System.out.println(str);
		}
		System.out.println("------------2-----------");
		for (String str : list3) {
			System.out.println(str);
		}
		
		int[] result = spa.doBetter3(arr, 10);
		System.out.println("------------3-----------");
		for(int i : result){
			System.out.println(i);
		}
	}

}
