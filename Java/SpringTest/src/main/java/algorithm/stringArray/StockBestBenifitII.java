package algorithm.stringArray;

/*
 * Given an array of stock prices, find the maximum profit that can be
 * earned by performing multiple non-overlapping transactions (buy and
 * sell). Example: {100, 80, 120, 130, 70, 60, 100, 125} Profit: 115
 */

public class StockBestBenifitII {
	public static int maximumProfit2(int[] stockPrices) {
		int totalProfit = 0;
		for (int i = 1; i < stockPrices.length; i++) {
			int currentProfit = stockPrices[i] - stockPrices[i - 1];
			if (currentProfit > 0) {
				totalProfit += currentProfit;
			}
		}
		return totalProfit;
	}
	
    public int maxProfit(int[] prices) {
		int totalProfit = 0;
		for (int i = 1; i < prices.length; i++) {
			int currentProfit = prices[i] - prices[i - 1];
			if (currentProfit > 0) {
				totalProfit += currentProfit;
			}
		}
		return totalProfit;
    }
    
	public static void main(String args[]) {
		int[] p = { 100, 80, 120, 130, 70, 60, 100, 125 };
		System.out.println(maximumProfit2(p));

	}
}
