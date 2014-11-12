package algorithm.etc;

public class StockBestBenifit {
/*
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * If you were only permitted to buy one share of the stock and sell one share of the stock, 
 * design an algorithm to find the best times to buy and sell.
 */
	
public String solution(int[] arr){
	
	int min = 0;
	int maxDiff = 0;
	int buy = 0;
	int sell = 0;
	
	for(int i = 0; i< arr.length-1; i++){
		if(arr[min] > arr[i]){
			min = i;
		}
		
		int diff = arr[i] - arr[min];
		
		if(maxDiff < diff ){
			maxDiff = diff;
			buy = min;
			sell = i;
		}
	}
	
	return "buy : "+buy +" , sell : "+ sell;
}
	
	public static void main(String args[]){
		StockBestBenifit sbb = new StockBestBenifit();
		int[] arr = {3,2,6,4,8,10,24,1,7};
		System.out.println(sbb.solution(arr));
	}
}
