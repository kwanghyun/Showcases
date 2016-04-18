package concurrency.book.sharing_object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionDispenser
 * 
 * Using ThreadLocal to ensure thread confinement
 * 
 * 3.3.3. ThreadLocal
 * 
 * A more formal means of maintaining thread confinement is ThreadLocal, which
 * allows you to associate a per-thread value with a value-holding object.
 * Thread-Local provides get and set access or methods that maintain a separate
 * copy of the value for each thread that uses it, so a get returns the most
 * recent value passed to set from the currently executing thread. Thread-local
 * variables are often used to prevent sharing in designs based on mutable
 * Singletons or global variables. For example, a single-threaded application
 * might maintain a global database connection that is initialized at startup to
 * avoid having to pass a Connection to every method. Since JDBC connections may
 * not be thread-safe, a multithreaded application that uses a global connection
 * without additional coordination is not thread-safe either. By using a
 * ThreadLocal to store the JDBC connection, as in ConnectionHolder in Listing
 * 3.10, each thread will have its own connection.
 */
public class I09_ConnectionDispenser {
	static String DB_URL = "jdbc:mysql://localhost/mydatabase";

	private ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
		public Connection initialValue() {
			try {
				return DriverManager.getConnection(DB_URL);
			} catch (SQLException e) {
				throw new RuntimeException("Unable to acquire Connection, e");
			}
		};
	};

	public Connection getConnection() {
		return connectionHolder.get();
	}
}
