package algorithm.dynamic;

import java.util.*;

/*
 * You have a number of envelopes with widths and heights given as a pair of
 * integers (w, h). One envelope can fit into another if and only if both
 * the width and height of one envelope is greater than the width and height
 * of the other envelope.
 * 
 * What is the maximum number of envelopes can you Russian doll? (put one
 * inside other)
 * 
 * Example: Given envelopes = [[5,4],[6,4],[6,7],[2,3]], the maximum number
 * of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 */
public class RussianDollEnvelopes {
	public int maxEnvelopes(int[][] envelopes) {
		if (envelopes == null || envelopes.length == 0)
			return 0;

		Arrays.sort(envelopes, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				if (a[0] != b[0]) {
					return a[0] - b[0];
				} else {
					return a[1] - b[1];
				}
			}
		});
		int max = 1;
		int[] arr = new int[envelopes.length];
		for (int i = 0; i < envelopes.length; i++) {
			arr[i] = 1;
			for (int j = i - 1; j >= 0; j--) {
				if (envelopes[i][0] > envelopes[j][0] && envelopes[i][1] > envelopes[j][1]) {
					arr[i] = Math.max(arr[i], arr[j] + 1);
				}
			}
			max = Math.max(max, arr[i]);
		}

		return max;
	}

	public int maxEnvelopesI(int[][] envelopes) {
		if (envelopes == null || envelopes.length == 0)
			return 0;

		Arrays.sort(envelopes, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				if (a[0] != b[0]) {
					return a[0] - b[0]; // ascending order
				} else {
					return b[1] - a[1]; // descending order
				}
			}
		});

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < envelopes.length; i++) {

			if (list.size() == 0 || list.get(list.size() - 1) < envelopes[i][1])
				list.add(envelopes[i][1]);

			int l = 0;
			int r = list.size() - 1;

			while (l < r) {
				int m = l + (r - l) / 2;
				if (list.get(m) < envelopes[i][1]) {
					l = m + 1;
				} else {
					r = m;
				}
			}

			list.set(r, envelopes[i][1]);
		}

		return list.size();
	}
}
