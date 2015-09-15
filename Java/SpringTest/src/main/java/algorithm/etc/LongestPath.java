package algorithm.etc;

/*
 * Problem Statement 
 * Write algorithm/code to find longest path between any two cities. 4X4 matrix was given. 
 * If there is no connectivity between two cities then the distance between them was given as -1. 
 * Its cyclic graph.
 */
public class LongestPath {

	static int[][] matrix = { 
			{ 0, 1, 2, 3 }, 
			{ 1, 0, 1, 1 }, 
			{ 2, 1, 0, 5 },
			{ 3, 4, 3, 0 } };

	public int longestPath(int node) {

		int dist = 0;
		int max = 0;
		int i;

		for (i = node + 1; i < 4; i++) {
			dist = 0;
			if (matrix[node][i] > 0) {
				System.out.println("node: " + node);
				System.out.println("i : " + i);
				System.out.println("matrix[node][i] : " + matrix[node][i]);
				dist += matrix[node][i];
				dist += longestPath(node + 1);
			}
		}
		if (max < dist)
			max = dist;

		return max;
	}

	public void solution(int[][] arr) {
		for (int k = 0; k < 4; k++) {
			for (int i = 1; i < 4; i++) {
				for (int j = 1; j < 4; j++) {
					if(i==1 && j ==2)
					System.out.println("i, j :: [" + i + ", " + j + "]" + " i, k :: [" + i + ", " + k + "]" + "k, j :: [" + k + ", " + j + "]");
					if (arr[i][j] > arr[i][k] + arr[k][j]) {
						arr[i][j] = arr[i][k] + arr[k][j];
						
						arr[i][j] = k;
					}
				}

			}
		}
	}

	public static void main(String args[]) {
		LongestPath lp = new LongestPath();
//		System.out.println(lp.longestPath(0));
		lp.solution(matrix);
		System.out.println(matrix[1][3]);
	}
}
