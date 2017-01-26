package algorithm.stringArray;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

	public static void main(String[] args) throws Exception {

		// Load your own data for testing
		P[] points = new P[] { new P(2, 7), new P(4, 13), new P(5, 7), new P(10, 5), new P(13, 9), new P(15, 5),
				new P(17, 7), new P(19, 10), new P(22, 7), new P(25, 10), new P(29, 14), new P(30, 2) };

		Arrays.sort(points, xComparator); // sort by x, then y

		P[] closest = findClosest(points);
		P[] closestx = findMinDist(findMinDistNeighbor(points), closest);

		for (P p : closestx)
			System.out.println(p);
		System.out.println("dist: " + distance(closestx[0], closestx[1]));
	}

	/**
	 * Find min distance for neightbors in sorted by x-coord point list Fix
	 * divide problem where information is lost in algorithm
	 * 
	 * @param ps
	 * @return
	 * 
	 * 		O(n)
	 */
	static P[] findMinDistNeighbor(P[] ps) {
		double minDist = Double.MAX_VALUE;
		P[] pMin = new P[] { new P(0, 0), new P(Double.MAX_VALUE, Double.MAX_VALUE) };
		if (ps.length < 4)
			return pMin;

		for (int i = 0; i < ps.length - 3; i++) {
			P p1 = ps[i];
			P p2 = ps[i + 1];
			P p3 = ps[i + 2];
			P p4 = ps[i + 3];
			double dist1 = distance(p1, p2);
			double dist2 = distance(p1, p3);
			double dist3 = distance(p1, p4);
			if (dist1 < minDist) { // update
				minDist = dist1;
				pMin = new P[] { p1, p2 };
			}
			if (dist2 < minDist) { // update
				minDist = dist2;
				pMin = new P[] { p1, p3 };
			}
			if (dist3 < minDist) { // update
				minDist = dist3;
				pMin = new P[] { p1, p4 };
			}
		}
		return pMin;
	}

	static P[] findMinDist(P[] p1, P[] p2) {
		double d1 = distance(p1[0], p1[1]);
		double d2 = distance(p2[0], p2[1]);
		return d1 < d2 ? p1 : p2;
	}

	/**
	 * Closest pair O(nlogn) Ashish Sharma Rengakrishnan Subramanian November
	 * 28, 2001 http://www.cis.ksu.edu/~subbu/Papers/Closest%20pair.pdf
	 * 
	 * @throws Exception
	 */
	static P[] findClosest(P[] ps) throws Exception {
		// ps must be sorted in x, then y
		int n = ps.length;

		if (n <= 3) {
			return shortest(ps);
		} else {
			int left = n / 2;
			int right = n / 2 + n % 2;

			// the set datas
			P[] Pleft = new P[left];
			P[] Pright = new P[right];
			P[] Pleftmin, Prightmin, Pclosest;

			for (int i = 0; i < left; i++)
				Pleft[i] = ps[i];
			for (int i = 0; i < right; i++)
				Pright[i] = ps[i + left];

			Pleftmin = findClosest(Pleft);
			Prightmin = findClosest(Pright);
			Pclosest = mergePlanes(Pleftmin, Prightmin);
			return Pclosest;
		}
	}

	static P[] mergePlanes(P[] p1, P[] p2) throws Exception {

		if (p1.length > 2 || p2.length > 2)
			throw new Exception("Invalid state in mergePlanes");

		double d1 = distance(p1[0], p1[1]);
		double d2 = distance(p2[0], p2[1]);
		double D = d1 < d2 ? d1 : d2; // delta

		// minimum
		P[] pMin = d1 < d2 ? p1 : p2; // default either in left or right
										// sub-plane

		// examine for possible min dist where
		// one point is in left sub-plane and one point is in right sub-plane
		for (int i = 0; i < p1.length; i++) {
			for (int j = 0; j < p2.length; j++) {
				P pi = p1[i];
				P pj = p2[j];
				if (pi.equals(pj))
					continue;

				double xi = p1[i].getX();
				double xj = p2[j].getX();
				double yi = p1[i].getY();
				double yj = p2[j].getY();

				if (xi < xj + D && yi + D > yj && yj > yi - D) {
					if (distance(pi, pj) < D) {
						return new P[] { pi, pj };
					}
				}
			}
		}

		// either both points were in left or right sub-plane
		return pMin;
	}

	// O(n^2) naive version of closest pair
	static P[] shortest(P[] ps) {
		P p1 = null;
		P p2 = null;

		double distance = Double.MAX_VALUE;
		for (int i = 0; i < ps.length; i++) {
			for (int j = 0; j < i; j++) {
				if (i == j)
					continue;
				P ptemp1 = ps[i];
				P ptemp2 = ps[j];
				if (ptemp1.equals(ptemp2))
					continue;

				double newDistance = distance(ptemp1, ptemp2);
				if (newDistance < distance) {
					// update
					distance = newDistance;
					p1 = ptemp1;
					p2 = ptemp2;
				}
			}
		}
		P[] points = new P[] { p1, p2 };
		return points;
	}

	static P[] union(P[] ps1, P[] ps2) {
		P[] ps = new P[ps1.length + ps2.length];
		for (int i = 0; i < ps1.length; i++)
			ps[i] = ps1[i];
		for (int i = 0; i < ps2.length; i++)
			ps[i + ps1.length] = ps2[i];
		return ps;
	}

	static double distance(P p1, P p2) {
		return p1.distance(p2); // Java api, Euclidean dist
	}

	static final Comparator<P> xComparator = new Comparator<P>() {
		@Override
		public int compare(P a, P b) {
			if (a.x < b.x) {
				return -1;
			}
			if (a.x > b.x) {
				return 1;
			}
			// if equal, sort by y
			if (a.y < b.y) {
				return -1;
			}
			if (a.y > b.y) {
				return 1;
			}
			return 0;
		}
	};
}