package algorithm.stringArray;

public class RemoveDuplicatedChars {
	/*
	 * 1. Ask Questions, understand problem clearly
	 * 2. Write pseudo first
	 * 3. Write Code
	 * 4. Test - Normal, Abnormal cases.
	 * 5. Debug - think before change
	 */
	
	
//	Design an algorithm and write code to remove the duplicate characters in a string 
//	without using any additional buffer. 
//	NOTE: One or two additional variables are fine. An extra copy of the array is not.
//	FOLLOW UP
//	Write the test cases for this method.
	
/*
 * @Image
 * 
 * KeyPoint : Check weather returnArr have duplicated char or not.   
 * 
 * index
 * for(i; a, b, c, d)
 *  	for (j ; _ _ _ _) 
 * 		if(str[i] == str[j]) break;
 * 	if(index == j) //no ducplication
 * 		returnArr[index++] = str[i]	
 */
	
	public String solution1(String str){
		if(str == null)
			return null;
		if(str.length() < 2)
			return str;

		char[] returnArr = new char[str.length()];
		int index = 0;
		int j;
		for(int i = 0; i< str.length(); i++){
			for( j = 0; j < index; j++ ){
				if(returnArr[j] == str.charAt(i))
					break;
			}
			if(index == j){ //no duplication found
				returnArr[index++] = str.charAt(i);
			}

		}
		return new String(returnArr);
	}
	
	public String solution2(String str){
		if(str == null)
			return null;
		if(str.length() < 2)
			return str;
		StringBuilder returnStr = new StringBuilder();
//		char[] returnArr = new char[str.length()];
		int index = 0;
		int j;
		for(int i = 0; i< str.length(); i++){
			for( j = 0; j < index; j++ ){
//				if(returnArr[j] == str.charAt(i))
				if(returnStr.charAt(j) == str.charAt(i))
					break;
			}
			if(index == j){ //no duplication found
//				returnArr[index++] = str.charAt(i);
				returnStr.append( str.charAt(i));
				index++;
			}

		}
		return returnStr.toString();
	}
	
	public static void main (String argsp[]){
		RemoveDuplicatedChars rdc = new RemoveDuplicatedChars();
		System.out.println(rdc.solution1("abcd"));
		System.out.println(rdc.solution1("abbb"));
		System.out.println(rdc.solution1("abab"));
		System.out.println(rdc.solution1("a"));
		System.out.println(rdc.solution1(null));
	}
	
	/* TEST CASES
	 * Normal
	 * 1. abcd
	 * 2. abab
	 * 3. aaaa
	 * 4. a
	 * Abnormal
	 * 1. Null String
	 * 
	 * 
	 */
}
