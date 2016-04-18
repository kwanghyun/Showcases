package concurrency.book.performance;

import java.util.*;

import net.jcip.annotations.*;

/**
 * ServerStatusBeforeSplit
 * <p/>
 * Candidate for lock splitting
 * 
 * The other way to reduce the fraction of time that a lock is held (and
 * therefore the likelihood that it will be contended) is to have threads ask
 * for it less often. This can be accomplished by lock splitting and lock
 * striping, which involve using separate locks to guard multiple independent
 * state variables previously guarded by a single lock. These techniques reduce
 * the granularity at which locking occurs, potentially allowing greater
 * scalability—but using more locks also increases the risk of deadlock.
 * 
 * As a thought experiment, imagine what would happen if there was only one lock
 * for the entire application instead of a separate lock for each object. Then
 * execution of all synchronized blocks, regardless of their lock, would be
 * serialized. With many threads competing for the global lock, the chance that
 * two threads want the lock at the same time increases, resulting in more
 * contention. So if lock requests were instead distributed over a larger set of
 * locks, there would be less contention. Fewer threads would be blocked waiting
 * for locks, thus increasing scalability.
 * 
 * If a lock guards more than one independent state variable, you may be able to
 * improve scalability by splitting it into multiple locks that each guard
 * different variables. This results in each lock being requested less often.
 * 
 * ServerStatus in Listing 11.6 shows a portion of the monitoring interface for
 * a database server that maintains the set of currently logged-on users and the
 * set of currently executing queries. As a user logs on or off or query
 * execution begins or ends, the ServerStatus object is updated by calling the
 * appropriate add or remove method. The two types of information are completely
 * independent; ServerStatus could even be split into two separate classes with
 * no loss of functionality.
 */
@ThreadSafe
public class I04_ServerStatusBeforeSplit {
	@GuardedBy("this")
	public final Set<String> users;
	@GuardedBy("this")
	public final Set<String> queries;

	public I04_ServerStatusBeforeSplit() {
		users = new HashSet<String>();
		queries = new HashSet<String>();
	}

	public synchronized void addUser(String u) {
		users.add(u);
	}

	public synchronized void addQuery(String q) {
		queries.add(q);
	}

	public synchronized void removeUser(String u) {
		users.remove(u);
	}

	public synchronized void removeQuery(String q) {
		queries.remove(q);
	}
}