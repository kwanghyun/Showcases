package concurrency.book.sharing_object;

/**
 * ThisEscape
 * 
 * Implicitly allowing the this reference to escape
 *
 * A common mistake that can let the this reference escape during construction
 * is to start a thread from a constructor. When an object creates a thread from
 * its constructor, it almost always shares its this reference with the new
 * thread, either explicitly (by passing it to the constructor) or implicitly
 * (because the Thread or Runnable is an inner class of the owning object). The
 * new thread might then be able to see the owning object before it is fully
 * constructed. There's nothing wrong with creating a thread in a constructor,
 * but it is best not to start the thread immediately. Instead, expose a start
 * or initialize method that starts the owned thread.
 * 
 * Calling an overrideable instance method (one that is neither private nor
 * final) from the constructor can also allow the this reference to escape.
 * 
 */
public class I06_ThisEscape {
	public I06_ThisEscape(EventSource source) {
		source.registerListener(new EventListener() {
			public void onEvent(Event e) {
				doSomething(e);
			}
		});
	}

	void doSomething(Event e) {
	}

	interface EventSource {
		void registerListener(EventListener e);
	}

	interface EventListener {
		void onEvent(Event e);
	}

	interface Event {
	}
}