package algorithm.stringArray;

/*
 * Say you have an array for which the ith element is the price of a given
 * stock on day i. If you were only permitted to buy one share of the stock
 * and sell one share of the stock, design an algorithm to find the best
 * times to buy and sell.
 * 
 * Case : [1,2,3,0]
 */
public class StockBestBenifitI {

	public int maxProfit(int[] prices) {
		int max = 0;
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < prices.length; i++) {
			min = Math.min(min, prices[i]);

			if (max < prices[i] - min) {
				max = prices[i] - min;
				System.out.println("buy => " + min + " sell => " + prices[i]);
			}
		}
		return max;
	}

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

			// min is alway smaller than minIdx
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

	public static void main(String args[]) {
		StockBestBenifitI sbb = new StockBestBenifitI();
		// int[] arr = { 3, 2, 6, 4, 8, 10, 24, 1, 7 };
		// int[] arr = { 3, 2, 15};
		// int[] arr = { 3, 2, 6, 4, 8, 1, 24, 1, 7, 0 };
		// int[] arr = { 3, 2, 6, 4, 8, 1, 24, 11, 0, 30 };
		// int[] arr = { 3, 5, 24, 2, 1, 0 };
		int[] arr = { 3, 2, 6, 4, 8, 1, 24, 11, 30, 0 };
		System.out.println("-----------------maxProfit-----------------");
		System.out.println(sbb.maxProfit(arr));
		System.out.println("-----------------solution-----------------");
		System.out.println(sbb.solution(arr));
		System.out.println("-----------------solution2-----------------");
		System.out.println(sbb.solution2(arr));
	}
}
