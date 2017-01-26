package algorithm.graph;

import java.util.ArrayList;
import java.util.HashMap;

import algorithm.Utils;

/*
 * Optimal read list for given number of days A person is determined to
 * finish the book in ‘k’ days but he never wants to stop a chapter in
 * between. Find the optimal assignment of chapters, such that the person
 * doesn’t read too many extra/less pages overall.
 * 
	Example 1:
	Input:  Number of Days to Finish book = 2
	           Number of pages in chapters = {10, 5, 5}
	Output: Day 1:  Chapter 1
	            Day 2:  Chapters 2 and 3
	
	Example 2:
	Input:  Number of Days to Finish book = 3
	           Number of pages in chapters = {8, 5, 6, 12}
	Output: Day 1:  Chapter 1
	            Day 2:  Chapters 2 and 3 
	            Day 2:  Chapter 4

 */

public class OptimalRead {

	public void minAssingment(int[] pages, int chapters, int days) {

		int[][] DAG = new int[chapters + 1][chapters + 1];

		int sum = 0;
		int[] sums = new int[chapters + 1];
		for (int i = 0; i < chapters; i++) {
			sum += pages[i];
			sums[i + 1] = sum;
		}
		int avg_pages = sum / days;
		Utils.printArray(sums);

		for (int r = 0; r <= chapters; r++) {
			for (int c = 0; c <= chapters; c++) {
				if (c <= r) {
					DAG[r][c] = -1;
				} else {
					sum = Math.abs(avg_pages - (sums[c] - sums[r]));
					DAG[r][c] = sum;
				}
			}
		}
		Utils.printMetrix(DAG);

	}

	public void assingChapters(int v, int[] paths, int sum, int k) {

	}

	public static void main(String[] args) {
		OptimalRead ob = new OptimalRead();
		int[] pages = { 8, 5, 6, 12 };
		ob.minAssingment(pages, pages.length, 3);
		HashMap<Character, ArrayList<Character>> map = new HashMap<>();
		ArrayList<Character> list = map.getOrDefault('A', new ArrayList<Character>());
		list.add('B');
		map.put('A', list);
		System.out.println(map);
	}
}
