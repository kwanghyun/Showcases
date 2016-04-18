package concurrency.book.sharing_object;

import scala.collection.immutable.HashSet;

/**
 * Publishing an object
 * 
 * Publishing an object means making it available to code outside of its current
 * scope, such as by storing a reference to it where other code can find it,
 * returning it from a nonprivate method, or passing it to a method in another
 * class
 */

class Secrets {
	public static HashSet<Secret> knownSecrets;

	public void initialize() {
		knownSecrets = new HashSet<Secret>();
	}
}

class Secret {
}