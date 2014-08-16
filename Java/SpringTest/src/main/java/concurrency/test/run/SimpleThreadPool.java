package concurrency.test.run;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool {

	public static void main(String[] args) {

		String command;


		if (args.length != 3) {
			System.out.println("arguments args.length = " + args.length);
			System.out.println("Invalid arguments setting");
			System.exit(0);
		}
		System.out.println("[SimpleThreadPool]::No. of Threads = " + args[0]);
		System.out.println("[SimpleThreadPool]::Iteration = " + args[1]);
		System.out.println("[SimpleThreadPool]::printMode = " + args[2]);
		SimpleThreadPool pool = new SimpleThreadPool();
		pool.executeJobs(Integer.parseInt(args[1]), Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]));

		while (true) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));
				System.out.print("Enter command");
				command = br.readLine();
				if (command.equals("exit")) {
					System.exit(0);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// }
	}

	private void executeJobs(int inumOfThread, int iteration, boolean printMode) {
		long startTime = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i = 0; i < inumOfThread; i++) {
			Runnable worker = new WorkerThread("[Worker-" + i + "]", iteration,
					printMode);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");
		long endTime = System.currentTimeMillis();
		System.out
				.println(("[SimpleThreadPool]::Took " + (endTime - startTime) / 1000000000)
						+ "seconds");
	}

}