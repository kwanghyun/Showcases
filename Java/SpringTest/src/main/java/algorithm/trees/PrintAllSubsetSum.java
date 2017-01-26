package algorithm.trees;

import java.util.ArrayList;

public class PrintAllSubsetSum {

	public void printAll(int k, int sum, ArrayList<Integer> list) {
		if (sum == 0) {
			System.out.println(list);
			return;
		}

		for (int i = 1; i < k; i++) {
			if (sum < i)
				break;
			list.add(i);
			printAll(k, sum - i, list);
			list.remove(list.size() - 1);
		}
	}

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		PrintAllSubsetSum ob = new PrintAllSubsetSum();
		ob.printAll(4, 4, list);
	}
}
