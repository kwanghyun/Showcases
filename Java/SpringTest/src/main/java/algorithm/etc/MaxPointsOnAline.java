package algorithm.etc;

import java.util.HashMap;

import concurrency.book.composing_objects.Point;

/*
 * Given n points on a 2D plane, find the maximum number of points that lie
 * on the same straight line.
 * 
 * Analysis
 * 
 * This problem can be solve by counting points that have the same slope for
 * each point. When counting, we need to be careful about the duplicate
 * points and points on the vertical lines.
 */
public class MaxPointsOnAline {
	public int maxPoints(Point[] points) {
		if (points == null || points.length == 0)
			return 0;

		HashMap<Double, Integer> map = new HashMap<Double, Integer>();
		int max = 0;

		for (int i = 0; i < points.length; i++) {
			int duplicate = 1;//
			int vertical = 0;
			for (int j = i + 1; j < points.length; j++) {
				// handle duplicates and vertical
				if (points[i].x == points[j].x) {
					if (points[i].y == points[j].y) {
						duplicate++;
					} else {
						vertical++;
					}
				} else {
					double slope = points[j].y == points[i].y ? 0.0
							: (1.0 * (points[j].y - points[i].y)) / (points[j].x - points[i].x);

					if (map.get(slope) != null) {
						map.put(slope, map.get(slope) + 1);
					} else {
						map.put(slope, 1);
					}
				}
			}

			for (Integer count : map.values()) {
				if (count + duplicate > max) {
					max = count + duplicate;
				}
			}

			max = Math.max(vertical + duplicate, max);
			map.clear();
		}

		return max;
	}

	public static void main(String[] args) {
		Point[] points = new Point[10];
		for (int i = 0; i < 10; i++) {
			Point p = new Point(i, i);
			points[i] = p;
		}

		MaxPointsOnAline ob = new MaxPointsOnAline();
		System.out.println(ob.maxPoints(points));
	}
}
