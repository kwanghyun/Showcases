package algorithm.stringArray;

import java.util.ArrayList;

/*
 * Find the intersection and union of two sorted array of integers.
 * 
 * arr1[] = {1,3,4,5,7}
 * arr2[] = {2,3,5,6}
 * 
 * Then your program should print Union as {1, 2, 3, 4, 5, 6, 7} and Intersection as {3, 5}. 
 */
public class FindIntersectionAndUnionInArray {
	public ArrayList<ArrayList<Integer>> getIntersectionAndUnion(int[] arr1, int[] arr2) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		int i = 0;
		int j = 0;
		ArrayList<Integer> intersections = new ArrayList<>();
		ArrayList<Integer> unions = new ArrayList<>();

		while (i < arr1.length && j < arr2.length) {

			if (arr1[i] < arr2[j]) {
				intersections.add(arr1[i]);
				i++;
			} else if (arr1[i] > arr2[j]) {
				intersections.add(arr2[j]);
				j++;
			} else {
				intersections.add(arr1[i]);
				unions.add(arr1[i]);
				i++;
				j++;
			}
		}

		while (i < arr1.length) {
			intersections.add(arr1[i]);
			i++;
		}

		while (j < arr2.length) {
			intersections.add(arr2[j]);
			j++;
		}

		result.add(intersections);
		result.add(unions);

		return result;
	}

	public ArrayList<ArrayList<Integer>> getIntersectionAndUnionI(int[] arr1, int[] arr2) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		int i = 0;
		int j = 0;
		ArrayList<Integer> intersections = new ArrayList<>();
		ArrayList<Integer> unions = new ArrayList<>();

		while (i < arr1.length || j < arr2.length) {

			if (i < arr1.length && j < arr2.length) {
				if (arr1[i] < arr2[j]) {
					intersections.add(arr1[i]);
					i++;
				} else if (arr1[i] > arr2[j]) {
					intersections.add(arr2[j]);
					j++;
				} else {
					intersections.add(arr1[i]);
					unions.add(arr1[i]);
					i++;
					j++;
				}
			} else if (i < arr1.length) {
				intersections.add(arr1[i]);
				i++;
			} else if (j < arr2.length) {
				intersections.add(arr2[j]);
				j++;
			}

		}

		result.add(intersections);
		result.add(unions);

		return result;
	}

	public static void main(String[] args) {
		FindIntersectionAndUnionInArray ob = new FindIntersectionAndUnionInArray();
		int[] arr1 = { 1, 3, 4, 5, 7 };
		int[] arr2 = { 2, 3, 5, 6 };
		System.out.println(ob.getIntersectionAndUnion(arr1, arr2));
		System.out.println("--------------------------------------------");
		System.out.println(ob.getIntersectionAndUnionI(arr1, arr2));
	}
}
