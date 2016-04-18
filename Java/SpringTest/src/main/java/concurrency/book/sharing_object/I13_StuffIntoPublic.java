package concurrency.book.sharing_object;

/**
 * StuffIntoPublic
 * 
 * Unsafe publication
 *
 * Because of visibility problems, the Holder could appear to another thread to
 * be in an inconsistent state, even though its invariants were properly
 * established by its constructor! This improper publication could allow another
 * thread to observe a partially constructed object.
 */
public class I13_StuffIntoPublic {
	public I14_Holder holder;

	public void initialize() {
		holder = new I14_Holder(42);
	}
}