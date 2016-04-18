package concurrency.book.threadpool;

import java.util.*;

/**
 * Puzzle
 * <p/>
 * Abstraction for puzzles like the 'sliding blocks puzzle'
 *
 * An appealing application of this technique is solving puzzles that involve
 * finding a sequence of transformations from some initial state to reach a goal
 * state, such as the familiar “sliding block puzzles”,[7] “Hi-Q”, “Instant
 * Insanity”, and other solitaire puzzles. [7] See
 * http://www.puzzleworld.org/SlidingBlockPuzzles.
 * 
 * We define a “puzzle” as a combination of an initial position, a goal
 * position, and a set of rules that determine valid moves. The rule set has two
 * parts: computing the list of legal moves from a given position and computing
 * the result of applying a move to a position. Puzzle in Listing 8.13 shows our
 * puzzle abstraction; the type parameters P and M represent the classes for a
 * position and a move. From this interface, we can write a simple sequential
 * solver that searches the puzzle space until a solution is found or the puzzle
 * space is exhausted.
 */
public interface I07_Puzzle<P, M> {
	P initialPosition();

	boolean isGoal(P position);

	Set<M> legalMoves(P position);

	P move(P position, M move);
}