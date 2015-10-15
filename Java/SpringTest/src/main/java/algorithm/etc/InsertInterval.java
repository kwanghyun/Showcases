package algorithm.etc;

import java.util.ArrayList;

/*Problem:
 Given a set of non-overlapping & sorted intervals, insert a new interval into the intervals
 (merge if necessary).
 Example 1:
 Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
 Example 2:
 Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as
 [1,2],[3,10],[12,16].
 This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].

 Quickly summarize 3 cases. Whenever there is intersection, created a new interval.
 
                             Current
                             |         |
      Case 1    New   |         |
      Case 2             |         |  New
      Case 3            New     |
                            |       New
                            | New  |
                           ---New---  
 */
public class InsertInterval {

	public ArrayList<Interval> insert(ArrayList<Interval> intervals,
			Interval newInterval) {

		ArrayList<Interval> result = new ArrayList<Interval>();

		for (Interval interval : intervals) {
			if (interval.end < newInterval.start) {
				result.add(interval);

			} else if (interval.start > newInterval.end) {
				result.add(newInterval);
				newInterval = interval;

			} else if (interval.end >= newInterval.start
					|| interval.start <= newInterval.end) {

				newInterval = new Interval(Math.min(interval.start,
						newInterval.start), Math.max(newInterval.end,
						interval.end));
			}
		}
		result.add(newInterval);
		return result;
	}
}