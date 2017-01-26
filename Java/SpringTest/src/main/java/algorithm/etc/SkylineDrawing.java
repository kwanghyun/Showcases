package algorithm.etc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/*
 * Given n rectangular buildings in a 2-dimensional city, computes the skyline
 * of these buildings, eliminating hidden lines. The main task is to view
 * buildings from a side and remove all sections that are not visible.
 * 
 * All buildings share common bottom and every building is represented by
 * triplet (left, ht, right)
 * 
 * ‘left': is x coordinated of left side (or wall). ‘right': is x coordinate of
 * right side ‘ht': is height of building.
 * 
 * For example, the building on right side (the figure is taken from here) is
 * represented as (1, 11, 5)
 * 
 * A skyline is a collection of rectangular strips. A rectangular strip is
 * represented as a pair (left, ht) where left is x coordinate of left side of
 * strip and ht is height of strip.
 * 
 * Example
 * 
 * Input: Array of buildings { (1,11,5), (2,6,7), (3,13,9), (12,7,16),
 * (14,3,25), (19,18,22), (23,13,29), (24,4,28) }
 * 
 * Output: Skyline (an array of rectangular strips)
 * 
 * A strip has x coordinate of left side and height (1, 11), (3, 13), (9, 0),
 * (12, 7), (16, 3), (19, 18), (22, 3), (25, 0)
 * 
 * The below figure (taken from here) demonstrates input and output. The left
 * side shows buildings and right side shows skyline.
 * 
 * Consider following as another example when there is only one building Input:
 * {(1, 11, 5)} Output: (1, 11), (5, 0)
 */
public class SkylineDrawing {

	static class BuildingPoint implements Comparable<BuildingPoint> {
		int x;
		boolean isStart;
		int height;

		@Override
		public int compareTo(BuildingPoint o) {
			// first compare by x.
			// If they are same then use this logic
			// if two starts are compared then higher height building should be
			// picked first
			// if two ends are compared then lower height building should be
			// picked first
			// if one start and end is compared then start should appear before
			// end
			if (this.x != o.x) {
				return this.x - o.x;
			} else {
				return (this.isStart ? -this.height : this.height) - (o.isStart ? -o.height : o.height);
			}
		}
	}

	public List<int[]> getSkyline(int[][] buildings) {

		BuildingPoint[] buildingPoints = new BuildingPoint[buildings.length * 2];
		int index = 0;
		for (int building[] : buildings) {
			buildingPoints[index] = new BuildingPoint();
			buildingPoints[index].x = building[0];
			buildingPoints[index].isStart = true;
			buildingPoints[index].height = building[2];

			buildingPoints[index + 1] = new BuildingPoint();
			buildingPoints[index + 1].x = building[1];
			buildingPoints[index + 1].isStart = false;
			buildingPoints[index + 1].height = building[2];
			index += 2;
		}
		Arrays.sort(buildingPoints);

		// using TreeMap because it gives log time performance.
		// PriorityQueue in java does not support remove(object) operation in
		// log time.
		TreeMap<Integer, Integer> queue = new TreeMap<>();
		queue.put(0, 1);

		int prevMaxHeight = 0;
		List<int[]> result = new ArrayList<>();
		for (BuildingPoint buildingPoint : buildingPoints) {
			if (buildingPoint.isStart) {
				queue.compute(buildingPoint.height, (key, value) -> {
					if (value != null) {
						return value + 1;
					}
					return 1;
				});
			} else {
				queue.compute(buildingPoint.height, (key, value) -> {
					if (value == 1) {
						return null;
					}
					return value - 1;
				});
			}
			// peek the current height after addition or removal of building x.
			int currentMaxHeight = queue.lastKey();
			// if height changes from previous height then this building x
			// becomes critcal x. So add it to the result.
			if (prevMaxHeight != currentMaxHeight) {
				result.add(new int[] { buildingPoint.x, currentMaxHeight });
				prevMaxHeight = currentMaxHeight;
			}
		}
		return result;
	}

	public static void main(String args[]) {
		int[][] buildings = { { 1, 3, 4 }, { 3, 4, 4 }, { 2, 6, 2 }, { 8, 11, 4 }, { 7, 9, 3 }, { 10, 11, 2 } };
		SkylineDrawing sd = new SkylineDrawing();
		List<int[]> criticalPoints = sd.getSkyline(buildings);
		criticalPoints.forEach(cp -> System.out.println(cp[0] + " " + cp[1]));

	}
}