package concurrency.book.atomic;

import java.util.concurrent.locks.*;

import net.jcip.annotations.*;

/**
 * ReentrantLockPseudoRandom
 * <p/>
 * Random number generator using ReentrantLock
 *
 * To demonstrate the differences in scalability between locks and atomic
 * variables, we constructed a benchmark comparing several implementations of a
 * pseudorandom number generator (PRNG). In a PRNG, the next “random” number is
 * a deterministic function of the previous number, so a PRNG must remember the
 * previous number as part of its state.
 * 
 * Listings 15.4 and 15.5 show two implementations of a thread-safe PRNG, one
 * using ReentrantLock and the other using AtomicInteger. The test driver
 * invokes each repeatedly; each iteration generates a random number (which
 * fetches and modifies the shared seed state) and also performs a number of
 * “busy-work” iterations that operate strictly on thread-local data. This
 * simulates typical operations that include some portion of operating on shared
 * state and some portion of operating on thread-local state.
 * 
 * Figures 15.1 and 15.2 show throughput with low and moderate levels of
 * simulated work in each iteration. With a low level of thread-local
 * computation, the lock or atomic variable experiences heavy contention; with
 * more thread-local computation, the lock or atomic variable experiences less
 * contention since it is accessed less often by each thread.
 * 
 * As these graphs show, at high contention levels locking tends to outperform
 * atomic variables, but at more realistic contention levels atomic variables
 * outperform locks.[6] This is because a lock reacts to contention by
 * suspending threads, reducing CPU usage and synchronization traffic on the
 * shared memory bus. (This is similar to how blocking producers in a
 * producer-consumer design reduces the load on consumers and thereby lets them
 * catch up.) On the other hand, with atomic variables, contention management is
 * pushed back to the calling class. Like most CAS-based algorithms,
 * AtomicPseudoRandom reacts to contention by trying again immediately, which is
 * usually the right approach but in a high-contention environment just creates
 * more contention.
 * 
 * [6] The same holds true in other domains: traffic lights provide better
 * throughput for high traffic but rotaries provide better throughput for low
 * traffic; the contention scheme used by ethernet networks performs better at
 * low traffic levels, but the token-passing scheme used by token ring networks
 * does better with heavy traffic.
 * 
 * Before we condemn AtomicPseudoRandom as poorly written or atomic variables as
 * a poor choice compared to locks, we should realize that the level of
 * contention in Figure 15.1 is unrealistically high: no real program does
 * nothing but contend for a lock or atomic variable. In practice, atomics tend
 * to scale better than locks because atomics deal more effectively with typical
 * contention levels.
 * 
 * The performance reversal between locks and atomics at differing levels of
 * contention illustrates the strengths and weaknesses of each. With low to
 * moderate contention, atomics offer better scalability; with high contention,
 * locks offer better contention avoidance. (CAS-based algorithms also
 * outperform lock-based ones on single-CPU systems, since a CAS always succeeds
 * on a single-CPU system except in the unlikely case that a thread is preempted
 * in the middle of the read-modify-write operation.)
 * 
 * Figures 15.1 and 15.2 include a third curve; an implementation of
 * PseudoRandom that uses a ThreadLocal for the PRNG state. This implementation
 * approach changes the behavior of the class—each thread sees its own private
 * sequence of pseudorandom numbers, instead of all threads sharing one
 * sequence—but illustrates that it is often cheaper to not share state at all
 * if it can be avoided. We can improve scalability by dealing more effectively
 * with contention, but true scalability is achieved only by eliminating
 * contention entirely.
 */
@ThreadSafe
public class I04_ReentrantLockPseudoRandom extends PseudoRandom {
	private final Lock lock = new ReentrantLock(false);
	private int seed;

	I04_ReentrantLockPseudoRandom(int seed) {
		this.seed = seed;
	}

	public int nextInt(int n) {
		lock.lock();
		try {
			int s = seed;
			seed = calculateNext(s);
			int remainder = s % n;
			return remainder > 0 ? remainder : remainder + n;
		} finally {
			lock.unlock();
		}
	}
}