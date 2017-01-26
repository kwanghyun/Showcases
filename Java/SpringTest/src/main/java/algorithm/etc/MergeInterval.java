package algorithm.etc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import algorithm.trees.Interval;

/*
 Given a collection of intervals, merge all overlapping intervals.
 For example,
 Given [1,3],[2,6],[8,10],[15,18],
 return [1,6],[8,10],[15,18].

 The key to solve this problem is defining a Comparator first to sort the 
 arraylist of Intevals. And then merge some intervals.
 The take-away message from this problem is utilizing the advantage of 
 sorted list/array.
 */
public class MergeInterval {
	public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
		if (intervals == null || intervals.size() <= 1)
			return intervals;

		// sort intervals by using self-defined Comparator
		Collections.sort(intervals, new IntervalComparator());
		ArrayList<Interval> result = new ArrayList<Interval>();
		Interval prev = intervals.get(0);

		for (int i = 1; i < intervals.size(); i++) {
			Interval curr = intervals.get(i);
			if (prev.end >= curr.start) {
				// merged case
				Interval merged = new Interval(prev.start, Math.max(prev.end,
						curr.end));
				prev = merged;
			} else {
				result.add(prev);
				prev = curr;
			}
		}
		result.add(prev);
		return result;
	}
		
	public static void main(String[] args) {
		Interval int1 = new Interval(1,3);
		Interval int2 = new Interval(2,6);
		Interval int3 = new Interval(8,10);
		Interval int4 = new Interval(15,18);
		ArrayList<Interval> intervals = new ArrayList<Interval>();
		intervals.add(int1);
		intervals.add(int2);
		intervals.add(int3);
		intervals.add(int4);
		MergeInterval obj = new MergeInterval();
		ArrayList<Interval> result = obj.merge(intervals);
		System.out.println(result);
	}
}

class IntervalComparator implements Comparator<Interval> {
	public int compare(Interval i1, Interval i2) {
		return i1.start - i2.start;
	}
}

