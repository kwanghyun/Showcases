package concurrency.book.liveness_harzards;

import java.util.*;

import concurrency.book.composing_objects.Point;
import net.jcip.annotations.*;

/**
 * CooperatingDeadlock
 * <p/>
 * Lock-ordering deadlock between cooperating objects
 *
 * Of course, Taxi and Dispatcher didn't know that they were each half of a
 * deadlock waiting to happen. And they shouldn't have to; a method call is an
 * abstraction barrier intended to shield you from the details of what happens
 * on the other side. But because you don't know what is happening on the other
 * side of the call, calling an alien method with a lock held is difficult to
 * analyze and therefore risky.
 * 
 * Calling a method with no locks held is called an open call, and classes that
 * rely on open calls are more well-behaved and composable than classes that
 * make calls with locks held. Using open calls to avoid deadlock is analogous
 * to using encapsulation to provide thread safety: while one can certainly
 * construct a thread-safe program without any encapsulation, the thread safety
 * analysis of a program that makes effective use of encapsulation is far easier
 * than that of one that does not. Similarly, the liveness analysis of a program
 * that relies exclusively on open calls is far easier than that of one that
 * does not. Restricting yourself to open calls makes it far easier to identify
 * the code paths that acquire multiple locks and therefore to ensure that locks
 * are acquired in a consistent order.[3]
 */
public class I05_CooperatingDeadlock {
	// Warning: deadlock-prone!
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
			this.location = location;
			if (location.equals(destination))
				dispatcher.notifyAvailable(this);
		}

		public synchronized Point getDestination() {
			return destination;
		}

		public synchronized void setDestination(Point destination) {
			this.destination = destination;
		}
	}

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

		public synchronized Image getImage() {
			Image image = new Image();
			for (Taxi t : taxis)
				image.drawMarker(t.getLocation());
			return image;
		}
	}

	class Image {
		public void drawMarker(Point p) {
		}
	}
}