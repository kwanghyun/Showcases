package concurrency.weakref;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WeakReferenceStack<E> {

	private final List<WeakReference<E>> stackReferences;
	private int stackPointer = 0;

	public WeakReferenceStack() {
		this.stackReferences = new ArrayList<>();
	}

	public void push(E element) {
		this.stackReferences.add(stackPointer, new WeakReference<>(element));
		stackPointer++;
	}

	/*
	 * when it is popped, the WeakReference is retrieved, and get is called to
	 * get that object. Now, when any client code has no more pointers to that
	 * object, it will be eligible for removal in the next garbage collection.
	 */
	public E pop() {
		stackPointer--;
		return this.stackReferences.get(stackPointer).get();
	}

	public E peek() {
		return this.stackReferences.get(stackPointer - 1).get();
	}
}