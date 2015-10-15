package algorithm.recursion;

public class DoubleRecursive {

	public void printRecursively(String str){
		printRecursively(str, str.substring(0,1), 1);
	}
	public void printRecursively(String str, String part, int idx) {
		if (idx > str.length())
			return;

		if (idx == str.length()) {
			System.out.println(part);
		} else/* if (idx != 0)*/ {
			// System.out.println("1. str => " + str + ", idx => " + idx);
			printRecursively(str, part + str.charAt(idx), idx + 1);
			// System.out.println("2. str => " + str + ", idx => " + idx);
			printRecursively(str, part + "_" + str.charAt(idx), idx + 1);
		}
	}
	
	public static void main(String[] args) {
		DoubleRecursive obj = new DoubleRecursive();
		obj.printRecursively("123");
		System.out.println("---------------------------");
		obj.printRecursively("123", "", 0);
	}
}
