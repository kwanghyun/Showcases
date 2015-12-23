package algorithm.etc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/*
 * Java program to design a data structure that support folloiwng operations
 * in the 0(1) time a) Insert b) Delete c) Search d) getRandom
 */

public class OoneDS {
	ArrayList<Integer> arr;
	HashMap<Integer, Integer> map;

	public OoneDS() {
		arr = new ArrayList<Integer>();
		map = new HashMap<Integer, Integer>();
	}

	void add(int x) {
		if (map.get(x) != null)
			return;

		int idx = arr.size();
		arr.add(x);

		map.put(x, idx);
	}

	void remove(int x) {
		Integer index = map.get(x);
		if (index == null)
			return;

		map.remove(x);

		int size = arr.size();
		Integer last = arr.get(size - 1);
		arr.set(index, last);
		arr.remove(size - 1);
		map.put(last, index);
	}

	int getRandom() {
		Random rand = new Random();
		int index = rand.nextInt(arr.size());

		return arr.get(index);
	}

	Integer search(int x) {
		return map.get(x);
	}

	public static void main(String[] args) {
		OoneDS obj = new OoneDS();
		obj.add(1);
		obj.add(2);
		obj.add(3);
		obj.add(4);
		obj.add(5);
		System.out.println(obj.getRandom());
		System.out.println(obj.getRandom());
		System.out.println(obj.getRandom());
		obj.remove(5);
		System.out.println(obj.getRandom());
		obj.remove(4);
		System.out.println(obj.getRandom());
		obj.remove(3);
		System.out.println(obj.getRandom());
		obj.remove(2);
		System.out.println(obj.getRandom());
		
	}
}
