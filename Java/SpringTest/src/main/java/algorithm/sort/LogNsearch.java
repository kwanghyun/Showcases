package algorithm.sort;

/*
 * Given a sorted array of n integers that has been rotated an unknown number of times, 
 * give an O(log n) algorithm that finds an element in the array. You may assume that the array was originally sorted in increasing order.
 * EXAMPLE:
 * Input: find 5 in array (15 16 19 20 25 1 3 4 5 7 10 14)
 * Output: 8 (the index of 5 in the array)
 */
public class LogNsearch {

	int count = 1;

	public int search(int[] arr, int start, int end, int num) {

		int mid = start + (end - start) / 2;

		if (start < 0 || mid > arr.length - 1)
			return count;

		if (num == arr[mid]) {
			return count;
		} else if (num < arr[mid]) {
			count++;
			search(arr, start, mid - 1, num);
		} else if (num > arr[mid]) {
			count++;
			search(arr, mid + 1, end, num);
		}
		return count;
	}
	
	public static int search2Book(int arr[], int start, int end, int num) {
		while (start <= end) {
			int mid = (start + end) / 2;
			if (num == arr[mid]) {
				return mid;
			} else if (arr[start] <= arr[mid]) {
				if (num > arr[mid]) {
					start = mid + 1;
				} else if (num >=arr[start]) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			} else if (num < arr[mid])
				end = mid - 1;
			else if (num <= arr[end])
				start = mid + 1;
			else
				end = mid - 1;
		}
		return -1;
	}
	
	public int searchIter(int[] arr, int start, int end, int num){
		int mid = 0;
		while(start <= end){
			mid = start + (end - start)/2;
//			System.out.println(mid);
			
			//Normal Case
			if(arr[mid] == num)
				return mid;
			else if(num> arr[mid]){
				start = mid + 1;
			}else if (num < arr[mid]){
				end = mid - 1;
			}
		}
		return mid;
	}

	public static void main(String args[]) {
//		int[] arr = { 15, 16, 18, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14 };
		int[] arr = {1, 3, 4, 5, 7, 10, 14 , 15, 16, 18, 19, 20, 25};
		LogNsearch lns = new LogNsearch();
//		System.out.println(lns.search(arr, 0, arr.length - 1, 15));
		System.out.println(lns.searchIter(arr, 0, arr.length - 1, 15));
	}
}
