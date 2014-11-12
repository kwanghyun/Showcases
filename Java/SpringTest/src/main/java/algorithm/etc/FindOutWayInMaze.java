package algorithm.etc;

import java.util.ArrayList;

/*
 * You have an MxN matrix and a robot trying to go from top-left to bottom-right.. 
 * Write a program that calculates the shortest path from top-left to bottom-right 
 * that does not go through a negative value. The robot can only move "down" and "left".   
 */
public class FindOutWayInMaze {

int[][] maze = {
		{1,1,1,1},
		{-1,1,-1,1},
		{1,1,-1,1},
		{1, 1, 1,10}
};
	
	String str = "";
	
public boolean findPath( int[][] maze, int x, int y){
	if(x > maze.length-1 || y > maze[0].length-1)
		return false;
	
	if(maze[x][y] < 0 ){
		return false;
	}
	str += "[" + x + ", " + y+"]";
	if(maze[x][y] == 10){
		return true;
	}
	
	boolean found = findPath(maze, x+1, y);
	if(!found)
		findPath(maze, x, y+1);

	return true;
}

public String findPath2( int[][] maze, int x, int y, String str){
	if(x > maze.length-1 || y > maze[0].length-1)
		return null;
	
	if(maze[x][y] < 0 ){
		return null;
	}
	str += "[" + x + ", " + y+"]";
	if(maze[x][y] == 10){
		return str;
	}

	String temp = findPath2(maze, x+1, y, str);
	if(temp != null)
		str = str + temp;
	
	if(temp == null)
		findPath2(maze, x, y+1, str);

	return str;
}
	
	public static void main(String args[]){
		FindOutWayInMaze m = new FindOutWayInMaze();
//		m.findPath(m.maze, 0, 0);
//		System.out.println(m.str);
		
		System.out.println(m.findPath2(m.maze, 0, 0, ""));
//		System.out.println(m.maze.length);
//		System.out.println(m.maze[0].length);
	}
}
