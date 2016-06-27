package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*[1,2,3] have the following permutations:
 [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].

 */
public class CC_8_4_Permutation {

	public void permutation(String str) {
		char[] chs = new char[str.length()];
		boolean[] visit = new boolean[str.length()];
		permutation(str, chs, 0, visit);
	}

	public void permutation(String str, char[] chs, int idx, boolean[] visit) {
		if (idx == str.length()) {
			System.out.println(Arrays.toString(chs));
		} else {
			for (int i = 0; i < str.length(); i++) {
				if(!visit[i]){
					visit[i] = true;
					chs[idx] = str.charAt(i);
					permutation(str, chs, idx + 1, visit);
					visit[i] = false;
				}					
			}

		}
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
		System.out.println("------------permutation---------------");
		p.permutation("1234");
		
		System.out.println("------------permutationI---------------");
		p.permutationI("1234");

		System.out.println("------------permutationII---------------");
		p.permutationII("1234");

		System.out.println("------------permutationIII---------------");
		p.permutationIII("1234");

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
