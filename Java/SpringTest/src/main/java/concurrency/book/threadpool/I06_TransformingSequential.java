package concurrency.book.threadpool;

import java.util.*;
import java.util.concurrent.*;

/**
 * TransformingSequential
 * <p/>
 * Transforming sequential execution into parallel execution
 *
 * The page rendering examples in Section 6.3 went through a series of
 * refinements in search of exploitable parallelism. The first attempt was
 * entirely sequential; the second used two threads but still performed all the
 * image downloads sequentially; the final version treated each image download
 * as a separate task to achieve greater parallelism. Loops whose bodies contain
 * nontrivial computation or perform potentially blocking I/O are frequently
 * good candidates for parallelization, as long as the iterations are
 * independent.
 * 
 * If we have a loop whose iterations are independent and we don't need to wait
 * for all of them to complete before proceeding, we can use an Executor to
 * transform a sequential loop into a parallel one, as shown in
 * processSequentially and processInParallel in Listing 8.10.
 * 
 * A call to processInParallel returns more quickly than a call to
 * processSequentially because it returns as soon as all the tasks are queued to
 * the Executor, rather than waiting for them all to complete. If you want to
 * submit a set of tasks and wait for them all to complete, you can use
 * ExecutorService.invokeAll; to retrieve the results as they become available,
 * you can use a CompletionService, as in Renderer on page 130.
 */
public abstract class I06_TransformingSequential {

	void processSequentially(List<Element> elements) {
		for (Element e : elements)
			process(e);
	}

	void processInParallel(Executor exec, List<Element> elements) {
		for (final Element e : elements)
			exec.execute(new Runnable() {
				public void run() {
					process(e);
				}
			});
	}

	public abstract void process(Element e);

	/*
	 * Loop parallelization can also be applied to some recursive designs; there
	 * are often sequential loops within the recursive algorithm that can be
	 * parallelized in the same manner as Listing 8.10. The easier case is when
	 * each iteration does not require the results of the recursive iterations
	 * it invokes. For example, sequentialRecursive in Listing 8.11 does a
	 * depth-first traversal of a tree, performing a calculation on each node
	 * and placing the result in a collection. The transformed version,
	 * parallelRecursive, also does a depth-first traversal, but instead of
	 * computing the result as each node is visited, it submits a task to
	 * compute the node result.
	 */
	public <T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
		for (Node<T> n : nodes) {
			results.add(n.compute());
			sequentialRecursive(n.getChildren(), results);
		}
	}

	public <T> void parallelRecursive(final Executor exec, List<Node<T>> nodes, final Collection<T> results) {
		for (final Node<T> n : nodes) {
			exec.execute(new Runnable() {
				public void run() {
					results.add(n.compute());
				}
			});
			parallelRecursive(exec, n.getChildren(), results);
		}
	}

	public <T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();
		parallelRecursive(exec, nodes, resultQueue);
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		return resultQueue;
	}

	interface Element {
	}

	interface Node<T> {
		T compute();

		List<Node<T>> getChildren();
	}
}
