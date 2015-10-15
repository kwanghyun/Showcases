package algorithm.recursion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Imagine a robot sitting on the upper left hand corner of an NxN grid. 
 * The robot can only move in two directions: right and down. 
 * How many possible paths are there for the robot?
 * FOLLOW UP
 * Imagine certain squares are "off limits", such that the robot can not step on them. 
 * Design an algorithm to get all possible paths for the robot.
 * 
 * Route number = (n + m)!/n! * m!
 */

/*
 * Mistake Note :
 * 1. Forgot to add recursion stopping point.  
 * 
 */

public class CC_8_2_FindRounte {
	public static void findRoute(int x, int y, String path,
			ArrayList<String> pathList) {

		path += "[" + x + "," + y + "]";
		
		if (x == 0 && y == 0) {
			pathList.add(path);
		} else if (x < 0 || y < 0) {// wrong way
			return;
		} else {
			findRoute(x - 1, y, path, pathList);
			findRoute(x, y - 1, path, pathList);
		}
		// return pathList;
	}

	public static List<String> robotPaths(int n) {
		List<String> pathList = new ArrayList<String>();
		getPaths(n, 1, 1, "", pathList);
		return pathList;
	}

	public static void getPaths(int n, int i, int j, String path,
			List<String> pathList) {
		path += String.format(" (%d,%d)", i, j);
		// System.out.println(path);
		if (i == n && j == n) { // reach the (n,n) point
			pathList.add(path);
		} else if (i > n || j > n) {// wrong way
			return;
		} else {
			getPaths(n, i + 1, j, path, pathList);
			getPaths(n, i, j + 1, path, pathList);
		}
	}
	
public void findRoutes(int x, int y, String str, ArrayList<String> list){
	if(x > 3 || y > 3){
		return;
	}
	
	str = str + "["+x + ", " + y +"] ";
	
	if(x == 3 && y ==3){
		list.add(str);
	}else{
		findRoutes(x +1, y, str, list);
		findRoutes(x, y+1, str, list);			
	}
}


	public static void main(String args[]) {
		// findRoute(1, 1);
		// System.out.println(visitedTrack.size());
		ArrayList<String> list = new ArrayList<String>();
		
//		findRoute(3, 3, "", list);
//		for (String s : list)
//			System.out.println(s);

//		System.out.println("@@@ Count : "+list.size());
		
		CC_8_2_FindRounte fr = new CC_8_2_FindRounte();
		ArrayList<String> list2 = new ArrayList<String>();
		fr.findRoutes(0,0,"", list2);
		for(String s : list2)
			System.out.println(s);
		System.out.println("SIZE : "+ list.size());
		
//		List<String> pathList = robotPaths(3);
//		for (String s : pathList)
//			System.out.println(s);
	}

}
