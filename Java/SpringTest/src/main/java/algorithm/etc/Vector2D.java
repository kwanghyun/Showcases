package algorithm.etc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
	Implement an iterator to flatten a 2d vector.
	
	For example,
	Given 2d vector =
	[
	  [1,2],
	  [3],
	  [4,5,6]
	]

 * Your Vector2D object will be instantiated and called as such:
 * Vector2D i = new Vector2D(vec2d);
 * while (i.hasNext()) v[f()] = i.next();
 */
public class Vector2D implements Iterator<Integer> {
	int r = 0;
	int c = 0;
	int rLen = 0;
	int cLen = 0;
	List<List<Integer>> vec2d;

	public Vector2D(List<List<Integer>> vec2d) {
		if (vec2d == null || vec2d.size() == 0)
			return;
		this.vec2d = vec2d;
		rLen = vec2d.size();
		cLen = vec2d.get(0).size();
	}

	@Override
	public Integer next() {
		int retVal = 0;
		retVal = vec2d.get(r).get(c);
		c++;
		return retVal;
	}

	public void moveToNextRow() {
		c = 0;
		r++;
		if (r < rLen)
			cLen = vec2d.get(r).size();
		else {
			cLen = -1;
		}
	}

	@Override
	public boolean hasNext() {
		while (c == cLen) {
			moveToNextRow();
		}

		if (r < rLen)
			return true;
		return false;
	}

	public static void main(String[] args) {
		List<List<Integer>> input = new ArrayList<>();

		// ArrayList<Integer> list1 = new ArrayList<>();
		// ArrayList<Integer> list2 = new ArrayList<>();
		// ArrayList<Integer> list3 = new ArrayList<>();
		// list1.add(1);
		// list1.add(2);
		// list2.add(3);
		// list3.add(4);
		// list3.add(5);
		// list3.add(6);
		// input.add(list1);
		// input.add(list2);
		// input.add(list3);

		// ArrayList<Integer> list1 = new ArrayList<>();
		// ArrayList<Integer> list2 = new ArrayList<>();
		// list2.add(3);
		// input.add(list1);
		// input.add(list2);

		// ArrayList<Integer> list1 = new ArrayList<>();
		// ArrayList<Integer> list2 = new ArrayList<>();
		// list1.add(1);
		// input.add(list1);
		// input.add(list2);

		ArrayList<Integer> list1 = new ArrayList<>();
		ArrayList<Integer> list2 = new ArrayList<>();
		ArrayList<Integer> list3 = new ArrayList<>();
		list3.add(1);
		input.add(list1);
		input.add(list2);
		input.add(list3);

		System.out.println(input);
		Vector2D it = new Vector2D(input);
		while (it.hasNext()) {
			System.out.println(it.next());
		}

	}
}
