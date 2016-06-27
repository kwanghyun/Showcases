package algorithm.stringArray;

import java.util.Arrays;

/*Given a matrix of mxn dimensions, print the elements of the matrix in diagonal order.*/
public class PrintMatrixDiagonally {

	public static void printMatrixDiagonally(int[][] matrix) {

		int r, c;
		int row = matrix.length;
		int col = matrix[0].length;

		for (int i = 0; i < row; i++) {
			for (r = i, c = 0; r >= 0 && c < col; r--, c++) {
				System.out.print(matrix[r][c] + " ");
			}
			System.out.println();
		}

		for (int i = 1; i < col; i++) {
			for (r = row - 1, c = i; r >= 0 && c < col; r--, c++) {
				System.out.print(matrix[r][c] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int row = 4, col = 6;

		int matrix[][] = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				matrix[i][j] = (i + 1) * 10 + j + 1;
			}
		}

		System.out.println("Input Matrix");
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}

		System.out.println("\nPrinting Matrix Diagonally");
		printMatrixDiagonally(matrix);
	}

}