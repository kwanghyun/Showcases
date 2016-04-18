package concurrency.book.composing_objects;

import java.util.*;

import net.jcip.annotations.*;

/**
 * BetterVector
 * <p/>
 * Extending Vector to have a put-if-absent method
 */
@ThreadSafe
public class I12_BetterVector<E> extends Vector<E> {
	// When extending a serializable class, you should redefine serialVersionUID
	static final long serialVersionUID = -3963416950630760754L;

	public synchronized boolean putIfAbsent(E x) {
		boolean absent = !contains(x);
		if (absent)
			add(x);
		return absent;
	}
}