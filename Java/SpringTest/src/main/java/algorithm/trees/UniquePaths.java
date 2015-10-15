package algorithm.trees;

/*
 * A robot is located at the top-left corner of a m x n grid. It can only move either down
 or right at any point in time. The robot is trying to reach the bottom-right corner of
 the grid.
 How many possible unique paths are there?

 A depth-first search solution is pretty straight-forward. However, the time of this
 solution is too expensive, and it didn’t pass the online judge
 */
public class UniquePaths {
	public int uniquePaths(int maxX, int maxY) {
		return dfs(0, 0, maxX, maxY);
	}

	public int dfs(int x, int y, int maxX, int maxY) {
		if (x == maxX - 1 && y == maxY - 1) {
			return 1;

		}
		if (x < maxX - 1 && y < maxY - 1) {
			return dfs(x + 1, y, maxX, maxY) + dfs(x, y + 1, maxX, maxY);
		}
		if (x < maxX - 1) {
			return dfs(x + 1, y, maxX, maxY);
		}
		if (y < maxY - 1) {
			return dfs(x, y + 1, maxX, maxY);
		}
		return 0;
	}
	
	public static void main(String[] args) {
		UniquePaths obj = new UniquePaths();
		System.out.println(obj.uniquePaths(8, 8));
	}
}
