package concurrency.book.building_blocks;

import java.util.*;

/**
 * UnsafeVectorHelpers
 * 
 * Compound actions on a Vector that may produce confusing results
 *
 * If thread A calls getLast on a Vector with ten elements, thread B calls
 * deleteLast on the same Vector, getLast throws ArrayIndexOutOfBoundsException.
 */
public class I01_UnsafeVectorHelpers {
	public static Object getLast(Vector list) {
		int lastIndex = list.size() - 1;
		return list.get(lastIndex);
	}

	public static void deleteLast(Vector list) {
		int lastIndex = list.size() - 1;
		list.remove(lastIndex);
	}
}