package concurrency.book.performance;

import java.util.*;

import net.jcip.annotations.*;

/**
 * ServerStatusAfterSplit
 * <p/>
 * ServerStatus refactored to use split locks
 *
 * Instead of guarding both users and queries with the ServerStatus lock, we can
 * instead guard each with a separate lock, as shown in Listing 11.7. After
 * splitting the lock, each new finer-grained lock will see less locking traffic
 * than the original coarser lock would have. (Delegating to a thread-safe Set
 * implementation for users and queries instead of using explicit
 * synchronization would implicitly provide lock splitting, as each Set would
 * use a different lock to guard its state.)
 * 
 * Splitting a lock into two offers the greatest possibility for improvement
 * when the lock is experiencing moderate but not heavy contention. Splitting
 * locks that are experiencing little contention yields little net improvement
 * in performance or throughput, although it might increase the load threshold
 * at which performance starts to degrade due to contention. Splitting locks
 * experiencing moderate contention might actually turn them into mostly
 * uncontended locks, which is the most desirable outcome for both performance
 * and scalability.
 */
@ThreadSafe
public class I05_ServerStatusAfterSplit {
	@GuardedBy("users")
	public final Set<String> users;
	@GuardedBy("queries")
	public final Set<String> queries;

	public I05_ServerStatusAfterSplit() {
		users = new HashSet<String>();
		queries = new HashSet<String>();
	}

	public void addUser(String u) {
		synchronized (users) {
			users.add(u);
		}
	}

	public void addQuery(String q) {
		synchronized (queries) {
			queries.add(q);
		}
	}

	public void removeUser(String u) {
		synchronized (users) {
			users.remove(u);
		}
	}

	public void removeQuery(String q) {
		synchronized (users) {
			queries.remove(q);
		}
	}
}