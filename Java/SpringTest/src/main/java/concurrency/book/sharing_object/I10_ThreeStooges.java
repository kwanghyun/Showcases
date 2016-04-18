package concurrency.book.sharing_object;

import java.util.*;

/**
 * 
 * ThreeStooges
 *
 * Immutable objects can still use mutable objects internally to manage their
 * state, as illustrated by ThreeStooges
 * 
 * While the Set that stores the names is mutable, the design of ThreeStooges
 * makes it impossible to modify that Set after construction. The stooges
 * reference is final, so all object state is reached through a final field. The
 * last requirement, proper construction, is easily met since the constructor
 * does nothing that would cause the this reference to become accessible to code
 * other than the constructor and its caller.
 */

public final class I10_ThreeStooges {
	private final Set<String> stooges = new HashSet<String>();

	public I10_ThreeStooges() {
		stooges.add("Moe");
		stooges.add("Larry");
		stooges.add("Curly");
	}

	public boolean isStooge(String name) {
		return stooges.contains(name);
	}

	public String getStoogeNames() {
		List<String> stooges = new Vector<String>();
		stooges.add("Moe");
		stooges.add("Larry");
		stooges.add("Curly");
		return stooges.toString();
	}
}