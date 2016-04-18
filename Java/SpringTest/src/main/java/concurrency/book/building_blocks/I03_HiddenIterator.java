package concurrency.book.building_blocks;

import java.util.*;

import net.jcip.annotations.*;

/**
 * HiddenIterator
 * 
 * Iteration hidden within string concatenation
 * 
 * While locking can prevent iterators from throwing
 * ConcurrentModificationException, you have to remember to use locking
 * everywhere a shared collection might be iterated. This is trickier than it
 * sounds, as iterators are sometimes hidden, as in HiddenIterator in Listing
 * 5.6. There is no explicit iteration in HiddenIterator, but the code in bold
 * entails iteration just the same. The string concatenation gets turned by the
 * compiler into a call to StringBuilder.append(Object), which in turn invokes
 * the collection's toString method—and the implementation of toString in the
 * standard collections iterates the collection and calls toString on each
 * element to produce a nicely formatted representation of the collection's
 * contents. The addTenThings method could throw
 * ConcurrentModificationException, because the collection is being iterated by
 * toString in the process of preparing the debugging message. Of course, the
 * real problem is that HiddenIterator is not thread-safe; the HiddenIterator
 * lock should be acquired before using set in the println call, but debugging
 * and logging code commonly neglect to do this. The real lesson here is that
 * the greater the distance between the state and the synchronization that
 * guards it, the more likely that someone will forget to use proper
 * synchronization when accessing that state. If HiddenIterator wrapped the
 * HashSet with a synchronizedSet, encapsulating the synchronization, this sort
 * of error would not occur.
 * 
 * Just as encapsulating an object's state makes it easier to preserve its
 * invariants, encapsulating its synchronization makes it easier to enforce its
 * synchronization policy.
 */
public class I03_HiddenIterator {
	@GuardedBy("this")
	private final Set<Integer> set = new HashSet<Integer>();

	public synchronized void add(Integer i) {
		set.add(i);
	}

	public synchronized void remove(Integer i) {
		set.remove(i);
	}

	public void addTenThings() {
		Random r = new Random();
		for (int i = 0; i < 10; i++)
			add(r.nextInt());
		System.out.println("DEBUG: added ten elements to " + set);
	}

	public static void main(String[] args) {
		I03_HiddenIterator ob = new I03_HiddenIterator();
		ob.addTenThings();
	}
}