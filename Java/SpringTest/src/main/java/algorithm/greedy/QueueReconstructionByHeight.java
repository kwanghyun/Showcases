package algorithm.greedy;

import java.util.*;

/*
 * Suppose you have a random list of people standing in a queue. Each person
 * is described by a pair of integers (h, k), where h is the height of the
 * person and k is the number of people in front of this person who have a
 * height greater than or equal to h. Write an algorithm to reconstruct the
 * queue.
 * 
 * Note: The number of people is less than 1,100.
 * 
 * Example
 * 
 * Input: [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
 * 
 * Output: [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
 */
public class QueueReconstructionByHeight {
	/*
	 * Greedy Algorithms We only consider about the taller or equal one. The
	 * shortest people with smallest k is useless for others. He should be
	 * ranked lastest. Conversely the tallest one with largest k is most
	 * powerful, who should be ranked first.
	 * 
	 * 1 order, sort according to h DESC and k ASC
	 * 
	 * 2 reorder, insert from tall to short according to k
	 */
	public int[][] reconstructQueue(int[][] people) {
		if ((people == null) || (people.length == 0)) {
			return new int[0][0];
		}

		Arrays.sort(people, (p1, p2) -> {
			int cmp = Integer.compare(p1[0], p2[0]);
			if (cmp != 0) {
				return -cmp; // DESC
			} else {
				return Integer.compare(p1[1], p2[1]); // ASC
			}
		});

		List<int[]> result = new LinkedList<>();
		for (int[] p : people) {
			result.add(p[1], p); // insert by k
		}

		return result.stream().toArray(int[][]::new);
	}
}
