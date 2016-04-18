package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*[1,2,3] have the following permutations:
 [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].

 */
public class CC_8_4_Permutation {

	public ArrayList<ArrayList<Integer>> permute(int[] num) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		// start from an empty list
		result.add(new ArrayList<Integer>());

		// num = [1,2,3]
		for (int i = 0; i < num.length; i++) {
			// list of list in current iteration of the array num
			ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();

			for (ArrayList<Integer> item : result) {
				// # of locations to insert is largest index + 1

				for (int j = 0; j < item.size() + 1; j++) {
					// + add num[i] to different locations
					item.add(j, num[i]);

					ArrayList<Integer> temp = new ArrayList<Integer>(item);
					current.add(temp);
					// System.out.println(temp);
					// - remove num[i] add
					item.remove(j);
				}
			}
			result = new ArrayList<ArrayList<Integer>>(current);
		}
		return result;
	}

	public ArrayList<String> permutation(String str) {
		ArrayList<String> permutations = new ArrayList<String>();
		if (str == null)
			return null;

		if (str.length() == 0) {
			// without this size program doesn't work...
			permutations.add(" ");
			return permutations;
		}

		char first = str.charAt(0);
		String remainder = str.substring(1);

		ArrayList<String> candidates = permutation(remainder);

		for (String candidate : candidates) {

			for (int i = 0; i < candidate.length(); i++) {
				String before = candidate.substring(0, i);
				String end = candidate.substring(i, str.length());
				permutations.add(new String(before + first + end));
			}
		}
		return permutations;
	}

	public void permutationI(String str) {
		char[] chs = new char[str.length()];
		permutationI(chs, str, 0);
	}

	private void permutationI(char[] chs, String str, int idx) {
		int n = str.length();
		if (n == 0)
			System.out.println(chs);
		else {
			for (int i = 0; i < n; i++) {
				chs[idx] = str.charAt(i);
				permutationI(chs, str.substring(0, i) + str.substring(i + 1, n), idx + 1);
			}
		}
	}

	public void permutationII(String str) {
		List<Character> list = new ArrayList<>();
		permutationII(list, str);
	}

	private void permutationII(List<Character> list, String str) {
		int n = str.length();
		if (n == 0)
			System.out.println(list);
		else {
			for (int i = 0; i < n; i++) {
				list.add(str.charAt(i));
				permutationII(list, str.substring(0, i) + str.substring(i + 1, n));
				list.remove(list.size() - 1);
			}
		}
	}

	public void permutationIII(String str) {
		permutationIII("", str);
	}

	private void permutationIII(String prefix, String str) {
		int n = str.length();
		if (n == 0)
			System.out.println(prefix);
		else {
			for (int i = 0; i < n; i++)
				permutationIII(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
		}
	}

	public static void main(String[] args) {

		// by adding " ", array 0 max, size 0
		List<String> list1 = new ArrayList<String>();
		list1.add("");

		// by adding " ", initializing array 10 max, size 1
		List<String> list2 = new ArrayList<String>();
		list2.add(" ");

		CC_8_4_Permutation p = new CC_8_4_Permutation();

		System.out.println("---------------------------");
		p.permutationIII("1234");

		System.out.println("---------------------------");
		p.permutationI("1234");

		System.out.println("---------------------------");
		p.permutationII("1234");

		// System.out.println("COUNT : " + list.size());
		// System.out.println("===================");
		// for (String str : list)
		// System.out.println("String : [" + str + "] " + "Length : ["
		// + str.length() + "]");
		//

		// int[] arr = {1,2,3};
		// ArrayList<ArrayList<Integer>> lists = p.permute(arr);
		// System.out.println("size :: "+lists.size());
		// for (ArrayList<Integer> list : lists)
		// System.out.println(list);

	}
}
