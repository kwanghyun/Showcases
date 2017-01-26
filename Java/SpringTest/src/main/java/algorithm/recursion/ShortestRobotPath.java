package algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

import algorithm.Utils;

/*
 * You have an MxN matrix and a robot trying to go from top-left to bottom-right.. 
 * Write a program that calculates the shortest path from top-left to bottom-right 
 * that does not go through a negative value. The robot can only move "down" and "left".   
 */
public class ShortestRobotPath {

	public int findShortestPath(int[][] maze, int x, int y) {
		int row = maze.length;
		int column = maze[0].length;
		if (x == row - 1 && y == row - 1) {
			return 1;
		}

		if (x < column && y < row && maze[x][y] == 1) {
			return 1 + Math.min(findShortestPath(maze, x + 1, y), findShortestPath(maze, x, y + 1));
		}
		return 100;
	}

	public static void main(String args[]) {
		ShortestRobotPath m = new ShortestRobotPath();
		int[][] maze = { { 1, 1, 0, 1 }, { 0, 1, 1, 1 }, { 1, 1, 0, 1 }, { 1, 1, 1, 1 } };
		Utils.printMetrix(maze);
		System.out.println(m.findShortestPath(maze, 0, 0));

	}
}
