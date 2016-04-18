package concurrency.book.liveness_harzards;

import java.util.*;

import concurrency.book.composing_objects.Point;
import net.jcip.annotations.*;

/**
 * CooperatingNoDeadlock
 * <p/>
 * Using open calls to avoiding deadlock between cooperating objects
 *
 * Taxi and Dispatcher in Listing 10.5 can be easily refactored to use open
 * calls and thus eliminate the deadlock risk. This involves shrinking the
 * synchronized blocks to guard only operations that involve shared state, as in
 * Listing 10.6. Very often, the cause of problems like those in Listing 10.5 is
 * the use of synchronized methods instead of smaller synchronized blocks for
 * reasons of compact syntax or simplicity rather than because the entire method
 * must be guarded by a lock. (As a bonus, shrinking the synchronized block may
 * also improve scalability as well.
 * 
 * Strive to use open calls throughout your program. Programs that rely on open
 * calls are far easier to analyze for deadlock-freedom than those that allow
 * calls to alien methods with locks held.
 * 
 * Restructuring a synchronized block to allow open calls can sometimes have
 * undesirable consequences, since it takes an operation that was atomic and
 * makes it not atomic. In many cases, the loss of atomicity is perfectly
 * acceptable; there's no reason that updating a taxi's location and notifying
 * the dispatcher that it is ready for a new destination need be an atomic
 * operation. In other cases, the loss of atomicity is noticeable but the
 * semantic changes are still acceptable. In the deadlock-prone version,
 * getImage produces a complete snapshot of the fleet locations at that instant;
 * in the refactored version, it fetches the location of each taxi at slightly
 * different times.
 * 
 * In some cases, however, the loss of atomicity is a problem, and here you will
 * have to use another technique to achieve atomicity. One such technique is to
 * structure a concurrent object so that only one thread can execute the code
 * path following the open call. For example, when shutting down a service, you
 * may want to wait for in-progress operations to complete and then release
 * resources used by the service. Holding the service lock while waiting for
 * operations to complete is inherently deadlock-prone, but releasing the
 * service lock before the service is shut down may let other threads start new
 * operations. The solution is to hold the lock long enough to update the
 * service state to “shutting down” so that other threads wanting to start new
 * operations—including shutting down the service—see that the service is
 * unavailable, and do not try. You can then wait for shutdown to complete,
 * knowing that only the shutdown thread has access to the service state after
 * the open call completes. Thus, rather than using locking to keep the other
 * threads out of a critical section of code, this technique relies on
 * constructing protocols so that other threads don't try to get in.
 */
class CooperatingNoDeadlock {
	@ThreadSafe
	class Taxi {
		@GuardedBy("this")
		private Point location, destination;
		private final Dispatcher dispatcher;

		public Taxi(Dispatcher dispatcher) {
			this.dispatcher = dispatcher;
		}

		public synchronized Point getLocation() {
			return location;
		}

		public synchronized void setLocation(Point location) {
			boolean reachedDestination;
			synchronized (this) {
				this.location = location;
				reachedDestination = location.equals(destination);
			}
			if (reachedDestination)
				dispatcher.notifyAvailable(this);
		}

		public synchronized Point getDestination() {
			return destination;
		}

		public synchronized void setDestination(Point destination) {
			this.destination = destination;
		}
	}

	@ThreadSafe
	class Dispatcher {
		@GuardedBy("this")
		private final Set<Taxi> taxis;
		@GuardedBy("this")
		private final Set<Taxi> availableTaxis;

		public Dispatcher() {
			taxis = new HashSet<Taxi>();
			availableTaxis = new HashSet<Taxi>();
		}

		public synchronized void notifyAvailable(Taxi taxi) {
			availableTaxis.add(taxi);
		}

		public Image getImage() {
			Set<Taxi> copy;
			synchronized (this) {
				copy = new HashSet<Taxi>(taxis);
			}
			Image image = new Image();
			for (Taxi t : copy)
				image.drawMarker(t.getLocation());
			return image;
		}
	}

	class Image {
		public void drawMarker(Point p) {
		}
	}

}