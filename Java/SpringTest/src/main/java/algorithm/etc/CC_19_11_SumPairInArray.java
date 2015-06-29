package algorithm.etc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Design an algorithm to find all pairs of integers within an array which sum to a specified value.
 * 
 */
public class CC_19_11_SumPairInArray {

	public List<String> findSumParis(int[] arr, int sum) {
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < arr.length; i++) {
			for (int  j = i + 1; j < arr.length; j++) {
				if(arr[i] + arr[j] == sum)
					list.add(arr[i] + " , "+arr[j]);
			}
		}
		
		return list;
	}

	public List<String> doBetter(int[] arr, int sum) {
		Arrays.sort(arr);
		ArrayList<String> list = new ArrayList<String>();

		int first = 0;
		int last = arr.length - 1;
		while(first < last){

			if (first != last && arr[first] + arr[last] == sum){
				list.add(arr[first] + " , "+arr[last]);
			}else{
				System.out.println("first : " + first + ", last : " + last);
				while( arr[last] > sum - arr[first]){
					last--;
					if( first != last && arr[first] + arr[last] == sum){
						list.add(arr[first] + " , "+arr[last]);
					}
				}	
			}
			first ++;
		}
		
		return list;
	}
	
	//This only works for no duplicated array.
	public List<String> doBetter2(int[] arr, int sum){
		Arrays.sort(arr);
		List<String> list = new ArrayList<String>();
		int head = 0;
		int tail = arr.length - 1;
		
		while (head < tail ){
			if(sum == arr[head] + arr[tail]){
				list.add(arr[head] + " , "+arr[tail]);
				head++;
			}else if( sum > arr[head] + arr[tail]){
				head ++;
			}else{
				tail --;
			}
		}
		return list;
	}

	public static void main(String args[]){
		//No duplicated element
		int[] arr = {5,1,0,3,4,7,2,6,8,10,9};
		//With duplicated elements
//		int[] arr = {5,1,0,3,8,2,4,7,2,6,8,10,9};
		CC_19_11_SumPairInArray spa = new CC_19_11_SumPairInArray();
		List<String> list = spa.findSumParis(arr, 10);
		List<String> list2 = spa.doBetter(arr, 10);
		List<String> list3 = spa.doBetter2(arr, 10);
		for(String str : list){
			System.out.println(str);
		}
		System.out.println("-------------------------");
		for(String str : list2){
			System.out.println(str);
		}
		System.out.println("-------------------------");
		for(String str : list3){
			System.out.println(str);
		}
	}
	
}
