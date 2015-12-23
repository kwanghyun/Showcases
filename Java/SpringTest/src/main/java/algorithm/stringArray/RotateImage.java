package algorithm.stringArray;

//Given an image represented by an NxN matrix, where each pixel 
//in the image is 4 bytes, write a method to rotate the image by 
//90 degrees. Can you do this in place?
public class RotateImage {

	/* matrix[i][j]
	 * [0][j] 1 1 1
	 * [1][j] 2 2 2
	 * [2][j] 3 3 3
	 *      
	 *      to which mean [i][j] => [j][i]
	 *      
	 * [0][j] 3 2 1
	 * [1][j] 3 2 1
	 * [2][j] 3 2 1
	 * 
	 * In the following solution, a new 2-dimension array is created to store the rotated
		matrix, and the result is assigned to the matrix at the end. This is WRONG! Why?
	 */
	
	int[][] matrix = {
			{1,1,1},
			{2,2,2},
			{3,3,3}
			};

	int[][] matrix3 = {
			{0,0,0},
			{0,0,0},
			{0,0,0}
			};

	public void solutionWithAdditionalArray(int[][] matrix) {

		int row = matrix.length;
		int[][] result = new int[row][row];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				result[j][i] = matrix[i][j];
			}
		}

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < row; j++) {
				matrix[i][j] = result[i][j];
			}
		}
	}
		
	
	public static void rotate(int[][] matrix, int n) {
		for (int layer = 0; layer < n / 2; ++layer) {
			int first = layer;
			int last = n - 1 - layer;
			for (int i = first; i < last; ++i) {
				int offset = i - first;
				int top = matrix[first][i]; // save top
				// left -> top
				matrix[first][i] = matrix[last - offset][first];

				// bottom -> left
				matrix[last - offset][first] = matrix[last][last - offset];

				// right -> bottom
				matrix[last][last - offset] = matrix[i][last];

				// top -> right
				matrix[i][last] = top; // right <- saved top
			}
		}
	}
	
	/*
	 * By using the relation "matrix[i][j] = matrix[n-1-j][i]", we can loop
	 * through the matrix.
	 */
	public void rotate(int[][] matrix) {
		int row = matrix.length;
		
		for (int i = 0; i < row / 2; i++) {
			for (int j = 0; j < Math.ceil(((double) row) / 2.); j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[row - 1 - j][i];
				matrix[row - 1 - j][i] = matrix[row - 1 - i][row - 1 - j];
				matrix[row - 1 - i][row - 1 - j] = matrix[j][row - 1 - i];
				matrix[j][row - 1 - i] = temp;
			}
		}
	}
		
	public void printMatrix(int[][] matrix){
		for(int i = 0; i< matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				System.out.print("["+ matrix[i][j] + "] ");
				
			}
			
			System.out.println("\n-----------");
		}
	}
	
	public static void main(String args[]){
		RotateImage ri = new RotateImage();

		System.out.println(ri.matrix.length); //3
		System.out.println(ri.matrix[0].length); //4

		ri.printMatrix(ri.matrix);
		ri.solutionWithAdditionalArray(ri.matrix);
		System.out.println("==============");
		ri.printMatrix(ri.matrix);
		
	}
}
