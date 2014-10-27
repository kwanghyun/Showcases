package algorithm.etc;

public class LongestPalindrome {
	
	public String findLongestPalindrome(char[] arr){
		
		int longestCount = Integer.MIN_VALUE;
		String longestStr = "";
		
		for(int i = 1; i< arr.length; i++){
			int j = 1;
			String str = "";
			int count = 0;
			boolean first = true;
			while (i - j >= 0 && i + j < arr.length){
				
				if(arr[i-j] != arr[i+j]){
					if(longestCount <count ){
						longestCount = count;
						longestStr = str;
					}
					break;
				}else{
					if(first){
						str = String.valueOf(arr[i]);
						first = false;
					}
					str = arr[i-j] + str + arr[i+j];
					j++;
					count++;
				}
			}
		}
		return longestStr;
	}
	
	public static void main(String args[]){
		LongestPalindrome lp = new LongestPalindrome();
		System.out.println(lp.findLongestPalindrome("1232112345432121".toCharArray()));
	}
	


}
