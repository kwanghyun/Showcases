package algorithm.stringArray;

public class StockBestBenifitI {
	/*
	 * Say you have an array for which the ith element is the price of a given
	 * stock on day i. If you were only permitted to buy one share of the stock
	 * and sell one share of the stock, design an algorithm to find the best
	 * times to buy and sell.
	 * 
	 * Case : [1,2,3,0]
	 */

	public String solution(int[] arr) {

		int minIdx = 0, maxIdx = 0, maxDiff = 0, tempMinIdx = 0;

		for (int idx = 0; idx < arr.length; idx++) {

			if (arr[minIdx] > arr[idx]) {
				tempMinIdx = idx;
			}

			int diff = arr[idx] - arr[tempMinIdx];

			if (maxDiff < diff) {
				maxDiff = diff;
				minIdx = tempMinIdx;
				maxIdx = idx;
			}
		}
		return arr[minIdx] + ", " + arr[maxIdx];
	}

	// This is wrong, For the case of getting min, max indexes, min should move
	// when max diff is changed. 
	public String solution2(int[] arr) {

		int minIdx = 0, maxIdx = 0, maxDiff = 0;

		for (int idx = 0; idx < arr.length; idx++) {
			
			//min is alway smaller than minIdx
			int diff = arr[idx] - arr[minIdx];

			if (maxDiff < diff) {
				maxDiff = diff;
				maxIdx = idx;
			}

			if (arr[minIdx] > arr[idx]) {
				minIdx = idx;
			}

		}
		return arr[minIdx] + ", " + arr[maxIdx];
	}

	/*Say you have an array for which the ith element is the price of a given stock on day
	i.
	If you were only permitted to complete at most one transaction (ie, buy one and sell
	one share of the stock), design an algorithm to find the maximum profit.
	
	*/
	
	public int maxProfit(int[] prices) {
		int profit = 0;
		int minElement = Integer.MAX_VALUE;
		
		for (int i = 0; i < prices.length; i++) {
			profit = Math.max(profit, prices[i] - minElement);
			minElement = Math.min(minElement, prices[i]);
		}
		return profit;
	}

	public static void main(String args[]) {
		StockBestBenifitI sbb = new StockBestBenifitI();
		// int[] arr = { 3, 2, 6, 4, 8, 10, 24, 1, 7 };
		// int[] arr = { 3, 2, 15};
		// int[] arr = { 3, 2, 6, 4, 8, 1, 24, 1, 7, 0 };
//		int[] arr = { 3, 2, 6, 4, 8, 1, 24, 11, 0, 30 };
//		int[] arr = { 3, 5, 24, 2, 1, 0 };
		int[] arr = { 3, 2, 6, 4, 8, 1, 24, 11, 30, 0 };
		System.out.println(sbb.maxProfit(arr));
		System.out.println(sbb.solution(arr));
		System.out.println(sbb.solution2(arr));
	}
}

	  