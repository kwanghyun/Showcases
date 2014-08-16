package security;

import java.io.*;
import java.util.*;
import java.security.Principal;
import java.security.PrivilegedAction;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;

/**
 * This SampleLogin application attempts to authenticate a user.
 * 
 * If the user successfully authenticates itself, the user name and number of
 * Credentials is displayed.
 * 
 * @version 1.1, 09/14/99
 */
public class JAAS_TEST {

	/**
	 * Attempt to authenticate the user.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		System.setProperty("java.security.auth.login.config", "jaas.config");

		// use the configured LoginModules for the "helloWorld" entry
		LoginContext lc = null;
		try {
			lc = new LoginContext("MyLoginModule", new MyCallbackHandler());
		} catch (LoginException le) {
			le.printStackTrace();
			System.exit(-1);
		}

		// the user has 3 attempts to authenticate successfully
		int i;
		for (i = 0; i < 3; i++) {
			try {

				// attempt authentication
				lc.login();

				// if we return with no exception, authentication succeeded
				break;

			} catch (AccountExpiredException aee) {

				System.out.println("Your account has expired");
				System.exit(-1);

			} catch (CredentialExpiredException cee) {

				System.out.println("Your credentials have expired.");
				System.exit(-1);

			} catch (FailedLoginException fle) {

				System.out.println("Authentication Failed");
				try {
					Thread.currentThread().sleep(3000);
				} catch (Exception e) {
					// ignore
				}

			} catch (Exception e) {

				System.out.println("Unexpected Exception - unable to continue");
				e.printStackTrace();
				System.exit(-1);
			}
		}

		// did they fail three times?
		if (i == 3) {
			System.out.println("Sorry");
			System.exit(-1);
		}

		// Look at what Principals we have:
		Iterator principalIterator = lc.getSubject().getPrincipals().iterator();
		System.out
				.println("\n\nAuthenticated user has the following Principals:");
		while (principalIterator.hasNext()) {
			Principal p = (Principal) principalIterator.next();
			System.out.println("\t" + p.toString());
		}

		// Look at some Principal-based work:
		Subject.doAsPrivileged(lc.getSubject(), new PrivilegedAction() {
			public Object run() {
				System.out.println("\nYour java.home property: "
						+ System.getProperty("java.home"));

				System.out.println("\nYour user.home property: "
						+ System.getProperty("user.home"));

				File f = new File("foo.txt");
				System.out.print("\nfoo.txt does ");
				if (!f.exists())
					System.out.print("not ");
				System.out.println("exist in your current directory");

				System.out.println("\nOh, by the way ...");

				try {
					Thread.currentThread().sleep(2000);
				} catch (Exception e) {
					// ignore
				}
				System.out.println("\n\nHello World!\n");
				return null;
			}
		}, null);
		System.exit(0);
	}
}

/**
 * The application must implement the CallbackHandler.
 * 
 * This application is text-based. Therefore it displays information to the user
 * using the OutputStreams System.out and System.err, and gathers input from the
 * user using the InputStream, System.in.
 */
class MyCallbackHandler implements CallbackHandler {

	/**
	 * Invoke an array of Callbacks.
	 * 
	 * 
	 * @param callbacks
	 *            an array of Callback objects which contain the information
	 *            requested by an underlying security service to be retrieved or
	 *            displayed.
	 * 
	 * @exception java.io.IOException
	 *                if an input or output error occurs.
	 * 
	 * @exception UnsupportedCallbackException
	 *                if the implementation of this method does not support one
	 *                or more of the Callbacks specified in the callbacks
	 *                parameter.
	 */
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {

		for (int i = 0; i < callbacks.length; i++) {
			if (callbacks[i] instanceof TextOutputCallback) {

				// display the message according to the specified type
				TextOutputCallback toc = (TextOutputCallback) callbacks[i];
				switch (toc.getMessageType()) {
				case TextOutputCallback.INFORMATION:
					System.out.println(toc.getMessage());
					break;
				case TextOutputCallback.ERROR:
					System.out.println("ERROR: " + toc.getMessage());
					break;
				case TextOutputCallback.WARNING:
					System.out.println("WARNING: " + toc.getMessage());
					break;
				default:
					throw new IOException("Unsupported message type: "
							+ toc.getMessageType());
				}

			} else if (callbacks[i] instanceof NameCallback) {

				// prompt the user for a user name
				NameCallback nc = (NameCallback) callbacks[i];

				// ignore the provided defaultName
				System.err.print(nc.getPrompt());
				System.err.flush();
				nc.setName((new BufferedReader(new InputStreamReader(System.in)))
						.readLine());

			} else if (callbacks[i] instanceof PasswordCallback) {

				// prompt the user for sensitive information
				PasswordCallback pc = (PasswordCallback) callbacks[i];
				System.err.print(pc.getPrompt());
				System.err.flush();
				pc.setPassword(readPassword(System.in));

			} else {
				throw new UnsupportedCallbackException(callbacks[i],
						"Unrecognized Callback");
			}
		}
	}

	// Reads user password from given input stream.
	private char[] readPassword(InputStream in) throws IOException {

		char[] lineBuffer;
		char[] buf;
		int i;

		buf = lineBuffer = new char[128];

		int room = buf.length;
		int offset = 0;
		int c;

		loop: while (true) {
			switch (c = in.read()) {
			case -1:
			case '\n':
				break loop;

			case '\r':
				int c2 = in.read();
				if ((c2 != '\n') && (c2 != -1)) {
					if (!(in instanceof PushbackInputStream)) {
						in = new PushbackInputStream(in);
					}
					((PushbackInputStream) in).unread(c2);
				} else
					break loop;

			default:
				if (--room < 0) {
					buf = new char[offset + 128];
					room = buf.length - offset - 1;
					System.arraycopy(lineBuffer, 0, buf, 0, offset);
					Arrays.fill(lineBuffer, ' ');
					lineBuffer = buf;
				}
				buf[offset++] = (char) c;
				break;
			}
		}

		if (offset == 0) {
			return null;
		}

		char[] ret = new char[offset];
		System.arraycopy(buf, 0, ret, 0, offset);
		Arrays.fill(buf, ' ');

		return ret;
	}
}
