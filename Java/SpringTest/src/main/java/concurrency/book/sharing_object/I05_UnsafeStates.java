package concurrency.book.sharing_object;

/**
 * UnsafeStates
 * 
 * Allowing internal mutable state to escape
 * 
 * Publishing states in this way is problematic because any caller can modify
 * its contents. In this case, the states array has escaped its intended scope,
 * because what was supposed to be private state has been effectively made
 * public.
 */
class I05_UnsafeStates {
	private String[] states = new String[] { "AK", "AL" /* ... */
	};

	public String[] getStates() {
		return states;
	}
}
