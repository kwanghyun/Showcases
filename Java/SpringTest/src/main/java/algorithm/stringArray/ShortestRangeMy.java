package algorithm.stringArray;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestRangeMy {

	PriorityQueue<ArrayWrapper> pq = new PriorityQueue<>();
	int max = 0;
	int minDiff = Integer.MAX_VALUE;

	public ShortestRangeMy(int[][] arrs) {
		for (int[] arr : arrs) {
			ArrayWrapper aw = new ArrayWrapper(0, arr);
			insert(aw);
		}
	}

	public int getShortestRange() {
		while (true) {
			System.out.println(pq);
			ArrayWrapper aw = pq.poll();

			int min = aw.getElement();
			System.out.println("MAX -> " + max);
			System.out.println("polled -> " + min);

			if (min == -1)
				break;

			int tempDiff = max - min;
			minDiff = Math.min(minDiff, tempDiff);
			insert(aw);
		}
		return minDiff;
	}

	public void insert(ArrayWrapper aw) {
		max = Math.max(max, aw.getElement());
		pq.offer(aw);
		aw.idx++;
	}

	public static void main(String[] args) {
		int[][] arrs = { { 3, 10, 15, 24 }, { 0, 1, 2, 20 }, { 1, 18, 21, 30 } };
		ShortestRangeMy ob = new ShortestRangeMy(arrs);
		System.out.println("Min Ranged is " + ob.getShortestRange());
	}

	class ArrayWrapper implements Comparable<ArrayWrapper> {
		int idx;
		int[] arr;

		public ArrayWrapper(int idx, int[] arr) {
			this.idx = idx;
			this.arr = arr;
		}

		public boolean isLastElement() {
			if (idx >= arr.length - 1)
				return true;
			return false;
		}

		public int getElement() {
			if (isLastElement()) {
				return -1;
			}
			return arr[idx];
		}

		@Override
		public int compareTo(ArrayWrapper o) {
			return this.arr[idx] - o.arr[o.idx];
		}

		@Override
		public String toString() {
			return "[" + arr[idx] + "] ";
		}
	}
}
