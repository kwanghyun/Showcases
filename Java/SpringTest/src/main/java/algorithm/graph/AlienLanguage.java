package algorithm.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/*
 * Given a sorted dictionary (array of words) of an alien language, find
 * order of characters in the language.
 * 
 * Examples:
 * 
	Input:  words[] = {"baa", "abcd", "abca", "cab", "cad"}
	Output: Order of characters is 'b', 'd', 'a', 'c'
	Note that words are sorted and in the given language "baa" 
	comes before "abcd", therefore 'b' is before 'a' in output.
	Similarly we can find other orders.
	
	Input:  words[] = {"caa", "aaa", "aab"}
	Output: Order of characters is 'c', 'a', 'b'
 */

public class AlienLanguage {
	HashMap<Character, HashSet<Character>> graph = new HashMap<>();

	public void buildGraph(String words[]) {
		for (int i = 1; i < words.length; i++) {
			String str1 = words[i - 1];
			String str2 = words[i];
			for (int j = 0; j < Math.min(str1.length(), str2.length()); j++) {
				char ch1 = str1.charAt(j);
				char ch2 = str2.charAt(j);

				if (!graph.containsKey(ch1)) {
					graph.put(ch1, new HashSet<Character>());
				}
				if (!graph.containsKey(ch1)) {
					graph.put(ch2, new HashSet<Character>());
				}

				if (ch1 != ch2) {
					HashSet<Character> adjs = graph.get(ch1);
					adjs.add(ch2);
					graph.put(ch1, adjs);
					break;
				}
			}
		}
		System.out.println(graph);
		topologicalSort();
	}

	public void topologicalSort() {
		HashSet<Character> visited = new HashSet<>();
		Stack<Character> stack = new Stack<>();

		System.out.println(graph.keySet());

		for (char vertex : graph.keySet()) {
			if (!visited.contains(vertex)) {
				dfs(vertex, visited, stack);
			}
		}
		while (!stack.isEmpty()) {
			System.out.format("%3c,", stack.pop());
		}
	}

	int idx = 1;

	public void dfs(char vertex, HashSet<Character> visited, Stack<Character> stack) {
		System.out.println((idx++) + " : " + vertex);
		visited.add(vertex);
		for (char adj : graph.get(vertex)) {
			if (!visited.contains(adj)) {
				dfs(adj, visited, stack);
			}
		}

		stack.push(vertex);
	}

	public static void main(String[] args) {
		AlienLanguage ob = new AlienLanguage();
		String[] words = { "baa", "abcd", "abca", "cab", "cad" };
		ob.buildGraph(words);

	}
}
