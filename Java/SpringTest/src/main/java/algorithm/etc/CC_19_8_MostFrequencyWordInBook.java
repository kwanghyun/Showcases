package algorithm.etc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.h2.expression.Function;

/*
 * Design a method to find the frequency of occurrences of any given word in a book.
 */
public class CC_19_8_MostFrequencyWordInBook {
	
	public String findMaxFreqeuncy(String[] book){
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int max =0 ;
		String maxWord = "";
		
		for(String word : book){
			word = word.trim();
			
			if(map.containsKey(word.toLowerCase())){
				int value = map.get(word);
				map.put(word,  value++);
				if(max < value ){
					max = value ;
					maxWord = word;
				}
					
			}else{
				map.put(word, 1);
			}
		}
		return maxWord;
	}
	
	/*
	 * Java 8
	 * */ 
	public HashMap<String, Integer> wordCountwithJava8(String[] book) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int max = 0;
		String maxWord = "";

		map = (HashMap<String, Integer>) Arrays.stream(book)
				.collect(Collectors.groupingBy(String::toLowerCase, Collectors.summingInt(s -> 1)));
		return map;
	}
	
	public static void main(String args[]){
		String [] book = {"apple","orage","apple","selk","apple","mongo","drama"};
		CC_19_8_MostFrequencyWordInBook ob = new CC_19_8_MostFrequencyWordInBook();
		System.out.println(ob.findMaxFreqeuncy(book));
		
		System.out.println(ob.wordCountwithJava8(book));
	}

}
