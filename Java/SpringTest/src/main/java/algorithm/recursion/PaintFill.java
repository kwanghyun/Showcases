package algorithm.recursion;

import java.util.Arrays;

/*
 * Implement the ¡°paint fill¡± function that one might see on many image 
 * editing programs. That is, given a screen (represented by a 2-dimensional 
 * array of Colors), a point, and a new color, fill in the surrounding area until 
 * you hit a border of that color.
 */

public class PaintFill {
	static int[][] matrix = {
			{0,0,0,0},
			{0,0,0,0},
			{0,0,0,0},
			{0,0,0,0}
	};
	
	public static int[][] changeColor(int[][] matrix){
		
		for (int i = 0; i < matrix[0].length; i++) {
			matrix[0][i] = 1;
			matrix[matrix.length - 1][i] = 1;
		}

		for (int i = 0; i < matrix.length; i++) {
			matrix[i][0] = 1;
			matrix[i][matrix.length - 1] = 1;
		}

		return matrix;
	}
	
	//TODO with Recursion
	
	public static void main(String args[]){
		int[][] arrs = changeColor(matrix);
		for( int[] arr : arrs)
			System.out.println(Arrays.toString(arr));
	}
}
