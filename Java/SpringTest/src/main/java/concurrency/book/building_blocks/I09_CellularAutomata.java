package concurrency.book.building_blocks;

import java.util.concurrent.*;

/**
 * CellularAutomata
 *
 * Coordinating computation in a cellular automaton with CyclicBarrier
 *
 * CyclicBarrier allows a fixed number of parties to rendezvous repeatedly at a
 * barrier point and is useful in parallel iterative algorithms that break down
 * a problem into a fixed number of independent subproblems. Threads call await
 * when they reach the barrier point, and await blocks until all the threads
 * have reached the barrier point. If all threads meet at the barrier point, the
 * barrier has been successfully passed, in which case all threads are released
 * and the barrier is reset so it can be used again. If a call to await times
 * out or a thread blocked in await is interrupted, then the barrier is
 * considered broken and all outstanding calls to await terminate with
 * BrokenBarrierException. If the barrier is successfully passed, await returns
 * a unique arrival index for each thread, which can be used to “elect” a leader
 * that takes some special action in the next iteration. CyclicBarrier also
 * lets you pass a barrier action to the constructor; this is a Runnable that is
 * executed (in one of the subtask threads) when the barrier is successfully
 * passed but before the blocked threads are released.
 */
public class I09_CellularAutomata {
	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;

	public I09_CellularAutomata(Board board) {
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();
		this.barrier = new CyclicBarrier(count, new Runnable() {
			public void run() {
				mainBoard.commitNewValues();
			}
		});
		this.workers = new Worker[count];
		for (int i = 0; i < count; i++)
			workers[i] = new Worker(mainBoard.getSubBoard(count, i));
	}

	private class Worker implements Runnable {
		private final Board board;

		public Worker(Board board) {
			this.board = board;
		}

		public void run() {
			while (!board.hasConverged()) {
				for (int x = 0; x < board.getMaxX(); x++)
					for (int y = 0; y < board.getMaxY(); y++)
						board.setNewValue(x, y, computeValue(x, y));
				try {
					/*
					 * Waits until all parties have invoked await on this
					 * barrier.
					 */
					barrier.await();
				} catch (InterruptedException ex) {
					return;
				} catch (BrokenBarrierException ex) {
					return;
				}
			}
		}

		private int computeValue(int x, int y) {
			// Compute the new value that goes in (x,y)
			return 0;
		}
	}

	public void start() {
		for (int i = 0; i < workers.length; i++)
			new Thread(workers[i]).start();
		mainBoard.waitForConvergence();
	}

	interface Board {
		int getMaxX();

		int getMaxY();

		int getValue(int x, int y);

		int setNewValue(int x, int y, int value);

		void commitNewValues();

		boolean hasConverged();

		void waitForConvergence();

		Board getSubBoard(int numPartitions, int index);
	}
}