package algorithm.etc;

import java.util.*;

/*
 * Design a hit counter which counts the number of hits received in the past
 * 5 minutes.
 * 
 * Each function accepts a timestamp parameter (in seconds granularity) and
 * you may assume that calls are being made to the system in chronological
 * order (ie, the timestamp is monotonically increasing). You may assume
 * that the earliest timestamp starts at 1.
 * 
 * It is possible that several hits arrive roughly at the same time.
 * 
 * Example: HitCounter counter = new HitCounter();
 * 
 * // hit at timestamp 1. counter.hit(1);
 * 
 * // hit at timestamp 2. counter.hit(2);
 * 
 * // hit at timestamp 3. counter.hit(3);
 * 
 * // get hits at timestamp 4, should return 3. counter.getHits(4);
 * 
 * // hit at timestamp 300. counter.hit(300);
 * 
 * // get hits at timestamp 300, should return 4. counter.getHits(300);
 * 
 * // get hits at timestamp 301, should return 3. counter.getHits(301);
 * Follow up: What if the number of hits per second could be very large?
 * Does your design scale?
 */

public class HitCounter {
	Map<Integer, Integer> counter;
	int offset = 0;
	int sum = 0;
	int last_ts = 0;

	/** Initialize your data structure here. */
	public HitCounter() {
		counter = new HashMap<>();
	}

	/**
	 * Record a hit.
	 * 
	 * @param timestamp
	 *            - The current timestamp (in seconds granularity).
	 */
	public void hit(int timestamp) {
		if (timestamp - last_ts > 300) {
			counter.clear();
			offset = timestamp - 300;
			sum = 0;
		}

		int count = counter.getOrDefault(timestamp, 0);
		counter.put(timestamp, count + 1);
		if (last_ts == timestamp) {
			sum++;
		} else {
			sum += counter.get(timestamp);
		}

		last_ts = timestamp;
	}

	/**
	 * Return the number of hits in the past 5 minutes.
	 * 
	 * @param timestamp
	 *            - The current timestamp (in seconds granularity).
	 */
	public int getHits(int timestamp) {

		for (int i = offset; i <= timestamp - 300; i++) {
			if (counter.containsKey(i)) {
				sum -= counter.get(i);
				counter.remove(i);
			}
		}

		if (offset - 300 > 0)
			offset = timestamp - 300;
		return sum;
	}
}
