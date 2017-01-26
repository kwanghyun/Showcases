package algorithm.etc;

import java.util.*;

public class SnakeGame {
	int r = 0;
	int c = 0;
	int snakeLen = 1;
	int foodIdx;
	int[][] board;
	int[][] food;
	// Queue<Integer> moves;
	Queue<String> moves;

	/**
	 * Initialize your data structure here.
	 * 
	 * @param width
	 *            - screen width
	 * @param height
	 *            - screen height
	 * @param food
	 *            - A list of food positions E.g food = [[1,1], [1,0]] means the
	 *            first food is positioned at [1,1], the second is at [1,0].
	 */
	public SnakeGame(int width, int height, int[][] food) {
		board = new int[height][width];
		this.food = food;
		moves = new LinkedList<>();
	}

	/**
	 * Moves the snake.
	 * 
	 * @param direction
	 *            - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
	 * @return The game's score after the move. Return -1 if game over. Game
	 *         over when snake crosses the screen boundary or bites its body.
	 */
	public int move(String direction) {
		switch (direction) {
		case "U":
			r--;
			break;
		case "L":
			c--;
			break;
		case "R":
			c++;
			break;
		case "D":
			r++;
			break;
		default:
		}


		if (isValidMove()) {

			if (foodIdx < food.length && r == food[foodIdx][0] && c == food[foodIdx][1]) {
				foodIdx++;
				snakeLen++;
			}

			if (foodIdx == food.length)
				return foodIdx;

			if (moves.size() >= snakeLen)
				moves.poll();
			// moves.offer(r * board.length + c);
			moves.offer(r + "-" + c);

			return foodIdx;
		}
		

		return -1;
	}

	private boolean isValidMove() {
		if (r < 0 || c < 0 || r >= board.length || c >= board[0].length)
			return false;

		// Iterator<Integer> it = moves.iterator();
		Iterator<String> it = moves.iterator();
		String currPos = r + "-" + c;

		System.out.println(
				"r = " + r + ", c = " + c + ", currPos =" + currPos + ", snakeLen = " + snakeLen + ", moves= " + moves);
		while (it.hasNext()) {
			// if (r * board.length + c == it.next())
			if (currPos.equals(it.next()))
				return false;
		}

		return true;
	}
}

/**
 * Your SnakeGame object will be instantiated and called as such: SnakeGame obj
 * = new SnakeGame(width, height, food); int param_1 = obj.move(direction);
 */
