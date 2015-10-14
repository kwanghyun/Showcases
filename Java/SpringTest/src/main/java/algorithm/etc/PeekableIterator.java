package algorithm.etc;

import java.util.Iterator;

public class PeekableIterator<E> implements Iterator<E> {

	Iterator<E> iterator;
	E nextElement;
	boolean hasPeek = false;

	public PeekableIterator(Iterator<E> iterator) {
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public E next() {
		if (hasNext()) {
			// Only get next element when there is no nextElement.
			if (!hasPeek) {
				nextElement = iterator.next();
			}
			// When next is called, the current element become stale
			hasPeek = false;
		}else{
			nextElement = null;
		}
		return nextElement;
	}

	public E peek() {
		if (hasNext()) {
			if (!hasPeek) {
				nextElement = iterator.next();
			}
			// When peek is called, we have the next element
			hasPeek = true;
		} else {
			nextElement = null;
		}
		return nextElement;
	}

	public void remove() {
		iterator.remove();
	}
}
