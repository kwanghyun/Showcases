package algorithm.stringArray;

/*
 * Given a m * n matrix, if an element is 0, set its entire row and column
 * to 0. Do it in place.
 * 
 * Analysis
 * 
 * This problem should be solved in place, i.e., no other array should be
 * used. We can use the first column and the first row to track if a
 * row/column should be set to 0.
 * 
 * Since we used the first row and first column to mark the zero row/column,
 * the original values are changed.
 * 
 */
public class SetMatrixZeroes {

	int[][] matrix = { { 0, 1, 0, 1 }, { 1, 1, 0, 1 }, { 1, 1, 1, 1 } };

	public void solution(int[][] matrix) {

		int[] column = new int[matrix[0].length];
		int[] row = new int[matrix.length];
		int count = 0;
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				if (matrix[x][y] == 0) {
					row[count] = x;
					column[count] = y;
					count++;
				}
			}
		}

		for (int c = 0; c < count; c++) {
			for (int i = 0; i < matrix.length; i++) {
				matrix[i][column[c]] = 0;
			}
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[row[c]][j] = 0;
			}
		}

	}

	public void setZeroes(int[][] matrix) {
		boolean firstRowZero = false;
		boolean firstColumnZero = false;

		// set first row and column zero or not
		for (int r = 0; r < matrix.length; r++) {
			if (matrix[r][0] == 0) {
				firstColumnZero = true;
				break;
			}
		}

		for (int c = 0; c < matrix[0].length; c++) {
			if (matrix[0][c] == 0) {
				firstRowZero = true;
				break;
			}
		}

		// mark zeros on first row and column
		for (int r = 1; r < matrix.length; r++) {
			for (int c = 1; c < matrix[0].length; c++) {
				if (matrix[r][c] == 0) {
					matrix[r][0] = 0;
					matrix[0][c] = 0;
				}
			}
		}

		// use mark to set elements
		for (int r = 1; r < matrix.length; r++) {
			for (int c = 1; c < matrix[0].length; c++) {
				if (matrix[r][0] == 0 || matrix[0][c] == 0) {
					matrix[r][c] = 0;
				}
			}
		}

		// set first column and row
		if (firstColumnZero) {
			for (int r = 0; r < matrix.length; r++)
				matrix[r][0] = 0;
		}

		if (firstRowZero) {
			for (int c = 0; c < matrix[0].length; c++)
				matrix[0][c] = 0;
		}
	}

	public void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print("[" + matrix[i][j] + "] ");

			}

			System.out.println("");
		}
	}


	public static void main(String args[]) {
		SetMatrixZeroes ob = new SetMatrixZeroes();
		System.out.println(ob.matrix[0].length); // 4
		System.out.println(ob.matrix.length); // 3

		ob.printMatrix(ob.matrix);
		ob.setZeroes(ob.matrix);
		System.out.println("==============");
		ob.printMatrix(ob.matrix);

	}
}
