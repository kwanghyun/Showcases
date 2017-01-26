package algorithm.etc;

import java.util.ArrayList;

public class ConsecutiveRange {

	class Range {
		int begin;
		int end;

		public Range(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}

		@Override
		public String toString() {
			return "[begin=" + begin + ", end=" + end + "]";
		}
	}

	public ArrayList<Range> getRanges(int[] arr) {
		if (arr == null || arr.length == 0)
			return null;
		ArrayList<Range> list = new ArrayList<>();

		Range range = new Range(arr[0], arr[0]);
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] - arr[i - 1] == 1) {
				range.end = arr[i];
			} else {
				list.add(range);
				range = new Range(arr[i], arr[i]);
			}
		}
		list.add(range);
		return list;
	}

	public static void main(String[] args) {
		int[] arr = { 4, 5, 6, 7, 8, 9, 12, 15, 16, 17, 18, 20, 22, 23, 24, 27 };
		ConsecutiveRange ob = new ConsecutiveRange();
		ArrayList<Range> ranges = ob.getRanges(arr);
		ranges.forEach(r -> System.out.println(r));
	}
}
