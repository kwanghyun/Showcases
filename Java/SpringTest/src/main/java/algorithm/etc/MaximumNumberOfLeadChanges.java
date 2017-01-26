package algorithm.etc;

public class MaximumNumberOfLeadChanges {

	int[] scores = { 2, 3 };
	int maxLeadChange = 1;

	public void getMaxLeadChanges(int s1Target, int s2Target, int s1old, int s2old, int s1new, int s2new,
			int leadChange) {

		if (s1new == s1Target && s2new == s2Target) {
			System.out.println("## s1old = " + s1old + ", s2old = " + s2old + ", s1new = " + s1new + ", s2new = "
					+ s2new + ", leadChange = " + leadChange);
			maxLeadChange = Math.max(maxLeadChange, leadChange);
			return;
		}

		if (s1new > s1Target || s2new > s2Target)
			return;

		boolean leadChagneIncreased = false;

		for (int i = 0; i < scores.length; i++) {
			if (s1old <= s2old) {
				if (s1new > s2new) {
					leadChagneIncreased = true;
					leadChange++;
				}
			} else {
				// s1old was bigger
				if (s1new < s2new) {
					leadChagneIncreased = true;
					leadChange++;
				}
			}

			getMaxLeadChanges(s1Target, s2Target, s1new, s2new, s1new, s2new + scores[i], leadChange);
			if (leadChagneIncreased)
				leadChange--;

			getMaxLeadChanges(s1Target, s2Target, s1new, s2new, s1new + scores[i], s2new, leadChange);
			if (leadChagneIncreased)
				leadChange--;

		}

	}

	public void getMaxLeadChangesI(int s1Target, int s2Target, int s1old, int s2old, int s1new, int s2new,
			int leadChange) {

		if (s1new == s1Target && s2new == s2Target) {
			System.out.println("## s1old = " + s1old + ", s2old = " + s2old + ", s1new = " + s1new + ", s2new = "
					+ s2new + ", leadChange = " + leadChange);
			maxLeadChange = Math.max(maxLeadChange, leadChange);
			return;
		}

		if (s1new > s1Target || s2new > s2Target)
			return;

		for (int i = 0; i < scores.length; i++) {
			if (s1old <= s2old) {
				if (s1new > s2new) {
					System.out.println("#1 s1old = " + s1old + ", s2old = " + s2old + ", s1new = " + s1new
							+ ", s2new = " + s2new + ", leadChange = " + leadChange);
					getMaxLeadChanges(s1Target, s2Target, s1new, s2new, s1new, s2new + scores[i], leadChange + 1);
				} else {
					System.out.println("#2 s1old = " + s1old + ", s2old = " + s2old + ", s1new = " + s1new
							+ ", s2new = " + s2new + ", leadChange = " + leadChange);

					getMaxLeadChanges(s1Target, s2Target, s1new, s2new, s1new + scores[i], s2new, leadChange);
				}
			} else {
				// s1old was bigger
				if (s1new < s2new) {
					System.out.println("#3 s1old = " + s1old + ", s2old = " + s2old + ", s1new = " + s1new
							+ ", s2new = " + s2new + ", leadChange = " + leadChange);

					getMaxLeadChanges(s1Target, s2Target, s1new, s2new, s1new + scores[i], s2new, leadChange + 1);
				} else {
					System.out.println("#4 s1old = " + s1old + ", s2old = " + s2old + ", s1new = " + s1new
							+ ", s2new = " + s2new + ", leadChange = " + leadChange);

					getMaxLeadChanges(s1Target, s2Target, s1new, s2new, s1new, s2new + scores[i], leadChange);
				}
			}
		}

	}

	public static void main(String[] args) {
		MaximumNumberOfLeadChanges ob = new MaximumNumberOfLeadChanges();
		int s1Target = 10;
		int s2Target = 6;
		int leadChange = 1;

		ob.getMaxLeadChanges(s1Target, s2Target, 0, 0, 0, 0, leadChange);
		System.out.println(ob.maxLeadChange);
	}
}
