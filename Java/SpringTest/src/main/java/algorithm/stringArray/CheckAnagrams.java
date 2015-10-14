package algorithm.stringArray;

//Write a method to decide if two strings are anagrams or not.
// NOTE : Anagram -> all single characters in each strings are the same
public class CheckAnagrams {
	
	//EASY : 
	// Sort both string and compare.
	
	public boolean checkAnagram2(String str1, String str2){
		if(str1==null || str2==null || str1.length() != str2.length())
			return false;
		
		int[] checker1 = new int[256];
		int[] checker2 = new int[256];
		int[] checkpoints = new int[str1.length()+ str2.length()];
		
		for(int i = 0; i < str1.length(); i ++){
			checker1[str1.charAt(i)]++;
			checker2[str2.charAt(i)]++;
			checkpoints[i] = str1.charAt(i);
		}
		
		for(int j = 0; j < checkpoints.length; j ++){
			if(checker1[checkpoints[j]]!= checker2[checkpoints[j]])
				return false;
		}
		return true;
	}
	
	
	public static void main(String args[]){
		CheckAnagrams ca = new CheckAnagrams();
		System.out.println(ca.checkAnagram2("abcdef", "abcdef"));
		System.out.println(ca.checkAnagram2("abcdef", "defabc"));
		System.out.println(ca.checkAnagram2("abcdef", "deabc"));
		System.out.println(ca.checkAnagram2("abcdef", null));
		System.out.println(ca.checkAnagram2(null, null));
	}

}
