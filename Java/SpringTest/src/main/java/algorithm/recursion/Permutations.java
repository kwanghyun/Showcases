package algorithm.recursion;

import java.util.Arrays;

/*
 * Permutations are for lists (order matters) and combinations are for
 * groups (order doesn’t matter).
 * 
 * A joke: A "combination lock" should really be called a "permutation lock"
 * . The order you put the numbers in matters. (A true "combination lock"
 * would accept both 10-17-23 and 23-17-10 as correct.)
 * 
 * Here’s a few examples of combinations (order doesn’t matter) from
 * permutations (order matters).
 * 
 * Combination: Picking a team of 3 people from a group of 10. C(10,3) =
 * 10!/(7! · 3!) = 10 · 9 · 8 / (3 · 2 · 1) = 120.
 * 
 * Permutation: Picking a President, VP and Waterboy from a group of 10.
 * P(10,3) = 10!/7! = 10 · 9 · 8 = 720.
 */

/*
 * Generating all permutations of a string(of characters):
 * 
 * The key to understanding how we can generate all permutations of a given
 * string is to imagine the string(which is essentially a set of
 * characters)as a complete graph where the nodes are the characters of the
 * string.This basically reduces the permutations generating problem into a
 * graph traversal problem:given a complete graph,visit all nodes of the
 * graph without visiting any node twice.How many different ways are there
 * to traverse such a graph?
 * 
 * We can use Depth First Search (DFS) traversal technique to traverse this
 * graph of characters. The important thing to keep in mind is that we must
 * not visit a node twice in any "branch" of the depth-first tree that runs
 * down from a node at the top of the tree to the leaf which denotes the
 * last node in the current "branch".
 */
/*	
*	       START
*	 /          |           \
*	A          B            C
*	/   \      /  \         /   \
* B    C   A    C      A    B
* |     |    |     |      |      |
* C    B   C    A      B     A
* 
*/
public class Permutations {
	void generatePermutations(char[] arr, char[] branch, int level, boolean[] visited) {
		if (level >= arr.length ) {
			System.out.println(branch);
			return;
		}

		for (int i = 0; i < arr.length; i++) {
			if (!visited[i]) {
				branch[level] = arr[i];
				visited[i] = true;
				generatePermutations(arr, branch, level + 1, visited);
				visited[i] = false;
				level--;
			}
		}
	}

	void generatePermutationsWithDup(char[] arr, char[] branch, int level) {
		if (level >= arr.length - 1) {
			System.out.println(branch);
			return;
		}

		for (int i = 0; i < arr.length; i++) {
			branch[++level] = arr[i];
			generatePermutationsWithDup(arr, branch, level);
			level--;
		}
	}

	public static void main(String[] args) {
		Permutations p = new Permutations();
		String str = "ABCD";
		int n = str.length();
		char[] arr = str.toCharArray();
		boolean[] visited = new boolean[n];
		for (int i = 0; i < n; i++)
			visited[i] = false;
		char[] branch = new char[n];
		p.generatePermutations(arr, branch, 0, visited);
		System.out.println("-------------------------");
		p.generatePermutationsWithDup(arr, branch, -1);
	}
}
