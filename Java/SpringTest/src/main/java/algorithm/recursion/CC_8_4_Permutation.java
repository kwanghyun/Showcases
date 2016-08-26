package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*[1,2,3] have the following permutations:
 [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].

 */
public class CC_8_4_Permutation {

	public void permutation(String str, List<Character> list, int idx) {
		if (idx == str.length()) {
			System.out.println(list);
			return;
		}

		for (int i = idx; i < str.length(); i++) {
			list.add(str.charAt(i));
			permutation(str, list, i + 1);
			list.remove(list.size() - 1);
		}
	}

	public void permutationI(String str, List<Character> list, int idx, boolean[] visited) {
		if (idx == str.length()) {
			System.out.println(list);
			return;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!visited[i]) {
				visited[i] = true;
				list.add(str.charAt(i));
				permutationI(str, list, idx + 1, visited);
				list.remove(list.size() - 1);
				visited[i] = false;
			}
		}
	}

	public void permutationII(String str, List<Character> list, int idx) {
		if (idx == str.length()) {
			System.out.println(list);
			return;
		}

		for (int i = 0; i < str.length(); i++) {
			list.add(str.charAt(i));
			permutationII(str, list, idx + 1);
			list.remove(list.size() - 1);
		}

	}

	private void permutationIII(List<Character> list, String str) {
		int strLen = str.length();
		if (strLen == 0) {
			System.out.println(list);
			return;
		}

		for (int i = 0; i < strLen; i++) {
			list.add(str.charAt(i));
			permutationIII(list, str.substring(0, i) + str.substring(i + 1, strLen));
			list.remove(list.size() - 1);
		}
	}

	public void permutation(String str) {
		List<Character> list = new ArrayList<>();
		permutation(str, list, 0);
	}

	public void permutationI(String str) {
		List<Character> list = new ArrayList<>();
		boolean[] visited = new boolean[str.length()];
		permutationI(str, list, 0, visited);
	}

	public void permutationII(String str) {
		List<Character> list = new ArrayList<>();
		permutationII(str, list, 0);
	}
	
	public void permutationIII(String str) {
		List<Character> list = new ArrayList<>();
		permutationIII(list, str);
	}

	public static void main(String[] args) {

		String targetStr = "123";
		// String targetStr = "hat";

		CC_8_4_Permutation p = new CC_8_4_Permutation();
		System.out.println("------------permutation---------------");
		p.permutation(targetStr);

		System.out.println("------------permutationI---------------");
		p.permutationI(targetStr);

		System.out.println("------------permutationII---------------");
		p.permutationII(targetStr);

		System.out.println("------------permutationIII---------------");
		p.permutationIII(targetStr);

	}
}
