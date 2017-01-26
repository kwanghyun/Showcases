package algorithm.dfsbfs;

import java.util.*;

import algorithm.Utils;

/*
 * You want to build a house on an empty land which reaches all buildings in
 * the shortest amount of distance. You can only move up, down, left and
 * right. You are given a 2D grid of values 0, 1 or 2, where:
 * 
 * Each 0 marks an empty land which you can pass by freely.
 * Each 1 marks a building which you cannot pass through. 
 * Each 2 marks an obstacle which you cannot pass through.
 * 
 * For example, given three buildings at (0,0), (0,4), (2,2), and an
 * obstacle at (0,2):
 * 
	1 - 0 - 2 - 0 - 1
	|   |   |   |   |
	0 - 0 - 0 - 0 - 0
	|   |   |   |   |
	0 - 0 - 1 - 0 - 0
 * 
 * The point (1,2) is an ideal empty land to build a house, as the total
 * travel distance of 3+3+1=7 is minimal. So return 7.
 * 
 * Note:
 * There will be at least one building. If it is not possible to build such
 * house according to the above rules, return -1.
 */
public class ShortestDistanceFromAllBuildings {
	int col;
	int row;
	int[][] reach;
	int[][] dist;

	public int shortestDistance(int[][] grid) {

		if (grid == null || grid.length == 0 || grid[0].length == 0)
			return -1;

		row = grid.length;
		col = grid[0].length;

		dist = new int[row][col];
		reach = new int[row][col];
		int totalBld = 0;
		Queue<Integer> q = new LinkedList<>();
		int min = Integer.MAX_VALUE;

		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				if (grid[r][c] == 1) {
					System.out.println("[START] r = " + r + ", c = " + c);
					q.offer(r * col + c);
					totalBld++;
					boolean[][] visited = new boolean[row][col];
					bfs(grid, q, 0, totalBld, visited);
					Utils.printMetrix(grid);
					Utils.printMetrix(reach);
					Utils.printMetrix(dist);
					System.out.println("==============");
				}
			}
		}
		Utils.printMetrix(grid);
		Utils.printMetrix(reach);
		Utils.printMetrix(dist);

		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				if (reach[r][c] == totalBld && dist[r][c] != 0) {
					min = Math.min(min, dist[r][c]);
				}
			}
		}
		System.out.println("min = " + min);
		if (min == Integer.MAX_VALUE)
			return -1;
		return min;
	}

	int[][] moves = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	private void bfs(int[][] grid, Queue<Integer> q, int d, int totalBld, boolean[][] visited) {
		while (!q.isEmpty()) {
			int size = q.size();

			for (int p = 0; p < size; p++) {

				int pos = q.poll();
				int or = pos / col;
				int oc = pos % col;

				visited[or][oc] = true;
				reach[or][oc]++;

				dist[or][oc] += d;

				for (int i = 0; i < moves.length; i++) {
					int r = or + moves[i][0];
					int c = oc + moves[i][1];

					if (isSafe(grid, r, c) && grid[r][c] == 0 && !visited[r][c]) {
						visited[r][c] = true;
						q.offer(r * col + c);
					}
				}
			}
			d++;

		}
	}

	private boolean isSafe(int[][] grid, int r, int c) {
		if (r < 0 || c < 0 || r >= row || c >= col)
			return false;
		return true;
	}

	public static void main(String[] args) {
		ShortestDistanceFromAllBuildings ob = new ShortestDistanceFromAllBuildings();
		// int[][] grid = { { 1, 0, 2, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0,
		// 0 } };
		int[][] grid = { { 1, 0 } };
		System.out.println(ob.shortestDistance(grid));
	}
}
