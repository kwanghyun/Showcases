package algorithm.trees;


public class ShortestUniquePrefix {
	/*
	 * Use the shortest unique prefix to represent each word in the array
	 * 
	 * input: ["zebra", "dog", "duck", "dot"]
	 * output: {zebra: z, dog: do, duck: du, dot: d}
	 * 
	 * input: [zebra, dog, duck, dove]
	 * output: {zebra: z, dog: do, duck: du, dove: d}
	 * 
	 * input: [bearcat, bear]
	 * output: {bearcat: be, bear: b}
	 * 
	 * Do check your program's output for this input:
	 * input: [bbbb, bbb, bb, b]
	 * and this one
	 * 
	 * input: [b, bb, bbb, bbbb]
	 * 
	 * An Efficient Solution is to use Trie. The idea is to maintain a count in every node. 
	 * Below are steps.
	 * 1) Construct a Trie of all words. Also maintain frequency of every node 
	 * (Here frequency is number of times node is visited during insertion). 
	 * Time complexity of this step is O(N) where N is total number of characters in all words
	 */
	
	
}
