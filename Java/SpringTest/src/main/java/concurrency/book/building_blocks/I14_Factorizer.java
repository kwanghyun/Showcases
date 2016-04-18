package concurrency.book.building_blocks;

import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * Factorizer
 * <p/>
 * Factorizing servlet that caches results using Memoizer
 *
 * With our concurrent cache implementation complete, we can now add real
 * caching to the factorizing servlet from Chapter 2, as promised. Factorizer in
 * Listing 5.20 uses Memoizer to cache previously computed values efficiently
 * and scalably.
 */
@ThreadSafe
public class I14_Factorizer extends GenericServlet implements Servlet {
	private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {
		public BigInteger[] compute(BigInteger arg) {
			return factor(arg);
		}
	};
	private final Computable<BigInteger, BigInteger[]> cache = new I13_Memoizer4<BigInteger, BigInteger[]>(c);

	public void service(ServletRequest req, ServletResponse resp) {
		try {
			BigInteger i = extractFromRequest(req);
			encodeIntoResponse(resp, cache.compute(i));
		} catch (InterruptedException e) {
			encodeError(resp, "factorization interrupted");
		}
	}

	void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
	}

	void encodeError(ServletResponse resp, String errorString) {
	}

	BigInteger extractFromRequest(ServletRequest req) {
		return new BigInteger("7");
	}

	BigInteger[] factor(BigInteger i) {
		// Doesn't really factor
		return new BigInteger[] { i };
	}
}