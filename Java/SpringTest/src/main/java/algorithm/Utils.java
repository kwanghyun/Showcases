package algorithm;

import java.util.Random;

public class Utils {

	public static void printMetrix(int[][] m) {
		int rlen = m.length;
		int clen = m[0].length;
		for (int r = 0; r < rlen; r++) {
			for (int c = 0; c < clen; c++) {
				System.out.print("[" + m[r][c] + "] ");
			}
			System.out.println("");
		}
		System.out.println("-------------------------");
	}

	public static int[] createIntArrayFromRange(int start, int end) {
		int arr[] = new int[end - start + 1];
		for (int i = 0; i <= end - start; i++) {
			arr[i] = start + i;
		}
		return arr;
	}

	public static int[] shuffleArray(int[] array) {
		Random rgen = new Random(); // Random number generator

		for (int i = 0; i < array.length; i++) {
			int randomPosition = rgen.nextInt(array.length);
			int temp = array[i];
			array[i] = array[randomPosition];
			array[randomPosition] = temp;
		}
		return array;
	}

	public static void printArray(int[] arr) {
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			System.out.print("[" + arr[i] + "] ");
		}
		System.out.println("");
	}

	public static void printMetrix(boolean[][] m) {
		int rlen = m.length;
		int clen = m[0].length;
		for (int r = 0; r < rlen; r++) {
			for (int c = 0; c < clen; c++) {
				System.out.print("[" + m[r][c] + "] ");
			}
			System.out.println("");
		}
		System.out.println("-------------------------");
	}
}
