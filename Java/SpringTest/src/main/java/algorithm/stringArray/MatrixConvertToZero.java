package algorithm.stringArray;

public class MatrixConvertToZero {
	
	int[][] matrix = {
			{1,1,1,1},
			{1,1,0,1},
			{1,0,1,1}
	};
	
	public void solution(int[][] matrix){
		
		int[] column = new int[matrix[0].length];
		int[] row = new int[matrix.length];
		int count = 0;
		for(int x = 0; x< matrix.length; x++){
			for (int y = 0; y < matrix[0].length; y++) {
				if (matrix[x][y] == 0) {
					row[count] = x;
					column[count] = y;
					count++;
				}
			}
		}
		
		for(int c=0; c < count; c++){
			for (int i = 0; i < matrix.length; i++) {
				matrix[i][column[c]] = 0;
			}
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[row[c]][j] = 0;
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
		MatrixConvertToZero ri = new MatrixConvertToZero();
		System.out.println(ri.matrix[0].length); //4
		System.out.println(ri.matrix.length); //3

		ri.printMatrix(ri.matrix);
		ri.solution(ri.matrix);
		System.out.println("==============");
		ri.printMatrix(ri.matrix);
		
	}
}
