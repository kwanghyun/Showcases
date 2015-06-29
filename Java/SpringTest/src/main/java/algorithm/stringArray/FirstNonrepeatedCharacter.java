package algorithm.stringArray;

import java.util.HashMap;
import java.util.Map;

public class FirstNonrepeatedCharacter {
	
	public Character firstNonRepeated(String str) {
		if (str == null)
			return null;
		Map<Character, Integer> mapper = new HashMap<Character, Integer>();

		Character ch;
		int length = str.length();
		for (int idx = 0; idx < length; idx++) {
			ch = str.charAt(idx);
			if (mapper.containsKey(ch)) {
				mapper.put(ch, mapper.get(ch) + 1);
			} else {
				mapper.put(ch, 1);
			}
		}
			
		for (int i = 0; i < length; i++) {
			ch = str.charAt(i);
			if (mapper.get(ch) == 1)
				return ch;
		}
		
		return null;
	}
}
