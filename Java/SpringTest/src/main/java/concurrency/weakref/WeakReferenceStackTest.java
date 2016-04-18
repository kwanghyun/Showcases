package concurrency.weakref;

import static org.junit.Assert.*;

import org.junit.Test;

public class WeakReferenceStackTest {

	/*
	 * The test inspects the stack with the peek method, confirming that the
	 * value is on the stack as expected. The peekedValue reference is then set
	 * to null. There are no references to this value, other than inside the
	 * WeakReference in the stack, so at the next garbage collection, the memory
	 * should be reclaimed. After instructing the JVM to perform a garbage
	 * collection, that reference is no longer available in the stack. You
	 * should also see a line printed to the standard out saying that the
	 * finalize method has been called.
	 */	
	@Test
	public void test() {
		
		final WeakReferenceStack<ValueContainer> stack = new WeakReferenceStack<>();

		final ValueContainer expected = new ValueContainer("Value for the stack");
		stack.push(new ValueContainer("Value for the stack"));

		ValueContainer peekedValue = stack.peek();
		assertEquals(expected, peekedValue);
		assertEquals(expected, stack.peek());
		
		peekedValue = null;
		System.gc();
		assertNull(stack.peek());
	}

	public class ValueContainer {
		private final String value;

		public ValueContainer(final String value) {
			this.value = value;
		}

		@Override
		protected void finalize() throws Throwable {
			super.finalize();
			System.out.printf("Finalizing for [%s]%n", toString());
		}

		/* equals, hashCode and toString omitted */
	}
}
