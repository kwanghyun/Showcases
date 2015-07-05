package algorithm;

import java.util.Arrays;

public class Basic {
	public static void main(String[] args) {
		String string = "This is a string";
		char[] array = new char[20];
		String[] strArr = new String[10];
		int[][] matrix = new int[4][5];
		char[] charArray = null;
		int beginIndex = 0;
		int endIndex = 5;
		
		System.out.println("Row :: " + matrix.length); //Row :: 4, Array's array 
		System.out.println("Column :: " + matrix[0].length); //Column :: 5
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
