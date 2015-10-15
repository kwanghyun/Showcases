package algorithm.etc;

import java.util.ArrayList;
import java.util.List;

/*Given numRows, generate the first numRows of Pascal’s triangle. For example,
 given numRows = 5, the result should be:
 [
         [1],
        [1,1],
       [1,2,1],
     [1,3,3,1],
    [1,4,6,4,1]
 ]
 */

public class PascalTriangle2 {

	public ArrayList<ArrayList<Integer>> generate(int numRows) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		if (numRows <= 0)
			return result;
		
		ArrayList<Integer> prev = new ArrayList<Integer>();
		prev.add(1);
		result.add(prev);
		
		for (int i = 2; i <= numRows; i++) {
			ArrayList<Integer> curr = new ArrayList<Integer>();
			curr.add(1); // first
			for (int j = 0; j < prev.size() - 1; j++) {
				curr.add(prev.get(j) + prev.get(j + 1)); // middle
			}
			curr.add(1);// last
			
			result.add(curr);
			prev = curr;
		}
		return result;
	}

	public static void main(String[] args) {
		PascalTriangle2 obj = new PascalTriangle2();
		for(ArrayList<Integer> row :obj.generate(5))
			System.out.println(row);

	}
}
