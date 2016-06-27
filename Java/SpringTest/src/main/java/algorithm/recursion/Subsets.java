package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Given a set of distinct integers, S, return all possible subsets.
 * 
 * Note: 1) Elements in a subset must be in non-descending order. 2) The
 * solution set must not contain duplicate subsets.
 * 
 * For example, given S = [1,2,3], the method returns:
 * [ [3], [1], [2], [1,2,3], [1,3], [2,3], [1,2], [] ]
 * 
 * Given a set S of n distinct integers, there is a relation between Sn and
 * Sn-1. The subset of Sn-1 is the union of {subset of Sn-1} and {each
 * element in Sn-1 + one more element}. 
 */
public class Subsets {
	public ArrayList<ArrayList<Integer>> subsets(int[] S) {
		if (S == null)
			return null;

		Arrays.sort(S);

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < S.length; i++) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();

			// get sets that are already in result
			for (ArrayList<Integer> a : result) {
				temp.add(new ArrayList<Integer>(a));
			}

			// add S[i] to existing sets
			for (ArrayList<Integer> a : temp) {
				a.add(S[i]);
			}

			// add S[i] only as a set
			ArrayList<Integer> single = new ArrayList<Integer>();
			single.add(S[i]);
			temp.add(single);

			result.addAll(temp);
		}

		// add empty set
		result.add(new ArrayList<Integer>());

		return result;
	}

public void subsetsI(int[] arr, int idx, ArrayList<ArrayList<Integer>> result, ArrayList<Integer> list) {
	if (idx == arr.length)
		return;
	list.add(arr[idx]);
	ArrayList<Integer> temp = new ArrayList<>(list);
	result.add(temp);
	subsetsI(arr, idx + 1, result, list);
	list.remove(list.size() - 1);
	subsetsI(arr, idx + 1, result, list);
}
	
	public ArrayList<ArrayList<Integer>> subsetsI(int[] S){
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		subsetsI(S, 0, result, list);
		result.add(new ArrayList<Integer>());
		return result;
	}

	public static void main(String[] args) {
		Subsets ob = new Subsets();
		int[] arr = { 1, 2, 3,4,5,6,7,8,9,10};
		System.out.println(ob.subsets(arr));
		System.out.println("------------------------------");
		System.out.println(ob.subsetsI(arr));
	}
}
