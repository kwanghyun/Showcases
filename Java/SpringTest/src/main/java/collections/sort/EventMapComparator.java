package collections.sort;

import java.util.Comparator;
import java.util.Map;

public class EventMapComparator implements Comparator<String> {
	Map<String, Event> map;

	public EventMapComparator(Map<String, Event> base) {
		map = base;
	}

	public int compare(String id1, String id2) {
		double diff = map.get(id1).getTimestamp() - map.get(id2).getTimestamp();
		if (diff > 0)
			return 1;
		else if(diff == 0){
			return 0;
		}
		return -1;	
	}
}
