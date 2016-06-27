package algorithm.recursion;

import java.util.Arrays;

/*
 * Generating combinations of k elements: Generating combinations of k
 * elements from the given set follows similar algorithm used to generate
 * all permutations, but since we don't want to repeat an a character even
 * in a different order we have to force the recursive calls to not to
 * follow the branches that repeat a set of characters.
 * 
 * If the given string is "ABC" and k = 2, our recursive tree will look like
 * this:
 *            START
 *            /        |  
 *          A         B
 *        /     \      | 
 *      B      C     C
 * 
 * Here we will have to make sure, once we start a "branch" from a node
 * (character), we must not come back to that node (character) again to
 * start another "branch". So, starting off a new recursive call (to
 * traverse a new "branch") must start from the following node (character)!
 */

public class Combination {

	public void combination(char[] arr, int k, int startId, char[] branch, int numElem) {
		if (numElem == k) {
			System.out.println(Arrays.toString(branch));
			return;
		}

		for (int i = startId; i < arr.length; ++i) {
			branch[numElem++] = arr[i];
			combination(arr, k, ++startId, branch, numElem);
			--numElem;
		}
	}
	
	public void combinationWithDup(char[] arr, int k, int startId, char[] branch, int numElem) {
		if (numElem == k) {
			System.out.println(Arrays.toString(branch));
			return;
		}

		for (int i = 0; i < arr.length; ++i) {
			branch[numElem++] = arr[i];
			combinationWithDup(arr, k, ++startId, branch, numElem);
			--numElem;
		}
	}
	
	public static void main(String[] args) {
		Combination c = new Combination();
		int k = 2;
		char[] input = "ABCD".toCharArray(); 
		char[] branch = new char[k];
		c.combination(input, k, 0, branch, 0);
		System.out.println("--------------------");
		c.combinationWithDup(input, k, 0, branch, 0);
	}
}
