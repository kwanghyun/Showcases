package concurrency.book.building_blocks;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;

/**
 * ProducerConsumer
 * <p/>
 * Producer and consumer tasks in a desktop search application
 * 
 * One type of program that is amenable to decomposition into producers and
 * consumers is an agent that scans local drives for documents and indexes them
 * for later searching, similar to Google Desktop or the Windows Indexing
 * service. DiskCrawler in Listing 5.8 shows a producer task that searches a
 * file hierarchy for files meeting an indexing criterion and puts their names
 * on the work queue; Indexer in Listing 5.8 shows the consumer task that takes
 * file names from the queue and indexes them.
 * 
 * The producer-consumer pattern offers a thread-friendly means of decomposing
 * the desktop search problem into simpler components. Factoring file-crawling
 * and indexing into separate activities results in code that is more readable
 * and reusable than with a monolithic activity that does both; each of the
 * activities has only a single task to do, and the blocking queue handles all
 * the flow control, so the code for each is simpler and clearer.
 * 
 * The producer-consumer pattern also enables several performance benefits.
 * Producers and consumers can execute concurrently; if one is I/O-bound and the
 * other is CPU-bound, executing them concurrently yields better overall
 * throughput than executing them sequentially. If the producer and consumer
 * activities are parallelizable to different degrees, tightly coupling them
 * reduces parallelizability to that of the less parallelizable activity.
 * 
 * Listing 5.9 starts several crawlers and indexers, each in their own thread.
 * As written, the consumer threads never exit, which prevents the program from
 * terminating; we examine several techniques for addressing this problem in
 * Chapter 7. While this example uses explicitly managed threads, many
 * producer-consumer designs can be expressed using the Executor task execution
 * framework, which itself uses the producer-consumer pattern.
 * 
 */
public class I04_ProducerConsumer {
	static class FileCrawler implements Runnable {
		private final BlockingQueue<File> fileQueue;
		private final FileFilter fileFilter;
		private final File root;

		public FileCrawler(BlockingQueue<File> fileQueue, final FileFilter fileFilter, File root) {
			this.fileQueue = fileQueue;
			this.root = root;
			this.fileFilter = new FileFilter() {
				public boolean accept(File f) {
					return f.isDirectory() || fileFilter.accept(f);
				}
			};
		}

		private boolean alreadyIndexed(File f) {
			return false;
		}

		public void run() {
			try {
				crawl(root);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		private void crawl(File root) throws InterruptedException {
			File[] entries = root.listFiles(fileFilter);
			if (entries != null) {
				for (File entry : entries)
					if (entry.isDirectory())
						crawl(entry);
					else if (!alreadyIndexed(entry))
						fileQueue.put(entry);
			}
		}
	}

	static class Indexer implements Runnable {
		private final BlockingQueue<File> queue;

		public Indexer(BlockingQueue<File> queue) {
			this.queue = queue;
		}

		public void run() {
			try {
				while (true)
					indexFile(queue.take());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		public void indexFile(File file) {
			// Index the file...
			System.out.println("INDEXING FILE....." + file.getAbsolutePath() + "/" + file.getName());
		};
	}

	private static final int BOUND = 10;
	private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

	public static void startIndexing(File[] roots) {
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return true;
			}
		};

		for (File root : roots)
			new Thread(new FileCrawler(queue, filter, root)).start();

		for (int i = 0; i < N_CONSUMERS; i++)
			new Thread(new Indexer(queue)).start();
	}

	public static void main(String[] args) {
		File[] files = { new File("/Users/jangkwanghyun/Downloads") };
		startIndexing(files);
	}
}