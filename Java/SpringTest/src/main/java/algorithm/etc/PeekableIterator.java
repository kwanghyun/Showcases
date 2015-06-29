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
		hasPeek = false;
		if (hasPeek) {
			return nextElement;
		} else {
			return iterator.next();
		}
	}

	public E peek() {
		if (hasNext()) {
			if (hasPeek == false) {
				nextElement = iterator.next();
				hasPeek = true;
			}
		} else {
			nextElement = null;
		}
		return nextElement;
	}
}
