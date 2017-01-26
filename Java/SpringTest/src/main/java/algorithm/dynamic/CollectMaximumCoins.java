package algorithm.dynamic;

/*	Collect maximum coins before hitting a dead end
Given a character matrix where every cell has one of the following values.

'C' -->  This cell has coin

'#' -->  This cell is a blocking cell. 
         We can not go anywhere from this.

'E' -->  This cell is empty. We don't get
         a coin, but we can move from here.  
Initial position is cell (0, 0) and initial direction is right.

Following are rules for movements across cells.

If face is Right, then we can move to below cells

Move one step ahead, i.e., cell (i, j+1) and direction remains right.
Move one step down and face left, i.e., cell (i+1, j) and direction becomes left.
If face is Left, then we can move to below cells

Move one step ahead, i.e., cell (i, j-1) and direction remains left.
Move one step down and face right, i.e., cell (i+1, j) and direction becomes right.
Final position can be anywhere and final direction can also be anything. The target is to collect maximum coins.
*/

public class CollectMaximumCoins {
	public boolean isValid(char[][] board, int r, int c) {
		if (r >= 0 && r < board.length && c >= 0 && c < board[0].length)
			return true;
		return false;
	}

	public int getMaxCoins(char[][] b, int r, int c, int d) {
		if (isValid(b, r, c) == false || b[r][c] == '#')
			return 0;

		int result = b[r][c] == 'C' ? 1 : 0;

		if (d == 1)
			return result + Math.max(getMaxCoins(b, r + 1, c, 0), // Down
					getMaxCoins(b, r, c + 1, 1)); // Ahead in right

		return result + Math.max(getMaxCoins(b, r + 1, c, 1), // Down
				getMaxCoins(b, r, c - 1, 0)); // Ahead in left
	}

	public int getMaxCoinsI(char[][] b, int r, int c, int d) {
		if (isValid(b, r, c) == false || b[r][c] == '#')
			return 0;

		int result = b[r][c] == 'C' ? 1 : 0;

		if (d == 1) {
			result += Math.max(getMaxCoins(b, r + 1, c, 0), // Down
					getMaxCoins(b, r, c + 1, 1)); // Ahead in right
		} else {
			result += Math.max(getMaxCoins(b, r + 1, c, 1), // Down
					getMaxCoins(b, r, c - 1, 0)); // Ahead in left
		}

		return result;
	}

	public int getMaxCoinsDP(char[][] b, int r, int c, int d, int dp[][][]) {
		if (isValid(b, r, c) == false || b[r][c] == '#')
			return 0;

		if (dp[r][c][d] != 0)
			return dp[r][c][d];

		dp[r][c][d] = b[r][c] == 'C' ? 1 : 0;

		// direction is right, get the max of two cases when you facing right in
		// this cell
		if (d == 1) {
			dp[r][c][d] += Math.max(getMaxCoinsDP(b, r + 1, c, 0, dp), // Down
					getMaxCoinsDP(b, r, c + 1, 1, dp)); // Ahead in right
		}
		// direction is left, get the max of two cases when you are facing left
		// in this cell
		if (d == 0) {
			dp[r][c][d] += Math.max(getMaxCoinsDP(b, r + 1, c, 1, dp), // Down
					getMaxCoinsDP(b, r, c - 1, 0, dp)); // Ahead in left
		}
		return dp[r][c][d];
	}

	public static void main(String[] args) {
		char[][] b = { { 'E', 'C', 'C', 'C', 'C' }, { 'C', '#', 'C', '#', 'E' }, { '#', 'C', 'C', '#', 'C' },
				{ 'C', 'E', 'E', 'C', 'E' }, { 'C', 'E', '#', 'C', 'E' } };

		CollectMaximumCoins ob = new CollectMaximumCoins();
		System.out.println(ob.getMaxCoins(b, 0, 0, 1));

		System.out.println(ob.getMaxCoinsI(b, 0, 0, 1));

		int[][][] dp = new int[b.length][b[0].length][2];
		System.out.println(ob.getMaxCoinsDP(b, 0, 0, 1, dp));
	}
}
