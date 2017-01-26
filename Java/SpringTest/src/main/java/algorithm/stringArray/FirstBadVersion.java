package algorithm.stringArray;

import algorithm.Utils;

/*
 * You are a product manager and currently leading a team to develop a new
 * product. Unfortunately, the latest version of your product fails the
 * quality check. Since each version is developed based on the previous
 * version, all the versions after a bad version are also bad.
 * 
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the
 * first bad one, which causes all the following ones to be bad.
 * 
 * You are given an API bool isBadVersion(version) which will return whether
 * version is bad. Implement a function to find the first bad version. You
 * should minimize the number of calls to the API.
 */
public class FirstBadVersion {
	char[] versions;

	public int firstBadVersion(int n) {

		int start = 1;
		int end = n;

		while (start < end) {
			System.out.println("start = " + start + ", end = " + end);
			int mid = (start + end) / 2;

			if (isBadVersion(mid)) {
				end = mid;
			} else {
				start = mid + 1;
			}
		}
		System.out.println("[END] start = " + start + ", end = " + end);
		return start;
	}

	public int firstBadVersionI(int n) {
		int left = 1;
		int right = n;
		while (left < right) {
			System.out.println("left = " + left + ", end = " + right);
			int mid = left + (right - left) / 2;
			if (isBadVersion(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		System.out.println("[END] left = " + left + ", end = " + right);
		return left;
	}

	private boolean isBadVersion(int i) {
		if (versions[i - 1] == 'b')
			return true;
		return false;
	}

	private void createVertions(int n, int firstBadVer) {
		versions = new char[n];

		for (int i = 0; i < n; i++) {
			if (i < firstBadVer - 1)
				versions[i] = 'g';
			else
				versions[i] = 'b';
		}
	}

	public static void main(String[] args) {
		FirstBadVersion ob = new FirstBadVersion();
		// int totalElem = 2;
		// ob.createVertions(totalElem, 1);
		int totalElem = 16000;
		ob.createVertions(totalElem, 1700);
		// int totalElem = 2;
		// ob.createVertions(totalElem, 2);

		// int totalElem = 2126753390;
		// ob.createVertions(totalElem, 1702766719);
		Utils.printArray(ob.versions);
		System.out.println(ob.firstBadVersion(totalElem));
		System.out.println("----------------");
		System.out.println(ob.firstBadVersionI(totalElem));
	}
}
