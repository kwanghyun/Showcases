package algorithm.trees;

import java.util.HashMap;
import java.util.Map;

/*Implement a trie with insert, search, and startsWith methods.
 * 
 * A trie node should contains the character, its children and the flag that marks if it is a
 leaf node.
 * */
class TrieNode {
	char c;
	HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
	boolean isLeaf;

	public TrieNode() {
	}

	public TrieNode(char c) {
		this.c = c;
	}
}

public class Trie {

	private TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	// Inserts a word into the trie.
	public void insert(String word) {
		HashMap<Character, TrieNode> children = root.children;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			TrieNode curr;
			if (children.containsKey(c)) {
				curr = children.get(c);
			} else {
				curr = new TrieNode(c);
				children.put(c, curr);
			}

			children = curr.children;
			// set leaf node
			if (i == word.length() - 1)
				curr.isLeaf = true;
		}
	}

	// Returns if the word is in the trie.
	public boolean search(String word) {
		TrieNode node = searchNode(word);
		if (node != null && node.isLeaf)
			return true;
		else
			return false;
	}

	// Returns if there is any word in the trie
	// that starts with the given prefix.
	public boolean startsWith(String prefix) {
		if (searchNode(prefix) == null)
			return false;
		else
			return true;
	}

	public TrieNode searchNode(String str) {
		Map<Character, TrieNode> children = root.children;
		TrieNode curr = null;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (children.containsKey(c)) {
				curr = children.get(c);
				children = curr.children;
			} else {
				return null;
			}
		}

		return curr;
	}
}
