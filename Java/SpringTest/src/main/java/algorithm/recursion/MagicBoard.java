package algorithm.recursion;

public class MagicBoard {

	int[][] board;
	boolean[] used;
	int magicSum;
	public void init(int count){
		board = new int[count][count];
		used = new boolean[count*count];
		magicSum = count * (count * count + 1) / 2;
	}
	
	public void printBoard(){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				System.out.print(board[row][col] + ", ");
			}
			System.out.println("\n----------");
		}
	}
	
	public boolean isValid(int count){
		
		int leftCrossSum = 0 , rightCrossSum = 0;
		
		for(int row =0 ; row < board.length; row++){
			
			int rowSum = 0, colSum = 0;
			
			leftCrossSum += board[row][row];
			rightCrossSum += board[row][count - row - 1];
			
			for(int col =0 ; col < board.length; col++){
				rowSum += board[row][col];
				colSum += board[col][row];
			}
			if(rowSum != magicSum || colSum != magicSum)
				return false;
		}
		
		if(leftCrossSum != magicSum || rightCrossSum != magicSum)
			return false;
		
		return true;
	}
	int callCount = 0;
	public boolean solve(int count, int step){
		
		if(step == count * count)
			return isValid(count);
		
		for(int value = 1; value < count * count + 1; value++){
			if(!used[value -1 ]){
				used[value - 1] = true;
				board[step / count][step % count] = value;
				callCount++;
				
				if(solve(count, step+1)){
					return true;
				}
				board[step / count][step % count] = 0;
				used[value - 1] = false;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		MagicBoard mBoard = new MagicBoard();
		int count = 3;
		mBoard.init(count);
		mBoard.printBoard();
		mBoard.solve(count, 0);
		System.out.println(0);
		mBoard.printBoard();
		System.out.println(mBoard.callCount);
		
	}
	
}
