package algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * While analyzing source code of a large number of open source Java projects, I found
 Java developers frequently sort in two ways. One is using the sort() method of Collections
 or Arrays, and the other is using sorted data structures, such as TreeMap and
 TreeSet.
 */
public class GeneralSort {
	public static void main(String[] args) {
		
		 /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		  * Collections.sort
		  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		List<Object> list = new ArrayList<Object>();
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		 /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		  * Arrays.sort
		  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		Object[] arr = new Object[10];
		Arrays.sort(arr, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		
		Set<Object> unsortedSet = new HashSet<Object>();
		Map<String, Integer> unsortedMap = new HashMap<String, Integer>();
		
		 /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		  * TreeSet
		  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		Set<Object> sortedSet = new TreeSet<Object>(new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		sortedSet.addAll(unsortedSet);
		
		 /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		  * TreeMap
		  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		Map<String, Integer> sortedMap = new TreeMap<String, Integer>(
				String.CASE_INSENSITIVE_ORDER);
		sortedMap.putAll(unsortedMap);
		/*
		 * TreeMap - using String.CASE_INSENSITIVE_ORDER which is a Comparator
		 * that orders Strings by compareToIgnoreCase
		 */
		
		// TreeMap - In general, defined comparator
		Map<Object, String> sortedMap2 = new TreeMap<Object, String>(
				new Comparator<Object>() {
					public int compare(Object o1, Object o2) {
						return o1.toString().compareTo(o2.toString());
					}
				});
		sortedMap.putAll(unsortedMap);
		
		/*
		 * This approach is very useful, if you would do a lot of search
		 * operations for the collection. The sorted data structure will give
		 * time complexity of O(logn), which is lower than O(n).
		 */
	}
}
