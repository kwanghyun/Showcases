package concurrency.book.building_blocks;

import java.util.*;
import java.util.concurrent.*;

/**
 * Memoizer3
 * <p/>
 * Replacing HashMap with ConcurrentHashMap
 *
 * Memoizer3 in Listing 5.18 redefines the backing Map for the value cache as a
 * ConcurrentHashMap<A, Future<V>> instead of a ConcurrentHashMap<A, V>.
 * Memoizer3 first checks to see if the appropriate calculation has been started
 * (as opposed to finished, as in Memoizer2). If not, it creates a FutureTask,
 * registers it in the Map, and starts the computation; otherwise it waits for
 * the result of the existing computation. The result might be available
 * immediately or might be in the process of being computed—but this is
 * transparent to the caller of Future.get.
 *
 * The Memoizer3 implementation is almost perfect: it exhibits very good
 * concurrency (mostly derived from the excellent concurrency of
 * ConcurrentHashMap), the result is returned efficiently if it is already
 * known, and if the computation is in progress by another thread, newly
 * arriving threads wait patiently for the result. It has only one defect—there
 * is still a small window of vulnerability in which two threads might compute
 * the same value. This window is far smaller than in Memoizer2, but because the
 * if block in compute is still a non-atomic check-then-act sequence, it is
 * possible for two threads to call compute with the same value at roughly the
 * same time, both see that the cache does not contain the desired value, and
 * both start the computation.
 * 
 * 
 */
public class I12_Memoizer3<A, V> implements Computable<A, V> {
	private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;

	public I12_Memoizer3(Computable<A, V> c) {
		this.c = c;
	}

	public V compute(A arg) throws InterruptedException {
		Future<V> f = cache.get(arg);
		if (f == null) {
			Callable<V> eval = new Callable<V>() {
				public V call() throws InterruptedException {
					return c.compute(arg);
				}
			};

			/*
			 * Creates a FutureTask that will, upon running, execute the given
			 * Callable.
			 */
			FutureTask<V> ft = new FutureTask<>(eval);
			f = ft;
			cache.put(arg, ft);
			ft.run(); // call to c.compute happens here
		}

		try {
			return f.get();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			throw LaunderThrowable.launderThrowable(e.getCause());
		}
	}
}