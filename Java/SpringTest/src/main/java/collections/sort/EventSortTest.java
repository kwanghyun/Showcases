package collections.sort;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class EventSortTest {
	private static final int MAX_EVENT_NUM = 10;

	List<SortableEvent> sortableEventList = new ArrayList<SortableEvent>();
	List<Event> eventList = new ArrayList<Event>();
	Map<String, Event> eventMap = new HashMap<String, Event>();

	@Before
	public void setUp() throws Exception {
		long time = 1434741020765L;

		for (int idx = 1; idx <= MAX_EVENT_NUM; idx++) {
			SortableEvent event = new SortableEvent();
			event.setId("id-" + idx);
			event.setTimestamp(time + idx * 10);
			sortableEventList.add(event);
		}

		for (int idx = 1; idx <= MAX_EVENT_NUM; idx++) {
			Event event = new SortableEvent();
			event.setId("id-" + idx);
			event.setTimestamp(time + idx * 10);
			eventList.add(event);
		}

		for (int idx = 1; idx <= MAX_EVENT_NUM; idx++) {
			Event event = new SortableEvent();
			event.setId("id-" + idx);
			event.setTimestamp(time + idx * 10);
			eventMap.put(event.getId(), event);
		}

	}

	public void printList(List<? extends Event> list) {
		int i = 0;
		for (Event ev : list) {
			i++;
			System.out.println(i + " :: " + ev.getId() + " || "
					+ ev.getTimestamp());
		}
		System.out.println(" ");
	}

	public void printMap(Map<String, Event> map) {
		int i = 0;
		for (Entry<String, Event> entry : map.entrySet()) {
			i++;
			System.out.println(i + " :: " + entry.getKey() + " || "
					+ entry.getValue().getTimestamp());
		}
		System.out.println(" ");
	}

	@Test
	public void testComparable() {
		Collections.shuffle(sortableEventList);
		printList(sortableEventList);
		Collections.sort(sortableEventList);
		printList(sortableEventList);
		assertEquals("id-1", sortableEventList.get(0).getId());
		assertEquals("id-10", sortableEventList.get(9).getId());
		System.out
				.println("---------------------- End of Test Comparable --------------------");
	}

	@Test
	public void testComparator() {
		Collections.shuffle(eventList);
		printList(eventList);
		Collections.sort(eventList, new EventComparator());
		printList(eventList);
		assertEquals("id-1", eventList.get(0).getId());
		assertEquals("id-10", eventList.get(9).getId());
		System.out
				.println("---------------------- End of Test Comparator --------------------");
	}

	@Test
	public void testMapComparator() {
		printMap(eventMap);
		TreeMap<String, Event> sortedMap = new TreeMap<String, Event>(
				new EventMapComparator(eventMap));
		sortedMap.putAll(eventMap);
		printMap(sortedMap);
		 assertEquals("id-1", eventList.get(0).getId());
		 assertEquals("id-10", eventList.get(9).getId());
	}

}
