package algorithm.etc;

import java.util.*;

public class EliminationGame {
	public int lastRemaining(int n) {

		if (n <= 1)
			return 1;

		if (n == 2)
			return 2;

		List<Integer> list = new ArrayList<>();
		boolean left2right = false;

		for (int i = 2; i <= n; i = i + 2) {
			list.add(i);
		}
		// System.out.println("1. list : " + list);

		while (list.size() > 1) {

			List<Integer> tList = new ArrayList<>();
			int i = 1;
			if (!left2right && list.size() % 2 == 0) {
				i = 0;
			}

			for (int idx = i; idx < list.size(); idx = idx + 2) {
				tList.add(list.get(idx));
			}
			// System.out.println("2. tList : " + tList);

			list = tList;
			left2right = !left2right;
		}
		return list.get(0);
	}

	public static void main(String[] args) {
		EliminationGame ob = new EliminationGame();
		System.out.println(ob.lastRemaining(10000000));
	}
}
