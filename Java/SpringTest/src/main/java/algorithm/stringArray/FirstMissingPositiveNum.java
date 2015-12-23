package algorithm.stringArray;

/*Given an unsorted integer array, find the first missing positive integer. For example,
 given [1,2,0] return 3 and [3,4,-1,1] return 2.
 Your algorithm should run in O(n) time and uses constant space.

 This problem can solve by using a bucket-sort like algorithm. Let’s consider finding
 first missing positive and 0 first. The key fact is that the ith element should be i, so we
 have: i==A[i] A[i]==A[A[i]]
 */
public class FirstMissingPositiveNum {

	public static int firstMissingPositiveAnd0(int arr[], int num) {
		for (int i = 0; i < num; i++) {
			while (arr[i] != i) {
				// if (arr[i] != i) {
				// no need to swap when ith element is out of range [0,n]
				if (arr[i] < 0 || arr[i] >= num)
					break;
				// swap elements
				int temp = arr[i];
				arr[i] = arr[temp];
				arr[temp] = temp;
			}
		}

		for (int i = 1; i < num; i++) {
			if (arr[i] != i)
				return i;
		}
		return num;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 0 };
		int[] arr2 = { 3, 4, -1, 1 };
		// System.out.println(firstMissingPositiveAnd0(arr, arr.length));
		System.out.println(firstMissingPositiveAnd0(arr2, arr2.length));
	}
}
