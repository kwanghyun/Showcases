package com.cisco.locker.ms.reactive;

import java.util.Observable;
import java.util.Observer;

public final class EscapingThisReference {

	/** A RadioStation is observed by the people listening to it. */
	static final class RadioStation extends Observable {
		// elided
	}

	/**
	 * A listener which waits until this object is fully-formed before it lets
	 * it be referenced by the outside world. Uses a private constructor to
	 * first build the object, and then configures the fully-formed object as a
	 * listener.
	 */
	static final class GoodListener implements Observer {
		/** Factory method. */
		static GoodListener buildListener(String aPersonsName, RadioStation aStation) {
			// first call the private constructor
			GoodListener listener = new GoodListener(aPersonsName);
			// the 'listener' object is now fully constructed, and can now be
			// passed safely to the outside world
			aStation.addObserver(listener);
			return listener;
		}

		@Override
		public void update(Observable aStation, Object aData) {
			// ..elided
		}

		private String fPersonsName;

		/** Private constructor. */
		private GoodListener(String aPersonsName) {
			this.fPersonsName = aPersonsName; // ok
		}
	}

	/**
	 * A listener which incorrectly passes a 'this' reference to the outside
	 * world before construction is completed.
	 */
	static final class BadListenerExplicit implements Observer {
		/** Ordinary constructor. */
		BadListenerExplicit(String aPersonsName, RadioStation aStation) {
			this.fPersonsName = aPersonsName; // OK
			// DANGEROUS - the 'this' reference shouldn't be passed to the
			// listener,
			// since the constructor hasn't yet completed; it doesn't matter if
			// this is the last statement in the constructor!
			aStation.addObserver(this);
		}

		@Override
		public void update(Observable aStation, Object aData) {
			// ..elided
		}

		private String fPersonsName;
	}

	/**
	 * Another listener that passes out a 'this' reference to the outside world
	 * before construction is completed; here, the 'this' reference is implicit,
	 * via the anonymous inner class.
	 */
	static final class BadListenerImplicit {
		/** Ordinary constructor. */
		BadListenerImplicit(String aPersonsName, RadioStation aStation) {
			this.fPersonsName = aPersonsName; // OK
			// DANGEROUS
			aStation.addObserver(new Observer() {
				@Override
				public void update(Observable aObservable, Object aData) {
					doSomethingUseful();
				}
			});
		}

		private void doSomethingUseful() {
			// ..elided
		}

		private String fPersonsName;
	}
}