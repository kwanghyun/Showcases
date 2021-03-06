package concurrency.book.threadpool;

import java.util.concurrent.atomic.*;

/**
 * PuzzleSolver
 * <p/>
 * Solver that recognizes when no solution exists
 *
 * ConcurrentPuzzleSolver does not deal well with the case where there is no
 * solution: if all possible moves and positions have been evaluated and no
 * solution has been found, solve waits forever in the call to getSolution. The
 * sequential version terminated when it had exhausted the search space, but
 * getting concurrent programs to terminate can sometimes be more difficult. One
 * possible solution is to keep a count of active solver tasks and set the
 * solution to null when the count drops to zero
 */
public class I12_PuzzleSolver<P, M> extends I10_ConcurrentPuzzleSolver<P, M> {

	I12_PuzzleSolver(I07_Puzzle<P, M> puzzle) {
		super(puzzle);
	}

	private final AtomicInteger taskCount = new AtomicInteger(0);

	protected Runnable newTask(P p, M m, I08_PuzzleNode<P, M> n) {
		return new CountingSolverTask(p, m, n);
	}

	class CountingSolverTask extends SolverTask {
		CountingSolverTask(P pos, M move, I08_PuzzleNode<P, M> prev) {
			super(pos, move, prev);
			taskCount.incrementAndGet();
		}

		public void run() {
			try {
				super.run();
			} finally {
				if (taskCount.decrementAndGet() == 0)
					solution.setValue(null);
			}
		}
	}
}