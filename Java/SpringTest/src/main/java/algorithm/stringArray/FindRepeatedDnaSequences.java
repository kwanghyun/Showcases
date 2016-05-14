package algorithm.stringArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * A DNA is composed of a series of nucleotides abbreviated as A, C, G, and
 * T, for example: 'ACGAATTCCG'. Write a function to find all the
 * 10-letter-long sequences(sub-strings) that occur more than once in a DNA
 * molecule. Example - Input: 'AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT', Expected
 * Output: [AAAAACCCCC,CCCCCAAAAA]
 */

/*
 * A naive approach of sliding the 10-letter window across the given
 * sequence combined with hashmap would take O(n^2) time. Using rolling hash
 * method takes O(n) time. Here are the steps.
 * 
 * 1. Compute hash value for the sequence in the first window.
 * 2. Store the computed value in a set/hashmap.
 * 3. Compute hash values for subsequent sequence(which would be sequence
 * obtained by sliding 10-letter window to the right by one character) using
 * rolling hash method. Rolling hash method makes use of hash value computed
 * for previous sequence to compute hash value for current sequence.
 * 4. If computed hash value is already present in the hashmap then add the
 * current sequence to output set, else store the computed value in the
 * hashmap. .
 * 5. Repeat step #3, #4 until all the 10-letter sequences are completed.
 * 
 * Rolling Hash computation uses following method. 
 * 
 * currHash = prevHash - val(skippedChar)*2^10
 * currHash = currHash * 2
 * currHash = currHash + 2*val(newChar)
 */
public class FindRepeatedDnaSequences {

	private static final Map<Character, Integer> encodings = new HashMap<>();
	static {
		encodings.put('A', 0);
		encodings.put('C', 1);
		encodings.put('G', 2);
		encodings.put('T', 3);
	}
	private final int Two_POW_9 = (int) Math.pow(2, 9);

	public List<String> findRepeatedDnaSequences(String s) {

		Set<String> res = new HashSet<>();
		Map<Integer, String> duplicates = new HashMap<>();

		for (int i = 0, rhash = 0; i < s.length(); i++) {
			if (i > 9)
				rhash -= Two_POW_9 * encodings.get(s.charAt(i - 10));

			rhash = 2 * rhash + encodings.get(s.charAt(i));

			if (i > 8) {
				if (duplicates.get(rhash) != null) {
					res.add(s.substring(i - 9, i + 1));
				} else {
					duplicates.put(rhash, "");
				}
			}
		}
		return new ArrayList<>(res);
	}

	public static void main(String[] args) {
		FindRepeatedDnaSequences sln = new FindRepeatedDnaSequences();
		List<String> list = sln.findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT");
//		List<String> list = sln.findRepeatedDnaSequences("AACAAAAACAAAACCAAAAACAAAAACAAAA");

		System.out.println("Repeated DNA sequences are: ");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
