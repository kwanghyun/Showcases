package algorithm.etc;

import java.util.Iterator;

/*
 * Given an Iterator class interface with methods: next() and hasNext(),
 * design and implement a PeekingIterator that support the peek() operation
 * -- it essentially peek() at the element that will be returned by the next
 * call to next().
 * 
 * Here is an example. Assume that the iterator is initialized to the
 * beginning of the list: [1, 2, 3].
 * 
 * Call next() gets you 1, the first element in the list.
 * 
 * Now you call peek() and it returns 2, the next element. Calling next()
 * after that still return 2.
 * 
 * You call next() the final time and it returns 3, the last element.
 * Calling hasNext() after that should return false.
 */

public class PeekableIterator<E> implements Iterator<E> {
	Iterator<E> iterator;
	E nextElement;
	boolean hasPeek = false;

	public PeekableIterator(Iterator<E> iterator) {
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return iterator.hasNext() || hasPeek;
	}

	public E next() {
		if (hasNext()) {
			// Only get next element when there is no nextElement.
			if (!hasPeek) {
				nextElement = iterator.next();
			}
			// When next is called, the current element become stale
			hasPeek = false;
		} else {
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
