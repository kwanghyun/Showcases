package concurrency.book.explicit_lock;

import java.util.*;
import java.util.concurrent.locks.*;

/**
 * ReadWriteMap
 * <p/>
 * Wrapping a Map with a read-write lock
 *
 * 13.2. Performance Considerations
 * 
 * When ReentrantLock was added in Java 5.0, it offered far better contended
 * performance than intrinsic locking. For synchronization primitives, contended
 * performance is the key to scalability: if more resources are expended on lock
 * management and scheduling, fewer are available for the application. A better
 * lock implementation makes fewer system calls, forces fewer context switches,
 * and initiates less memory-synchronization traffic on the shared memory bus,
 * operations that are time-consuming and divert computing resources from the
 * program.
 * 
 * Java 6 uses an improved algorithm for managing intrinsic locks, similar to
 * that used by ReentrantLock, that closes the scalability gap considerably. On
 * Java 5.0, ReentrantLock offers considerably better throughput, but on Java 6,
 * the two are quite close.
 * 
 * 13.3. Fairness
 * 
 * The ReentrantLock constructor offers a choice of two fairness options: create
 * a non-fair lock (the default) or a fair lock. Threads acquire a fair lock in
 * the order in which they requested it, whereas a non-fair lock permits
 * barging: threads requesting a lock can jump ahead of the queue of waiting
 * threads if the lock happens to be available when it is requested. (Semaphore
 * also offers the choice of fair or non-fair acquisition ordering.) Non-fair
 * ReentrantLocks do not go out of their way to promote barging—they simply
 * don't prevent a thread from barging if it shows up at the right time. With a
 * fair lock, a newly requesting thread is queued if the lock is held by another
 * thread or if threads are queued waiting for the lock; with a non-fair lock,
 * the thread is queued only if the lock is currently held
 * 
 * 13.4. Choosing Between Synchronized and ReentrantLock
 * 
 * ReentrantLock provides the same locking and memory semantics as intrinsic
 * locking, as well as additional features such as timed lock waits,
 * interruptible lock waits, fairness, and the ability to implement
 * non-block-structured locking. The performance of ReentrantLock appears to
 * dominate that of intrinsic locking, winning slightly on Java 6 and
 * dramatically on Java 5.0. So why not deprecate synchronized and encourage all
 * new concurrent code to use ReentrantLock? Some authors have in fact suggested
 * this, treating synchronized as a “legacy” construct. But this is taking a
 * good thing way too far.
 * 
 * Intrinsic locks still have significant advantages over explicit locks. The
 * notation is familiar and compact, and many existing programs already use
 * intrinsic locking—and mixing the two could be confusing and error-prone.
 * Reentrant-Lock is definitely a more dangerous tool than synchronization; if
 * you forget to wrap the unlock call in a finally block, your code will
 * probably appear to run properly, but you've created a time bomb that may well
 * hurt innocent bystanders. Save ReentrantLock for situations in which you need
 * something ReentrantLock provides that intrinsic locking doesn't.
 * 
 * ReentrantLock is an advanced tool for situations where intrinsic locking is
 * not practical. Use it if you need its advanced features: timed, polled, or
 * interruptible lock acquisition, fair queueing, or non-block-structured
 * locking. Otherwise, prefer synchronized.
 * 
 * Future performance improvements are likely to favor synchronized over
 * ReentrantLock. Because synchronized is built into the JVM, it can perform
 * optimizations such as lock elision for thread-confined lock objects and lock
 * coarsening to eliminate synchronization with intrinsic locks (see Section
 * 11.3.2); doing this with library-based locks seems far less likely. Unless
 * you are deploying on Java 5.0 for the foreseeable future and you have a
 * demonstrated need for ReentrantLock's scalability benefits on that platform,
 * it is not a good idea to choose ReentrantLock over synchronized for
 * performance reasons.
 */
public class I04_ReadWriteMap<K, V> {
	private final Map<K, V> map;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock r = lock.readLock();
	private final Lock w = lock.writeLock();

	public I04_ReadWriteMap(Map<K, V> map) {
		this.map = map;
	}

	public V put(K key, V value) {
		w.lock();
		try {
			return map.put(key, value);
		} finally {
			w.unlock();
		}
	}

	public V remove(Object key) {
		w.lock();
		try {
			return map.remove(key);
		} finally {
			w.unlock();
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		w.lock();
		try {
			map.putAll(m);
		} finally {
			w.unlock();
		}
	}

	public void clear() {
		w.lock();
		try {
			map.clear();
		} finally {
			w.unlock();
		}
	}

	public V get(Object key) {
		r.lock();
		try {
			return map.get(key);
		} finally {
			r.unlock();
		}
	}

	public int size() {
		r.lock();
		try {
			return map.size();
		} finally {
			r.unlock();
		}
	}

	public boolean isEmpty() {
		r.lock();
		try {
			return map.isEmpty();
		} finally {
			r.unlock();
		}
	}

	public boolean containsKey(Object key) {
		r.lock();
		try {
			return map.containsKey(key);
		} finally {
			r.unlock();
		}
	}

	public boolean containsValue(Object value) {
		r.lock();
		try {
			return map.containsValue(value);
		} finally {
			r.unlock();
		}
	}
}