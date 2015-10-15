package algorithm.trees;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Given a set of candidate numbers (C) and a target number (T), find all unique combinations
 in C where the candidate numbers sums to T. The same repeated number may
 be chosen from C unlimited number of times.
 Note: All numbers (including target) will be positive integers. Elements in a combination
 (a1, a2, ... , ak) must be in non-descending order. (ie, a1 <= a2 <= ... <= ak). The
 solution set must not contain duplicate combinations. For example, given candidate
 set 2,3,6,7 and target 7, A solution set is:
 [7]
 [2, 2, 3]

 The first impression of this problem should be depth-first search(DFS). To solve DFS
 problem, recursion is a normal implementation.
 Note that the candidates array is not sorted, we need to sort it first.
 */
public class CombinationSum {
	public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates,
			int target) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		if (candidates == null || candidates.length == 0)
			return result;

		ArrayList<Integer> current = new ArrayList<Integer>();
		Arrays.sort(candidates);

		combinationSum(candidates, target, 0, current, result);

		return result;
	}

	public void combinationSum(int[] candidates, int target, int j,
			ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> result) {

		if (target == 0) {
			ArrayList<Integer> temp = new ArrayList<Integer>(curr);
			System.out.println("Add   : target => " + target + ", j => " + j
					+ ", curr : " + curr);
			result.add(temp);
			return;
		}

		for (int i = j; i < candidates.length; i++) {
			if (target < candidates[i]) {
				System.out.println("Return: target => " + target + ", i => "
						+ i + ", curr : " + curr);
				return;
			}

			curr.add(candidates[i]);
			System.out.println("Before : target => " + target + ", i => " + i
					+ ", curr : " + curr);
			System.out.println("-----------------");
			combinationSum(candidates, target - candidates[i], i, curr, result);
			curr.remove(curr.size() - 1);
			System.out.println("After   : target => " + target + ", i => " + i
					+ ", curr : " + curr);
			System.out.println("-----------------");
		}
	}

	public ArrayList<ArrayList<Integer>> solution(int[] arr, int sum) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		Arrays.sort(arr);
		solution(arr, sum, result, list);
		return result;
	}

	public void solution(int[] arr, int sum,
			ArrayList<ArrayList<Integer>> result, ArrayList<Integer> list) {

		if (sum == 0) {
			ArrayList<Integer> newlist = new ArrayList<Integer>();
			newlist.addAll(list);
			result.add(newlist);
			return;
		}

		for (int i = 0; i < arr.length; i++) {
			if (sum < arr[i])
				return;
			list.add(arr[i]);
			solution(arr, sum - arr[i], result, list);
			list.remove(list.size() - 1);
		}
	}

	public static void main(String[] args) {
		CombinationSum obj = new CombinationSum();
		int[] candidates = { 2, 3, 6 };
		ArrayList<ArrayList<Integer>> result1 = obj.solution(candidates, 7);
		 obj.combinationSum(candidates, 7);
		 System.out.println(result1);
		 System.out.println("---------------------------------------------");
		ArrayList<ArrayList<Integer>> result2 = obj.solution(candidates, 7);
		System.out.println(result2);

	}
}
