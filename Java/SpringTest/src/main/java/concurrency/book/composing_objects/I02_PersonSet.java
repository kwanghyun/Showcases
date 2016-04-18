package concurrency.book.composing_objects;

import java.util.*;

import net.jcip.annotations.ThreadSafe;

/**
 * PersonSet
 * 
 * Using confinement to ensure thread safety
 *
 * This example makes no assumptions about the thread-safety of Person, but if
 * it is mutable, additional synchronization will be needed when accessing a
 * Person retrieved from a PersonSet. The most reliable way to do this would be
 * to make Person thread-safe; less reliable would be to guard the Person
 * objects with a lock and ensure that all clients follow the protocol of
 * acquiring the appropriate lock before accessing the Person. Instance
 * confinement is one of the easiest ways to build thread-safe classes. It also
 * allows flexibility in the choice of locking strategy; PersonSet happened to
 * use its own intrinsic lock to guard its state, but any lock, consistently
 * used, would do just as well. Instance confinement also allows different state
 * variables to be guarded by different locks
 */
@ThreadSafe
public class I02_PersonSet {
	private final Set<Person> mySet = new HashSet<Person>();

	public synchronized void addPerson(Person p) {
		mySet.add(p);
	}

	public synchronized boolean containsPerson(Person p) {
		return mySet.contains(p);
	}

	interface Person {
	}
}