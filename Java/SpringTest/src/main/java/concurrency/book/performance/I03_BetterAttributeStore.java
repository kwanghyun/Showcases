package concurrency.book.performance;

import java.util.*;
import java.util.regex.*;

import net.jcip.annotations.*;

/**
 * BetterAttributeStore
 * <p/>
 * Reducing lock duration
 *
 * BetterAttributeStore in Listing 11.5 rewrites AttributeStore to reduce
 * significantly the lock duration. The first step is to construct the Map key
 * associated with the user's location, a string of the form
 * users.name.location. This entails instantiating a StringBuilder object,
 * appending several strings to it, and instantiating the result as a String.
 * After the location has been retrieved, the regular expression is matched
 * against the resulting location string. Because constructing the key string
 * and processing the regular expression do not access shared state, they need
 * not be executed with the lock held. BetterAttributeStore factors these steps
 * out of the synchronized block, thus reducing the time the lock is held.
 * 
 * Reducing the scope of the lock in userLocationMatches substantially reduces
 * the number of instructions that are executed with the lock held. By Amdahl's
 * law, this removes an impediment to scalability because the amount of
 * serialized code is reduced.
 * 
 * Because AttributeStore has only one state variable, attributes, we can
 * improve it further by the technique of delegating thread safety (Section
 * 4.3). By replacing attributes with a thread-safe Map (a Hashtable,
 * synchronizedMap, or ConcurrentHashMap), AttributeStore can delegate all its
 * thread safety obligations to the underlying thread-safe collection. This
 * eliminates the need for explicit synchronization in AttributeStore, reduces
 * the lock scope to the duration of the Map access, and removes the risk that a
 * future maintainer will undermine thread safety by forgetting to acquire the
 * appropriate lock before accessing attributes.
 * 
 * While shrinking synchronized blocks can improve scalability, a synchronized
 * block can be too small—operations that need to be atomic (such updating
 * multiple variables that participate in an invariant) must be contained in a
 * single synchronized block. And because the cost of synchronization is
 * nonzero, breaking one synchronized block into multiple synchronized blocks
 * (correctness permitting) at some point becomes counterproductive in terms of
 * performance.[9] The ideal balance is of course platform-dependent, but in
 * practice it makes sense to worry about the size of a synchronized block only
 * when you can move “substantial” computation or blocking operations out of it.
 */
@ThreadSafe
public class I03_BetterAttributeStore {
	@GuardedBy("this")
	private final Map<String, String> attributes = new HashMap<String, String>();

	public boolean userLocationMatches(String name, String regexp) {
		String key = "users." + name + ".location";
		String location;
		synchronized (this) {
			location = attributes.get(key);
		}
		if (location == null)
			return false;
		else
			return Pattern.matches(regexp, location);
	}
}