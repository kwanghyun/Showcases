package concurrency.book.sharing_object;

/**
 * Holder
 * 
 * Class at risk of failure if not properly published
 *
 * Because synchronization was not used to make the Holder visible to other
 * threads, we say the Holder was not properly published. Two things can go
 * wrong with improperly published objects. Other threads could see a stale
 * value for the holder field, and thus see a null reference or other older
 * value even though a value has been placed in holder. But far worse, other
 * threads could see an up-todate value for the holder reference, but stale
 * values for the state of the Holder.
 */
public class I14_Holder {
	private int n;

	public I14_Holder(int n) {
		this.n = n;
	}

	public void assertSanity() {
		if (n != n)
			throw new AssertionError("This statement is false.");
	}
}