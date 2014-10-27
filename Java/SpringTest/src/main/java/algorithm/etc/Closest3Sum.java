package algorithm.etc;

import java.util.Arrays;

/*
 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, 
 * target. Return the sum of the three integers. You may assume that each input would have exactly 
 * one solution. For example, given array S = {-1 2 1 -4}, and target = 1. 
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 */
public class Closest3Sum {
	
	public int findCloestSum(int[] arr, int sum){
		int cloestSum = 0;
		int smalledstDiff = Integer.MAX_VALUE;
		for(int i =0; i < arr.length; i ++){
			for(int j = i +1; j < arr.length; j ++){
				for(int k = j +1; k<arr.length; k++){
					if(i!=j || i != k || j != k){
						int diff = Math.abs(sum - (arr[i] + arr[j] + arr[k]));
						if(diff < smalledstDiff){
							smalledstDiff = diff;
							cloestSum = (arr[i] + arr[j] + arr[k]);
						}
					}
				}
			}
		}
		return cloestSum;
	}
	
	public int doBetter(int[] arr, int sum){
		Arrays.sort(arr);
		int closestSum = 0;
		int smallestDiff = Integer.MAX_VALUE;
		
		for(int i = 0; i < arr.length; i ++){
			int k = arr.length - 1;
			int j = i + 1; 
			while(j < k){
				int tempSum = arr[i] + arr[j] + arr[k];
				int diff = Math.abs(sum - tempSum);
				if(diff < smallestDiff){
					smallestDiff = diff;
					closestSum = tempSum;
					
					if(tempSum > sum)
						k--;
					else if (tempSum < sum)
						j++;
				}
			}
		}
		return closestSum;
		
	}
	
	public static void main(String args[]){
		Closest3Sum cs = new Closest3Sum();
		int[] arr = {-1, 2, 1, -4};
		System.out.println(cs.findCloestSum(arr, 1));
		System.out.println(cs.doBetter(arr, 1));
	}
}
