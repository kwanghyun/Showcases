package transaction.util;

/**
 * DBException -A class for defining all database related exceptions. All
 * business exceptions should inherit from DBException. Exceptions can be
 * defined based on database scenarios.
 * 
 * @author Gowtam<gmallipe>
 * @since 1.0
 * @see DBException
 */

@SuppressWarnings("serial")
public class DBException extends Exception {
	/**
	 * a public constructor for DBException
	 * 
	 * @param msg
	 *            exception message.
	 * 
	 */
	public DBException(String msg) {
		super(msg);
	}
	
	public DBException(String msg, Exception e) {
		super(msg, e);
	}
}