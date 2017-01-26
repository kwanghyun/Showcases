package algorithm.stringArray;

/*
 * Given a 1D array, implement function Sum(x1,x2) where x1 and x2 are
 * indices of array. Find sum of all elements in between the given indices
 * inclusive of them. Do in Time complexity of O(1)
 */
public class SumOrderOfOne {
	public int getSumBetween(int[] arr, int idx1, int idx2) {
		int[] sum = new int[arr.length];
		sum[0] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			sum[i] = sum[i - 1] + arr[i];
		}
		return sum[idx2] - ((idx1 - 1) >= 0 ? sum[idx1 - 1] : 0);
	}

	public static void main(String[] args) {
		SumOrderOfOne ob = new SumOrderOfOne();
		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		System.out.println(ob.getSumBetween(arr, 1, 4));
		System.out.println(ob.getSumBetween(arr, 0, 1));
		System.out.println(ob.getSumBetween(arr, 7, 8));
	}
}
