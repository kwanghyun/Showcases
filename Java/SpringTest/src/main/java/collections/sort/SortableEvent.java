package collections.sort;

public class SortableEvent extends Event implements Comparable<Event> {

	public int compareTo(Event event) {
		double diff = this.getTimestamp() - event.getTimestamp();
		if (diff > 0)
			return 1;
		else if(diff == 0){
			return 0;
		}
		return -1;	
	}
}
