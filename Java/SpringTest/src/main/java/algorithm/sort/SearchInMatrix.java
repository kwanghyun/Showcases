package algorithm.sort;

/*
 * Given a matrix in which each row and each column is sorted, 
 * write a method to find an element in it.
 */
public class SearchInMatrix {

	public String search(int[][] matrix, int num) {

		for (int i = 0; i < matrix.length; i++) {

			if (matrix[i][matrix[i].length-1] < num)
				continue;
			else {
				for (int j = 0; j < matrix.length; j++) {
					if (matrix[i][j] == num)
						return new String(i + ", " + j);
				}
			}
		}
		return "Not Found";
	}
	
	public String searchBook(int[][] matrix, int num){
		int row = 0;
		int column = matrix[0].length -1 ;
		
		while(row < matrix.length && column >= 0){
			if(matrix[row][column] == num){
				return new String(row + ", " + column);
			}else if(matrix[row][column] < num){
				row++;
			}else{
				column --;
			}
		}
		return "Not Found";
	}
	
	public static void main(String args[]){
		int [][] matrix = {
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12},
				{13,14,15,16}
		};
		SearchInMatrix sim = new SearchInMatrix();
		System.out.println(sim.search(matrix, 11));
		System.out.println(sim.searchBook(matrix, 11));
	}
}
