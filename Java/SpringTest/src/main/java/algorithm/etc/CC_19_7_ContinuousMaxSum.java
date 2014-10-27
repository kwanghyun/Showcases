package algorithm.etc;

/*
 * You are given an array of integers (both positive and negative). 
 * Find the continuous sequence with the largest sum. Return the sum.
 */
public class CC_19_7_ContinuousMaxSum {
	
	public int maxSum(int[] arr){
		int maxSum = 0;
		for(int i = 0; i < arr.length - 2; i++ ){
			int sum = arr[i] + arr[i+1] +arr[i+2];
			if(i == 0) 
				maxSum = sum;
			else if(sum > maxSum)
				maxSum = sum;
		}
		
		return maxSum;
	}

	public static void main(String args[]){
		
		int[] arr = {2, -8, 3, -2, 4, -10};
		
		CC_19_7_ContinuousMaxSum cms = new CC_19_7_ContinuousMaxSum();
		System.out.println(cms.maxSum(arr));
		
	}
}
