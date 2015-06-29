package collections.sort;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

	public int compare(Event event1, Event event2) {
		double diff = event1.getTimestamp() - event2.getTimestamp();
		if (diff > 0)
			return 1;
		else if(diff == 0){
			return 0;
		}
		return -1;	
	}
}
