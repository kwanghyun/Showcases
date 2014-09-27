package algorithm.stringArray;

public class StringAllUniqueChar {
	// Check string has all unique characters. What if you can not use
	// additional data structures?

	public boolean checkUniqueChars(String str){
		char[] charArr = str.toCharArray();
		for(int i = 0; i < charArr.length; i++ ){
			for(int j = i+1; j < charArr.length; j++){
				if(charArr[i] ==  charArr[j])
					return false;
			}
		}
		return true;
	}
	
	public boolean checkUniqueChars2(String str){
		boolean[] char_set = new boolean[256];
		for(int i =0; i<str.length(); i++){
			int val = str.charAt(i);
			if(char_set[val]) 
				return false;
			char_set[val] = true;
		}
		return true;
	}
	
	
	public static void main(String args[]){
		StringAllUniqueChar obj = new StringAllUniqueChar();
		String testString = "asdfgh";
		System.out.println(obj.checkUniqueChars(testString));		
		System.out.println(obj.checkUniqueChars2(testString));
	}
}
