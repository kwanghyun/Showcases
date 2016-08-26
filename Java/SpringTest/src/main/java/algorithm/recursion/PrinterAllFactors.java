package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;

public class PrinterAllFactors {

	public void solution(int n, ArrayList<Integer> list) {
		if (isPrime(n)) {
			list.add(n);
			System.out.println(list.toString().replace(",", " *"));
			list.remove(list.size() - 1);
		}
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0) {
				list.add(i);
				solution(n / i, list);
				list.remove(list.size() - 1);
			}
		}
	}

	public boolean isPrime(int n) {
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public void solutionI(int n, int[] arr, int idx) {
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0) {
				arr[idx] = i;
				if (isPrime(n / i)) {
					arr[idx + 1] = n / i;
					System.out.println(Arrays.toString(arr).replace(",", " *"));
					arr[idx + 1] = 0;
				} else {
					solutionI(n / i, arr, idx + 1);
				}
				arr[idx] = 0;
			}
		}
	}

	public void solutionII(int n, ArrayList<Integer> list) {
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0) {
				list.add(i);
				if (isPrime(n / i)) {
					list.add(n / i);
					System.out.println(list);
					list.remove(list.size() - 1);
				} else {
					solutionII(n / i, list);
				}
				list.remove(list.size() - 1);
			}
		}
	}

	public static void main(String[] args) {
		PrinterAllFactors obj = new PrinterAllFactors();
		System.out.println("------------solution---------");
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		obj.solution(12, list1);
		System.out.println("-----------solutionI----------");
		int[] arr = new int[3];
		obj.solutionI(12, arr, 0);
		System.out.println("-----------solutionII----------");
		ArrayList<Integer> list = new ArrayList<Integer>();
		obj.solutionII(12, list);
	}
}
