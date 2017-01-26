package algorithm.dfsbfs;

import java.util.LinkedList;
import java.util.Queue;

import algorithm.Utils;

/*
 * You are given a m x n 2D grid initialized with these three possible
 * values.
 * 
 * 1. -1 - A wall or an obstacle.
 * 
 * 2. 0 - A gate.
 * 
 * 3. INF - Infinity means an empty room. We use the value 2^31 - 1 =
 * 2147483647 to represent INF as you may assume that the distance to a gate
 * is less than 2147483647.
 * 
 * Fill each empty room with the distance to its nearest gate. If it is
 * impossible to reach a gate, it should be filled with INF.
 * 
 * For example, given the 2D grid: 
 * 
	INF  -1  0  INF
	INF INF INF  -1
	INF  -1 INF  -1
	  0  -1 INF INF
 * 
 * After running your function, the 2D grid
 * should be: 3 -1 0 1

	  3  -1   0   1
	  2   2   1  -1
	  1  -1   2  -1
	  0  -1   3   4
 */
public class WallsAndGates {

	class QNode {
		int row, col, dist;

		public QNode(int r, int c, int d) {
			this.row = r;
			this.col = c;
			this.dist = d;
		}

	};

	int row[] = { -1, 0, 1, 0 };
	int col[] = { 0, 1, 0, -1 };

	public boolean isSafe(int r, int c, int rLen, int cLen) {
		if ((r < 0 || r > rLen - 1) || (c < 0 || c > cLen - 1))
			return false;

		return true;
	}

	public boolean isEmptyRoom(int r, int c, int matrix[][]) {
		if (matrix[r][c] == Integer.MAX_VALUE)
			return true;

		return false;
	}

	public void wallsAndGates(int rooms[][]) {
		if (rooms.length == 0)
			return;
		int rLen = rooms.length;
		int cLen = rooms[0].length;
		Queue<QNode> q = new LinkedList<>();

		for (int r = 0; r < rLen; r++) {
			for (int c = 0; c < cLen; c++) {
				// initialize each cell as -1

				if (rooms[r][c] == 0) {
					QNode qNode = new QNode(r, c, 0);
					q.offer(qNode);
				}
			}
		}

		while (!q.isEmpty()) {
			QNode curr = q.poll();
			int cr = curr.row;
			int cc = curr.col;
			int d = curr.dist;

			for (int i = 0; i < 4; i++) {
				int r = cr + row[i];
				int c = cc + col[i];

				if (isSafe(r, c, rLen, cLen) && isEmptyRoom(r, c, rooms)) {
					rooms[r][c] = d + 1;
					QNode qNode = new QNode(r, c, d + 1);
					q.offer(qNode);
				}
			}
		}
	}

	public static void main(String[] args) {
		int matrix[][] = { { 2147483647, -1, 0, 2147483647 }, { 2147483647, 2147483647, 2147483647, -1 },
				{ 2147483647, -1, 2147483647, -1 }, { 0, -1, 2147483647, 2147483647 } };
		WallsAndGates ob = new WallsAndGates();
		ob.wallsAndGates(matrix);
	}
}
