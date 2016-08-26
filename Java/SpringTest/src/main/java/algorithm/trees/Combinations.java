package algorithm.trees;

import java.util.ArrayList;

/*
 * Given two integers n and k, return all possible combinations of k numbers
 * out of 1 ... n.
 * 
 * For example, if n = 4 and k = 2, a solution is:
 * 
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 */
public class Combinations {
	public ArrayList<ArrayList<Integer>> combinationI(int n, int k) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		if (n <= 0 || n < k)
			return result;

		ArrayList<Integer> item = new ArrayList<Integer>();
		dfsI(n, k, 1, item, result); // because it need to begin from 1

		return result;
	}

	public ArrayList<ArrayList<Integer>> combinationII(int n, int k) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		if (n <= 0 || n < k)
			return result;

		ArrayList<Integer> item = new ArrayList<Integer>();
		dfsII(n, k, 1, item, result); // because it need to begin from 1

		return result;
	}

	public ArrayList<ArrayList<Integer>> combinationIII(int n, int k) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		if (n <= 0 || n < k)
			return result;

		ArrayList<Integer> item = new ArrayList<Integer>();
		dfsIII(n, k, 1, item, result); // because it need to begin from 1

		return result;
	}

	public ArrayList<ArrayList<Integer>> combinationIV(int n, int k) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		if (n <= 0 || n < k)
			return result;

		ArrayList<Integer> item = new ArrayList<Integer>();
		dfsIV(n, k, 1, item, result); // because it need to begin from 1

		return result;
	}

	private void dfsI(int n, int k, int idx, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> result) {
		if (list.size() == k) {
			result.add(new ArrayList<Integer>(list));
			return;
		}

		for (int i = idx; i <= n; i++) {
			list.add(i);
			dfsI(n, k, ++idx, list, result);
			list.remove(list.size() - 1);
		}
	}

	private void dfsII(int n, int k, int idx, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> result) {
		if (list.size() == k) {
			result.add(new ArrayList<Integer>(list));
			return;
		}

		for (int i = idx; i <= n; i++) {
			list.add(i);
			dfsII(n, k, idx++, list, result);
			list.remove(list.size() - 1);
		}
	}

	private void dfsIII(int n, int k, int idx, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> result) {
		if (list.size() == k) {
			result.add(new ArrayList<Integer>(list));
			return;
		}

		for (int i = idx; i <= n; i++) {
			list.add(i);
			dfsIII(n, k, idx + 1, list, result);
			list.remove(list.size() - 1);
		}
	}

	private void dfsIV(int n, int k, int idx, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> result) {
		if (list.size() == k) {
			result.add(new ArrayList<Integer>(list));
			return;
		}

		for (int i = 1; i <= n; i++) {
			list.add(i);
			dfsIV(n, k, idx + 1, list, result);
			list.remove(list.size() - 1);
		}
	}

	public void testI(int idx, int target) {
		if (idx == target)
			return;
		System.out.println("before - " + idx);
		testI(++idx, target);
		System.out.println("after - " + idx);
	}

	public void testII(int idx, int target) {
		if (idx == target)
			return;
		System.out.println("before - " + idx);
		testII(idx++, target);
		System.out.println("after - " + idx);
	}

	public void testIII(int idx, int target) {
		if (idx == target)
			return;
		System.out.println("before - " + idx);
		testIII(idx + 1, target);
		System.out.println("after - " + idx);
	}

	public void test_loopI(int idx, int target) {
		if (idx == target) {
			System.out.println("return - " + idx);
			return;
		}
		for (int i = idx; i <= target; i++) {
			System.out.println("before idx -> " + idx + ", i ->" + i);
			test_loopI(++idx, target);
			System.out.println("after idx -> " + idx + ", i ->" + i);
		}
	}

	public void test_loopIII(int idx, int target) {
		if (idx == target) {
			System.out.println("return - " + idx);
			return;
		}
		for (int i = idx; i <= target; i++) {
			System.out.println("before idx -> " + idx + ", i ->" + i);
			test_loopIII(idx + 1, target);
			System.out.println("after idx -> " + idx + ", i ->" + i);
		}
	}

	public static void main(String[] args) {
		Combinations ob = new Combinations();
		System.out.println("-------------- (combinationI) ++idx ------------------");
		System.out.println(ob.combinationI(4, 2));
		System.out.println("-------------- (combinationII) idx++ ------------------");
		System.out.println(ob.combinationII(4, 2));
		System.out.println("-------------- (combinationIII) idx + 1 ------------------");
		System.out.println(ob.combinationIII(4, 2));
		System.out.println("-------------- (combinationIV) i = 1 ------------------");
		System.out.println(ob.combinationIV(4, 2));
		System.out.println("-------------- ++idx ------------------");
		ob.testI(0, 3);
		System.out.println("-------------- idx++ ------------------");
		// ob.testII(0, 3);
		System.out.println("-------------- idx + 1 ------------------");
		ob.testIII(0, 3);
		System.out.println("-------------- loop ++idx ------------------");
		ob.test_loopI(0, 2);
		System.out.println("-------------- loop idx++ ------------------");
		// ob.testII(0, 3);
		System.out.println("-------------- loop idx + 1 ------------------");
		ob.test_loopIII(0, 2);
	}
}
