package algorithm.trees;

import java.util.ArrayList;
import java.util.List;

/*
 * Given an array of meeting time intervals consisting of start and end
 * times [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend
 * all meetings.
 * 
 * For example, Given [[0, 30],[5, 10],[15, 20]], return false.
 */

public class MeetingRooms {

	// TODO USE Interval Tree
	IntervalSearchTree ist = new IntervalSearchTree();

	public boolean canAttendMeetings(Interval[] intervals) {
		if (intervals == null || intervals.length == 0)
			return true;

		for (int i = 1; i < intervals.length; i++) {
			int start = intervals[i].start;
			int end = intervals[i].end;
			if (ist.getOverlap(start, end) != null)
				return false;
			else
				ist.add(start, end);
		}

		return true;
	}

	public boolean canAttendMeetingsI(Interval[] intervals) {
		if (intervals == null || intervals.length == 0)
			return true;
		List<Interval> attendable = new ArrayList<>();
		attendable.add(intervals[0]);
		for (int i = 1; i < intervals.length; i++) {
			Interval newInt = intervals[i];

			for (int j = 0; j < attendable.size(); j++) {
				Interval curr = attendable.get(j);
				// System.out.println("new = " + newInt + ", curr = " + curr);
				if (newInt.end > curr.start && newInt.start < curr.end)
					return false;
			}
			attendable.add(newInt);
			System.out.println(attendable);
		}
		return true;
	}

	public static void main(String[] args) {
		Interval n1 = new Interval(5, 8);
		Interval n2 = new Interval(6, 8);
		Interval[] arr = { n1, n2 };
		MeetingRooms ob = new MeetingRooms();
		System.out.println(ob.canAttendMeetings(arr));
	}
}
