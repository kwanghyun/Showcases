package concurrency.book.sharing_object;

/**
 * SafeListener
 * <p/>
 * Using a factory method to prevent the this reference from escaping during
 * construction
 *
 * If you are tempted to register an event listener or start a thread from a
 * constructor, you can avoid the improper construction by using a private
 * constructor and a public factory method
 */
public class I07_SafeListener {
	private final EventListener listener;

	private I07_SafeListener() {
		listener = new EventListener() {
			public void onEvent(Event e) {
				doSomething(e);
			}
		};
	}

	public static I07_SafeListener newInstance(EventSource source) {
		I07_SafeListener safe = new I07_SafeListener();
		source.registerListener(safe.listener);
		return safe;
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
