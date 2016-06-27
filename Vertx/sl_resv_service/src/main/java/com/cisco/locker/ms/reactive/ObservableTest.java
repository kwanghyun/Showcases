package com.cisco.locker.ms.reactive;

import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

import rx.Observable;
import rx.subscriptions.Subscriptions;

public class ObservableTest<T> {

	static Observable<Path> listFolder(Path dir, String glob) { // (1)
		return Observable.<Path> create(subscriber -> {
			System.out.println("lsitFolder() start");
			try {
				// java.nio.file.Files.newDirectoryStream(Path dir, String glob)
				// Opens a directory, returning a DirectoryStream to iterate
				// over the entries in the directory. The elements returned by
				// the directory stream's iterator are of type Path, each one
				// representing an entry in the directory. The Path objects are
				// obtained as if by resolving the name of the directory entry
				// against dir. The entries returned by the iterator are
				// filtered by matching the String representation of their file
				// names against the given globbing pattern.
				// glob : the glob pattern
				DirectoryStream<Path> stream = Files.newDirectoryStream(dir, glob);
				
				// Subscriber.add() operator to add a new Subscription instance
				// to the subscriber, created using the Subscriptions.create()
				// operator. This method creates a Subscription instance using
				// an action. This action will be executed when the Subscription
				// instance is unsubscribed, which means when the Subscriber
				// instance is unsubscribed in this case.So this is similar to
				// putting the closing of the stream in the final block.
				subscriber.add(Subscriptions.create(() -> {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}));
				Observable.<Path> from(stream).subscribe(subscriber);
			} catch (DirectoryIteratorException ex) {
				subscriber.onError(ex);
			} catch (IOException ioe) {
				subscriber.onError(ioe);
			}
		});
	}

	static Observable<String> from(final Path path) { // (2)
		return Observable.<String> create(subscriber -> {
			try {
				System.out.println("from() start");
				// Opens a file for reading, returning a BufferedReader to read
				// text from the file in an efficient manner. 
				BufferedReader reader = Files.newBufferedReader(path);
				subscriber.add(Subscriptions.create(() -> {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}));
				String line = null;
				while ((line = reader.readLine()) != null && !subscriber.isUnsubscribed()) {
					subscriber.onNext(line);
				}
				if (!subscriber.isUnsubscribed()) {
					subscriber.onCompleted();
				}
			} catch (IOException ioe) {
				if (!subscriber.isUnsubscribed()) {
					subscriber.onError(ioe);
				}
			}
		});
	}

	static void subscribePrint(Observable<String> observable, String name) {
		observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	public static void main(String[] args) {
		// Path java.nio.file.Paths.get(String first, String... more)
		// Converts a path string, or a sequence of strings that when joined
		// form a path string, to a Path. If more does not specify any elements
		// then the value of the first parameter is the path string to convert.
		Observable<String> fsObs = listFolder(Paths.get("src", "main", "resources"), "{ACMQ.txt,log4j.properties}")														
				.flatMap(path -> from(path)); // (3)

		subscribePrint(fsObs, "FS"); // (4)
		
		// (3) The example using flatMap creates an Observable instance from a
		// folder, using the listFolder() operator, which emits two Path
		// parameters to files. Using the flatMap() operator for every file, we
		// create an Observable instance, using the from(Path) operator, which
		// emits the file content as lines.
		// (4) The result of the preceding chain will be the two file contents,
		// printed on the standard output. If we used the Scheduler instances
	}
}
