package concurrency.book.threadpool;

import java.util.*;

import net.jcip.annotations.*;

/**
 * PuzzleNode
 * <p/>
 * Link node for the puzzle solving framework
 *
 * SequentialPuzzleSolver in Listing 8.15 shows a sequential solver for the
 * puzzle framework that performs a depth-first search of the puzzle space. It
 * terminates when it finds a solution (which is not necessarily the shortest
 * solution).
 * 
 * Rewriting the solver to exploit concurrency would allow us to compute next
 * moves and evaluate the goal condition in parallel, since the process of
 * evaluating one move is mostly independent of evaluating other moves. (We say
 * “mostly” because tasks share some mutable state, such as the set of seen
 * positions.) If multiple processors are available, this could reduce the time
 * it takes to find a solution.
 */
@Immutable
public class I08_PuzzleNode<P, M> {
	final P pos;
	final M move;
	final I08_PuzzleNode<P, M> prev;

	public I08_PuzzleNode(P pos, M move, I08_PuzzleNode<P, M> prev) {
		this.pos = pos;
		this.move = move;
		this.prev = prev;
	}

	List<M> asMoveList() {
		List<M> solution = new LinkedList<M>();
		for (I08_PuzzleNode<P, M> n = this; n.move != null; n = n.prev)
			solution.add(0, n.move);
		return solution;
	}
}