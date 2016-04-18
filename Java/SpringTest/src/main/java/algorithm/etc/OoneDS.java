package algorithm.etc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/*
 * Java program to design a data structure that support folloiwng operations
 * in the 0(1) time a) Insert b) Delete c) Search d) getRandom
 */

public class OoneDS<T> {
	ArrayList<T> list;
	HashMap<T, Integer> map;

	public OoneDS() {
		list = new ArrayList<T>();
		map = new HashMap<T, Integer>();
	}

	public void insert(T val) {
		if (map.get(val) != null)
			return;
		
		//get index before add element
		int idx = list.size();
		list.add(val);
		map.put(val, idx);
	}

	public void remove(T val) {
		Integer oldIdx = map.get(val);
		if (oldIdx == null)
			return;

		int lastIdx = list.size();
		T lastVal = list.get(lastIdx - 1);

		list.set(oldIdx, lastVal);
		map.put(lastVal, oldIdx);

		list.remove(lastIdx - 1);
		map.remove(val);

	}

	public T getRandom() {
		Random rand = new Random();
		int index = rand.nextInt(list.size());

		return list.get(index);
	}

	public Integer search(int val) {
		return map.get(val);
	}

	public static void main(String[] args) {
		OoneDS<Integer> obj = new OoneDS<>();
		obj.insert(1);

		obj.insert(2);
		obj.insert(3);
		obj.insert(4);
		obj.insert(5);
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
		obj.remove(1);

	}
}
