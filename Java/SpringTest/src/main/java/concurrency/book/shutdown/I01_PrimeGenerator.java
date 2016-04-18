package concurrency.book.shutdown;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

import net.jcip.annotations.*;

/**
 * PrimeGenerator
 * <p/>
 * Using a volatile field to hold cancellation state
 *
 * There is no safe way to preemptively stop a thread in Java, and therefore no
 * safe way to preemptively stop a task. There are only cooperative mechanisms,
 * by which the task and the code requesting cancellation follow an agreed-upon
 * protocol.
 *
 * One such cooperative mechanism is setting a “cancellation requested” flag
 * that the task checks periodically; if it finds the flag set, the task
 * terminates early. PrimeGenerator in Listing 7.1, which enumerates prime
 * numbers until it is cancelled, illustrates this technique. The cancel method
 * sets the cancelled flag, and the main loop polls this flag before searching
 * for the next prime number. (For this to work reliably, cancelled must be
 * volatile.)
 * 
 * The cancel method is called from a finally block to ensure that the prime
 * generator is cancelled even if the the call to sleep is interrupted. If
 * cancel were not called, the prime-seeking thread would run forever, consuming
 * CPU cycles and preventing the JVM from exiting.
 */
@ThreadSafe
public class I01_PrimeGenerator implements Runnable {
	private static ExecutorService exec = Executors.newCachedThreadPool();

	@GuardedBy("this")
	private final List<BigInteger> primes = new ArrayList<BigInteger>();
	private volatile boolean cancelled;

	public void run() {
		BigInteger p = BigInteger.ONE;
		while (!cancelled) {
			p = p.nextProbablePrime();
			synchronized (this) {
				primes.add(p);
			}
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}

	static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
		I01_PrimeGenerator generator = new I01_PrimeGenerator();
		exec.execute(generator);
		try {
			SECONDS.sleep(1);
		} finally {
			generator.cancel();
		}
		return generator.get();
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(aSecondOfPrimes());
	}
}