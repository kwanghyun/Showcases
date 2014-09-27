package algorithm.probablity;

public class SameBirthday {
	public static double caculateProbability(int n) {
		double x = 1;

		for (int i = 0; i < n; i++) {
			x = x * (365.0 - i) / 365.0;
		}
		System.out.println("x - " + x);
		double pro = Math.round((1 - x) * 100);
		return pro / 100;
	}

	public static void main(String args[]) {
		System.out.println(caculateProbability(50));
	}
}
