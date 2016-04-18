package concurrency.book.threadpool;

import java.util.*;

/**
 * SequentialPuzzleSolver
 * <p/>
 * Sequential puzzle solver
 *
 * @author Brian Goetz and Tim Peierls
 */

public class I09_SequentialPuzzleSolver<P, M> {
	private final I07_Puzzle<P, M> puzzle;
	private final Set<P> seen = new HashSet<P>();

	public I09_SequentialPuzzleSolver(I07_Puzzle<P, M> puzzle) {
		this.puzzle = puzzle;
	}

	public List<M> solve() {
		P pos = puzzle.initialPosition();
		return search(new I08_PuzzleNode<P, M>(pos, null, null));
	}

	private List<M> search(I08_PuzzleNode<P, M> node) {
		if (!seen.contains(node.pos)) {
			seen.add(node.pos);
			if (puzzle.isGoal(node.pos))
				return node.asMoveList();
			for (M move : puzzle.legalMoves(node.pos)) {
				P pos = puzzle.move(node.pos, move);
				I08_PuzzleNode<P, M> child = new I08_PuzzleNode<P, M>(pos, move, node);
				List<M> result = search(child);
				if (result != null)
					return result;
			}
		}
		return null;
	}
}