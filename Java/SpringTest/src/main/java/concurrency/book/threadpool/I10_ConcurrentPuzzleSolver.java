package concurrency.book.threadpool;

import java.util.*;
import java.util.concurrent.*;

/**
 * ConcurrentPuzzleSolver
 * <p/>
 * Concurrent version of puzzle solver
 *
 * ConcurrentPuzzleSolver in Listing 8.16 uses an inner SolverTask class that
 * extends Node and implements Runnable. Most of the work is done in run:
 * evaluating the set of possible next positions, pruning positions already
 * searched, evaluating whether success has yet been achieved (by this task or
 * by some other task), and submitting unsearched positions to an Executor.
 * 
 * To avoid infinite loops, the sequential version maintained a Set of
 * previously searched positions; ConcurrentPuzzleSolver uses a
 * ConcurrentHashMap for this purpose. This provides thread safety and avoids
 * the race condition inherent in conditionally updating a shared collection by
 * using putIfAbsent to atomically add a position only if it was not previously
 * known. ConcurrentPuzzleSolver uses the internal work queue of the thread pool
 * instead of the call stack to hold the state of the search.
 * 
 * The concurrent approach also trades one form of limitation for another that
 * might be more suitable to the problem domain. The sequential version performs
 * a depth-first search, so the search is bounded by the available stack size.
 * The concurrent version performs a breadth-first search and is therefore free
 * of the stack size restriction (but can still run out of memory if the set of
 * positions to be searched or already searched exceeds the available memory).
 */
public class I10_ConcurrentPuzzleSolver<P, M> {
	private final I07_Puzzle<P, M> puzzle;
	private final ExecutorService exec;
	private final ConcurrentMap<P, Boolean> seen;
	protected final I11_ValueLatch<I08_PuzzleNode<P, M>> solution = new I11_ValueLatch<I08_PuzzleNode<P, M>>();

	public I10_ConcurrentPuzzleSolver(I07_Puzzle<P, M> puzzle) {
		this.puzzle = puzzle;
		this.exec = initThreadPool();
		this.seen = new ConcurrentHashMap<P, Boolean>();
		if (exec instanceof ThreadPoolExecutor) {
			ThreadPoolExecutor tpe = (ThreadPoolExecutor) exec;
			tpe.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		}
	}

	private ExecutorService initThreadPool() {
		return Executors.newCachedThreadPool();
	}

	public List<M> solve() throws InterruptedException {
		try {
			P p = puzzle.initialPosition();
			exec.execute(newTask(p, null, null));
			// block until solution found
			I08_PuzzleNode<P, M> solnPuzzleNode = solution.getValue();
			return (solnPuzzleNode == null) ? null : solnPuzzleNode.asMoveList();
		} finally {
			exec.shutdown();
		}
	}

	protected Runnable newTask(P p, M m, I08_PuzzleNode<P, M> n) {
		return new SolverTask(p, m, n);
	}

	protected class SolverTask extends I08_PuzzleNode<P, M> implements Runnable {
		SolverTask(P pos, M move, I08_PuzzleNode<P, M> prev) {
			super(pos, move, prev);
		}

		public void run() {
			if (solution.isSet() || seen.putIfAbsent(pos, true) != null)
				return; // already solved or seen this position
			if (puzzle.isGoal(pos))
				solution.setValue(this);
			else
				for (M m : puzzle.legalMoves(pos))
					exec.execute(newTask(puzzle.move(pos, m), m, this));
		}
	}
}