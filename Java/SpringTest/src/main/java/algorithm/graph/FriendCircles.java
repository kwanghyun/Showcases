package algorithm.graph;

/*
 * There are n students in a class. Every student can have 0 or more
 * friends. If A is a friend of B and B is a friend of C then A and C are
 * also friends. So we define a friend circle as a group of students who are
 * friends as given by above definition. Given an nXn-matrix friends which
 * consists of characters Y or N. If friends[i][j]=Y, then ith and jth
 * students are friends, friends[i][j]=N, then i and j are not friends. Find
 * the total number of such friend circles in the class.
 * 
 * Order of the Algorithm
 * 
 * Time Complexity is O(n^2) Space Complexity is O(n)
 */

public class FriendCircles {

public static int getFriendCircles(char[][] friends) {

	if (friends == null || friends.length < 1)
		return 0;

	int noOfCircles = 0;

	boolean visited[] = new boolean[friends.length];

	for (int i = 0; i < visited.length; i++)
		visited[i] = false;

	for (int i = 0; i < friends.length; i++) {
		if (!visited[i]) {
			noOfCircles++;
			visited[i] = true;
			findFriends(friends, visited, i);
		}
	}

	return noOfCircles;
}

public static void findFriends(char[][] friends, boolean[] visited, int id) {

	for (int i = 0; i < friends.length; i++) {
		if (!visited[i] && i != id && 'Y' == friends[id][i]) {
			visited[i] = true;
			findFriends(friends, visited, i);
		}
	}
}
	
	public static void main(String[] args) {

		char[][] friends = { "YYNN".toCharArray(), "YYYN".toCharArray(), "NYYN".toCharArray(), "NNNY".toCharArray() };
		System.out.println(getFriendCircles(friends));
	}

}
