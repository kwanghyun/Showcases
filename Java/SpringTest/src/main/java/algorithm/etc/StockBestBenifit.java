package algorithm.etc;

public class StockBestBenifit {
	/*
	 * Say you have an array for which the ith element is the price of a given
	 * stock on day i. If you were only permitted to buy one share of the stock
	 * and sell one share of the stock, design an algorithm to find the best
	 * times to buy and sell.
	 */
	
	public String solution(int[] arr) {
	
		int minIdx = 0, maxIdx = 0, maxDiff = 0, tempMinIdx = 0;
		
		for (int idx = 0; idx < arr.length; idx++) {
	
			if (arr[minIdx] > arr[idx]) {
				tempMinIdx = idx;
			}
	
			int diff = arr[idx] - arr[tempMinIdx];
			
//			System.out.println("idx -> "+arr[idx]);
//			System.out.println("tempMinIdx -> "+arr[tempMinIdx]);
//			System.out.println("diff -> "+diff);
//			System.out.println("maxDiff -> "+maxDiff);
//			System.out.println("minIdx -> "+arr[minIdx]);
//			System.out.println("maxIdx -> "+arr[maxIdx]);	
//			System.out.println("==============");
			
			if (maxDiff < diff) {
				maxDiff = diff;
				minIdx = tempMinIdx;
				maxIdx = idx;
			}
		}
		return "Buy : " + arr[minIdx] + ", Sell : " + arr[maxIdx];
	}

	public static void main(String args[]) {
		StockBestBenifit sbb = new StockBestBenifit();
//		 int[] arr = { 3, 2, 6, 4, 8, 10, 24, 1, 7 };
//		 int[] arr = { 3, 2, 15};
//		int[] arr = { 3, 2, 6, 4, 8, 1, 24, 1, 7, 0 };
		int[] arr = { 3, 2, 6, 4, 8, 1, 24, 11, 0, 30 };
		System.out.println(sbb.solution(arr));
		
	}
}
