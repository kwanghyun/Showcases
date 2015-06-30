package algorithm.etc;

import java.util.ArrayList;
import java.util.List;

/*
 * You have an MxN matrix and a robot trying to go from top-left to bottom-right.. 
 * Write a program that calculates the shortest path from top-left to bottom-right 
 * that does not go through a negative value. The robot can only move "down" and "left".   
 */
public class FindOutWayInMaze {

int[][] maze = {
		{1,1,0,1},
		{0,1,1,1},
		{1,1,0,1},
		{1,1,1,1}
};

	List<String> pathList = new ArrayList<String>();
	public void findPath(int[][] maze, String path, int x, int y){
		int row = maze.length;
		int column = maze[0].length;
				
		if(x > column -1 || y > row -1 ){
			return;
		}
		if(maze[x][y] == 0){
			return;
		}
		if(x == row -1 && y == column -1){
			path = path + "[" + x + ", " + y + "] ";
			pathList.add(path);
			return;
		}else{
			path = path + "[" + x + ", " + y + "] ";
			findPath(maze, path, x + 1, y);
			findPath(maze, path, x, y + 1);
		}
	}

	
	public static void main(String args[]){
		FindOutWayInMaze m = new FindOutWayInMaze();
		m.findPath(m.maze, "", 0, 0);
		for(String item : m.pathList){
			System.out.println(":::" + item);
		}
		
	}
}
