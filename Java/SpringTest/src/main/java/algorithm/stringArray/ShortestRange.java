package algorithm.stringArray;

import java.awt.event.AWTEventListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/*
 * You have k lists of sorted integers. Find the smallest range that
 * includes at least one number from each of the k lists.
 * 
 * For example, 
 * List 1: [4, 10, 15, 24, 26] 
 * List 2: [0, 9, 12, 20] 
 * List 3: [5, 18, 22, 30]
 * 
 * The smallest range here would be [20, 24] as it contains 24 from list 1,
 * 20 from list 2, and 22 from list 3.
 */

public class ShortestRange {
	public int size;
	public HeapNode[] Heap;
	public int position;
	static int gMax;
	static int gMin;
	int currMax; // tracks the max entry in the heap
	int range = Integer.MAX_VALUE;

	public ShortestRange(int k) {
		this.size = k;
		Heap = new HeapNode[k + 1]; // size+1 because index 0 will be empty
		position = 0;
		Heap[0] = new HeapNode(0, -1); // put some junk values at 0th index node
	}

	public int merge(int[][] arrs, int k, int n) {
		int nk = n * k;
		int count = 0;
		int[] arrPointers = new int[k];

		// create index pointer for every list.
		for (int i = 0; i < arrPointers.length; i++) {
			arrPointers[i] = 0;
		}

		for (int i = 0; i < k; i++) {
			insert(arrs[i][arrPointers[i]], i); // insert the element into heap

		}

		while (count < nk) {
			HeapNode h = extractMin(); // get the min node from the heap.
			int min = h.data; // this is min among all the values in the heap
			if (range > currMax - min) { // check if current difference > range
				gMin = min;
				gMax = currMax;
				range = gMax - gMin;
			}
			arrPointers[h.listNo]++;
			if (arrPointers[h.listNo] < n) { // check if list is not burns out
				insert(arrs[h.listNo][arrPointers[h.listNo]], h.listNo); // insert
																			// the
				// next element
				// from the list
			} else {
				return range; // if any of this list
								// burns out, return range
			}
			count++;
		}
		return range;
	}

	public void insert(int data, int listNo) {
		// keep track of max element entered in Heap till now
		if (data != Integer.MAX_VALUE && currMax < data) {
			currMax = data;
		}
		if (position == 0) { // check if Heap is empty
			Heap[position + 1] = new HeapNode(data, listNo); // insert the first
																// element in
																// heap
			position = 2;
		} else {
			Heap[position++] = new HeapNode(data, listNo);// insert the element
															// to the end
			bubbleUp(); // call the bubble up operation
		}
	}

	public HeapNode extractMin() {
		HeapNode min = Heap[1]; // extract the root
		Heap[1] = Heap[position - 1]; // replace the root with the last element
										// in
										// the heap
		Heap[position - 1] = null; // set the last Node as NULL
		position--; // reduce the position pointer
		sinkDown(1); // sink down the root to its correct position
		return min;
	}

	public void sinkDown(int k) {
		int smallest = k;
		// check which is smaller child , 2k or 2k+1.
		if (2 * k < position && Heap[smallest].data > Heap[2 * k].data) {
			smallest = 2 * k;
		}
		if (2 * k + 1 < position && Heap[smallest].data > Heap[2 * k + 1].data) {
			smallest = 2 * k + 1;
		}
		if (smallest != k) { // if any if the child is small, swap
			swap(k, smallest);
			sinkDown(smallest); // call recursively
		}

	}

	public void swap(int a, int b) {
		// System.out.println("swappinh" + mH[a] + " and " + mH[b]);
		HeapNode temp = Heap[a];
		Heap[a] = Heap[b];
		Heap[b] = temp;
	}

	public void bubbleUp() {
		int pos = position - 1; // last position
		while (pos > 0 && Heap[pos / 2].data > Heap[pos].data) { // check if its
																	// parent is
			// greater.
			HeapNode y = Heap[pos]; // if yes, then swap
			Heap[pos] = Heap[pos / 2];
			Heap[pos / 2] = y;
			pos = pos / 2; // make pos to its parent for next iteration.
		}
	}



	public static void main(String[] args) {
		int[][] A = new int[3][];
		A[0] = new int[] { 3, 10, 15, 24 };
		A[1] = new int[] { 0, 1, 2, 20 };
		A[2] = new int[] { 1, 18, 21, 30 };

		ShortestRange m = new ShortestRange(A.length);
		int rng = m.merge(A, A.length, A[0].length);
		System.out.println("Smallest Range is: " + rng + " from " + gMin + " To " + gMax);
	}

}
