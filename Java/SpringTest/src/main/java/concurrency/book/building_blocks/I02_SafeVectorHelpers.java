package concurrency.book.building_blocks;

import java.util.*;

/**
 * SafeVectorHelpers
 * <p/>
 * Compound actions on Vector using client-side locking
 * 
 */
public class I02_SafeVectorHelpers {
	public static Object getLast(Vector list) {
		synchronized (list) {
			int lastIndex = list.size() - 1;
			return list.get(lastIndex);
		}
	}

	public static void deleteLast(Vector list) {
		synchronized (list) {
			int lastIndex = list.size() - 1;
			list.remove(lastIndex);
		}
	}
}