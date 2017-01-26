package algorithm.dynamic;

import algorithm.etc.LargestRectangleInHistogram;

/*
 * Maximum size rectangle binary sub-matrix with all 1s
	Given a binary matrix, find the maximum size rectangle binary-sub-matrix with all 1’s.
	
	Input :   0 1 1 0
	             1 1 1 1
	             1 1 1 1
	             1 1 0 0
	
	Output :  1 1 1 1
	              1 1 1 1
*/
public class MaxRectangleArea {

	public int maximum(int input[][]) {
		int temp[] = new int[input[0].length];
		LargestRectangleInHistogram mh = new LargestRectangleInHistogram();
		int maxArea = 0;
		int area = 0;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				if (input[i][j] == 0) {
					temp[j] = 0;
				} else {
					temp[j] += input[i][j];
				}
			}
			area = mh.largestRectangleArea(temp);
			if (area > maxArea) {
				maxArea = area;
			}
		}
		return maxArea;
	}

	public static void main(String args[]) {
		int input[][] = { { 1, 1, 1, 0 }, { 1, 1, 1, 1 }, { 0, 1, 1, 0 }, { 0, 1, 1, 1 }, { 1, 0, 0, 1 },
				{ 1, 1, 1, 1 } };
		MaxRectangleArea mrs = new MaxRectangleArea();
		int maxRectangle = mrs.maximum(input);
		// System.out.println("Max rectangle is of size " + maxRectangle);
		assert maxRectangle == 8;
	}
}
