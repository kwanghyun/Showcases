package algorithm.trees;

/*Given a 2-d grid map of '1's (land) and '0's (water), count the number of islands. An
 island is surrounded by water and is formed by connecting adjacent lands horizontally
 or vertically. You may assume all four edges of the grid are all surrounded by water.

 Example 1:
 -----------
 11110
 11010
 11000
 00000
 -----------
 Answer: 1

 Example 2:
 -----------
 11000
 11000
 00100
 00011
 -----------
 Answer: 3
 *
 *The basic idea of the following solution is merging adjacent lands, and the merging
 * should be done recursively.
 */
public class NumberOfIslands {

	public int numIslands(char[][] grid) {
		if (grid == null || grid.length == 0 || grid[0].length == 0)
			return 0;
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == '1') {
					count++;
					merge(grid, i, j);
				}
			}
		}
		return count;
	}

	public void merge(char[][] grid, int i, int j) {
		// validity checking
		if (i < 0 || j < 0 || i > grid.length - 1 || j > grid[0].length - 1)
			return;
		
		// if current cell is water or visited
		if (grid[i][j] != '1')
			return;
		
		// set visited cell to '2'
		grid[i][j] = '2';
		
		// merge all adjacent land
		merge(grid, i - 1, j);
		merge(grid, i + 1, j);
		merge(grid, i, j - 1);
		merge(grid, i, j + 1);
	}
}
