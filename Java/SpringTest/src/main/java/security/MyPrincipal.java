package security;

import java.security.Principal;

@SuppressWarnings("serial")
public class MyPrincipal implements Principal, java.io.Serializable {

	private String name;

	/*
	 * Create a HWPrincipal with the supplied name.
	 */
	public MyPrincipal(String name) {
		if (name == null)
			throw new NullPointerException("illegal null input");

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return ("[MyPrincipal]:  " + name);
	}

	/*
	 * Compares the specified Object with the MyPrincipal for equality. Returns
	 * true if the given object is also a MyPrincipal and the two MyPrincipals
	 * have the same user name.
	 */
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (this == o)
			return true;

		if (!(o instanceof MyPrincipal))
			return false;
		MyPrincipal object = (MyPrincipal) o;

		if (this.getName().equals(object.getName()))
			return true;
		return false;
	}

	/*
	 * Return a hash code for the HWPrincipal.
	 */
	public int hashCode() {
		return name.hashCode();
	}
}