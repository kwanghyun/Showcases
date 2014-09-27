package algorithm;

import java.util.Arrays;

public class Basic {
	public void basic() {
		String string = "This is a string";
		String[] array = new String[5];
		String[] strArr = new String[10];
		char[] charArray = null;
		int beginIndex = 0;
		int endIndex = 5;

		string.toCharArray(); // get char array of a String
		Arrays.sort(array); // sort an array
		Arrays.toString(charArray); // convert to string
		string.charAt(beginIndex); // get a char at the specific index
		string.length(); // string length
		int size = strArr.length; // array size
		string.substring(beginIndex);
		string.substring(beginIndex, endIndex);
		Integer.valueOf("25");// string to integer
		String.valueOf(25);// integer to string
	}
}
