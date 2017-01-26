package algorithm.stringArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/* Top K Frequent Elements */
public class KFrequentElement {

	class QNode implements Comparable<QNode> {
		int key;
		int value;

		public QNode(int key, int value) {
			this.key = key;
			this.value = value;
		}

		public int compareTo(QNode n) {
			return value - n.value;
		}

	}

	public List<Integer> topKFrequent(int[] nums, int k) {
		List<Integer> result = new ArrayList<>();
		if (nums == null || nums.length == 0)
			return result;

		Queue<QNode> q = new PriorityQueue<>();
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < nums.length; i++) {
			if (!map.containsKey(nums[i])) {
				map.put(nums[i], 1);
			} else {
				map.put(nums[i], map.get(nums[i]) + 1);
			}
		}

		for (int key : map.keySet()) {
			q.offer(new QNode(key, map.get(key)));
			if (q.size() > k)
				q.poll();

		}
		System.out.println("map = " + map + "q = " + q);

		for (int i = 0; i < nums.length; i++) {
			QNode curr = q.poll();
			if (curr != null)
				result.add(curr.key);
			else {
				break;
			}
		}

		return result;
	}

	public List<Integer> topKFrequentI(int[] nums, int k) {

		List<Integer>[] bucket = new List[nums.length + 1];
		Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

		for (int n : nums) {
			frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
		}

		for (int key : frequencyMap.keySet()) {
			int frequency = frequencyMap.get(key);
			if (bucket[frequency] == null) {
				bucket[frequency] = new ArrayList<>();
			}
			bucket[frequency].add(key);
		}

		List<Integer> res = new ArrayList<>();

		for (int pos = bucket.length - 1; pos >= 0 && res.size() < k; pos--) {
			if (bucket[pos] != null) {
				res.addAll(bucket[pos]);
			}
		}
		return res;
	}

	public static void main(String[] args) {
		KFrequentElement ob = new KFrequentElement();
		int[] nums = { 1, 1, 1, 2, 2, 3 };
		int k = 2;
		System.out.println(ob.topKFrequent(nums, k));
		System.out.println(ob.topKFrequentI(nums, k));
	}
}
