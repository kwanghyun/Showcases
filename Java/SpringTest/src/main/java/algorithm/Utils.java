package algorithm;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Utils {

	public static void printMetrix(int[][] m) {
		int rlen = m.length;
		int clen = m[0].length;
		for (int r = 0; r < rlen; r++) {
			for (int c = 0; c < clen; c++) {
				System.out.format("%,3d,", m[r][c]);
			}
			System.out.println("");
		}
		System.out.println("-------------------------");
	}

	public static void printMetrix(char[][] m) {
		int rlen = m.length;
		int clen = m[0].length;
		for (int r = 0; r < rlen; r++) {
			for (int c = 0; c < clen; c++) {
				if (m[r][c] == '0')
					System.out.println(" " + m[r][c] + " ,");
				else
					System.out.format("%,3s,", m[r][c]);
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

	public static String getCallStackPrefix(int callstack) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < callstack; i++) {
			sb.append("  ");
		}
		sb.append("[" + callstack + "] ");
		return sb.toString();
	}

	public static void printCS(int callstack, String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(getCallStackPrefix(callstack));
		sb.append(str);
		System.out.println(sb);
	}

	public static void printCsEOL(int callstack) {
		StringBuilder sb = new StringBuilder();
		sb.append(getCallStackPrefix(callstack));
		sb.append("  ------- (EOL) -------   ");
		System.out.println(sb);
	}

	public static void printCsEOR(int callstack) {
		StringBuilder sb = new StringBuilder();
		sb.append(getCallStackPrefix(callstack));
		sb.append("  ------- (EOR) -------   ");
		System.out.println(sb);
	}

	public static void printCsEOF(int callstack) {
		StringBuilder sb = new StringBuilder();
		sb.append(getCallStackPrefix(callstack));
		sb.append("  ------- (EOF) -------   ");
		System.out.println(sb);
	}

	public static void printCS(int callstack, Map<String, ?> map) {
		StringBuilder sb = new StringBuilder();
		sb.append(getCallStackPrefix(callstack));
		for (Entry<String, ?> entry : map.entrySet()) {
			sb.append("  ");
			sb.append(map.get(entry.getKey()));
			sb.append(" = ");
			sb.append(map.get(entry.getValue()));
			sb.append(", ");
		}
		System.out.println(sb);
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

	public static void printArray(boolean[] arr) {
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			System.out.print("[" + arr[i] + "] ");
		}
		System.out.println("");
	}

	public static void printArray(char[] arr) {
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
