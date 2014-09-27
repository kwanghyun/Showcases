package algorithm.trees;

import java.util.Arrays;

public class MyBinarySearch {

	static int count = 0;

	public int binarySearch(int[] array, int start, int end, int targetNum) {

		System.out.println("-----------------------------------------");
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		System.out.println("-----------------------------------------");

		MyBinarySearch.count++;

		if (start < 0)
			start = 0;
		if (end > array.length)
			end = array.length;
		if (start >= end)
			return -1;

		int middle = end / 2 + start;
		if (middle > array.length)
			middle = array.length - 1;

		if (array[middle] > targetNum) {
			binarySearch(array, start, middle , targetNum);
		} else if (array[middle] < targetNum) {
			binarySearch(array, middle + 1, end, targetNum);
		} else if (array[middle] == targetNum) {
			return MyBinarySearch.count;
		}
		return MyBinarySearch.count;
	}

	public static void main(String args[]) {

		MyBinarySearch bs = new MyBinarySearch();
		int[] array = createSortedRandomIntegerArray(10000);
		int start = 0;
		int end = array.length-1;
		int targetNum = 14;
		System.out.println("count : "
				+ bs.binarySearch(array, start, end, targetNum));
	}

	private static int[] createSortedRandomIntegerArray(int maxLengh) {

		int array[] = new int[maxLengh];

		for (int i = 0; i < maxLengh; i++) {
			int num = new Double(Math.random() * (maxLengh * 10)).intValue();
			array[i] = num;
		}
		Arrays.sort(array);
		System.out.println(array);
		for (int i = 0; i < maxLengh; i++) {
			System.out.print("[" + array[i] + "] , ");
		}
		return array;
	}
}
