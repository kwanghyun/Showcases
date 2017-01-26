package algorithm.etc;

import java.util.*;

/*
 * Given two 1d vectors, implement an iterator to return their elements
 * alternately.
 * 
 * For example, given two 1d vectors:
 * 
 * v1 = [1, 2]
 * v2 = [3, 4, 5, 6]
 * 
 * By calling next repeatedly until hasNext returns false, the order of
 * elements returned by next should be: [1, 3, 2, 4, 5, 6].
 * 
 * Follow up: What if you are given k 1d vectors? How well can your code be
 * extended to such cases?
 */
public class ZigzagIterator {
	List<List<Integer>> lists;
	int r = 0;
	int c = 0;
	int maxCLen = 0;

	public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
		lists = new ArrayList<>();
		lists.add(v1);
		lists.add(v2);
		maxCLen = Math.max(v1.size(), v2.size());
	}

	public int next() {
		int num = lists.get(r).get(c);
		r++;
		return num;
	}

	public boolean hasNext() {

		while (c < maxCLen) {
			if (r == lists.size()) {
				r = 0;
				c++;
			}

			if (c >= lists.get(r).size()) {
				r++;
			} else if (c < lists.get(r).size() && r < lists.size())
				break;
		}

		return (c >= maxCLen) ? false : true;
	}

}
