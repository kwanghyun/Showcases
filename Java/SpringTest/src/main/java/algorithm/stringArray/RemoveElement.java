package algorithm.stringArray;

/*
 * Given an array and a value, remove all instances of that value in place and return
 the new length. (Note: The order of elements can be changed. It doesn’t matter what
 you leave beyond the new length.)
 */
public class RemoveElement {
	
	public int removeElement(int[] arr, int elem) {
		int i = 0;
		int j = 0;
		
		while (j < arr.length) {
			if (arr[j] != elem) {
				arr[i] = arr[j];
				i++;
			}
			j++;
		}
		
		return i;
	}
}
