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
	 */
	
	int[][] matrix = {
			{1,1,1},
			{2,2,2},
			{3,3,3}
			};
	
	int[][] matrix2 = {
			{1,1,1,1},
			{2,2,2,2},
			{3,3,3,3}
			};

	int[][] matrix3 = {
			{0,0,0},
			{0,0,0},
			{0,0,0},
			{0,0,0}
			};

	public void solutionWithAdditionalArray(int[][] matrix){
		
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				matrix3[j][i]=matrix2[i][j];
			}
		}
	}
	
	//Work only n * n matrix 
	//But how you can do that without any new matrix? it only possible when it is 4 * 4 matrix in my example?
	public void solution(int[][] matrix){
		
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				int temp = matrix[j][i];
				matrix[i][j]=matrix[j][i];
				matrix[i][j] = temp;
			}
		}
	}
	
	//TODO m * n matrix 
	
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

		System.out.println(ri.matrix2.length); //3
		System.out.println(ri.matrix2[0].length); //4

		ri.printMatrix(ri.matrix2);
		ri.solution(ri.matrix2);
		System.out.println("==============");
		ri.printMatrix(ri.matrix3);
		
	}
}
