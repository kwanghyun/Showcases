package algorithm.sort;

/*
 * Given a sorted array of strings which is interspersed with empty strings, 
 * write a method to find the location of a given string.
 * Example: find "ball" in ["at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""] will return 4
 * Example: find "ballcar" in ["at", "", "", "", "", "ball", "car", "", "", "dad", "", ""] will return -1
 */
public class FindWithZeroString {

	public int search(String[] arr, int start, int end, String str) {
		int mid = 0;
		while (start <= end) {
			mid = start + (end - start) / 2;

			if (arr[mid] == "") {

				while (arr[mid].equals("")) {
					if (mid > arr.length - 1)
						return -1;
					mid++;
				}
			}
			
			int result = arr[mid].compareTo(str);

			if (result == 0)
				return mid;
			else if (result > 0)
				end = mid - 1;
			else
				start = mid + 1;
		}
		return mid;
	}

	public static void main(String args[]) {
		String[] arr = { "at", "", "", "", "ball", "", "", "car", "", "",
				"dad", "", "" };
		FindWithZeroString fz = new FindWithZeroString();
		System.out.println(fz.search(arr, 0, arr.length - 1, "ball"));
	}
}
