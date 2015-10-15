package files.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*There are several words in a file. Get the occurrence of every word 
 * and sort it based on the occurrence, if more than one word is having 
 * same occurrence than sort it alphabetically.*/
public class WordCount {

	public TreeMap<String, Integer> wordCount() throws IOException {
		
		File file = new File("C:/words.txt");
		BufferedReader reader = new BufferedReader( new FileReader(file));
		HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
		
		String line = null;
		while((line = reader.readLine()) != null){
			line = line.replaceAll("[^\\w\\s]", "");
			String[] words = line.split("\\s+");
			
			for(String word : words){
				if(wordsMap.containsKey(word)){
					wordsMap.put(word, wordsMap.get(word) + 1);
				}else{
					wordsMap.put(word, 1);
				}
			}
		}
		
		TreeMap<String, Integer> sortedResult = new TreeMap<String, Integer>(
				new WordsComparator(wordsMap));
		sortedResult.putAll(wordsMap);
		
		return sortedResult;
	}

	public class WordsComparator implements Comparator<String> {
		HashMap<String, Integer> baseMap;

		public WordsComparator(HashMap<String, Integer> map) {
			this.baseMap = map;
		}

		@Override
		public int compare(String word1, String word2) {
			if (baseMap.get(word1) == baseMap.get(word2)) {
				return word1.compareToIgnoreCase(word2);
			} else {
				return baseMap.get(word1) - baseMap.get(word2);
			}
		}
	}
	
	public static void main(String[] args) {
		
		WordCount obj = new WordCount();
		try {
			TreeMap<String, Integer> result = obj.wordCount();
			for(Map.Entry<String, Integer> wordSet : result.entrySet()){
				System.out.println(wordSet);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
