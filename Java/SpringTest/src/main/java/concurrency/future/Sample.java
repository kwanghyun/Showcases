package concurrency.future;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Sample {
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		int noOfTasks = 5;
		int noOfThread = 5;
		int noOfComputation = 5;
		HashMap<String, Integer> result = new HashMap<String, Integer>(); 
		
		ExecutorService workers = Executors.newFixedThreadPool(noOfThread);
		ArrayList<Future<HashMap<String, Integer>>> completedTasks = 
				new ArrayList<Future<HashMap<String, Integer>>>();

		for (int i = 0; i < noOfTasks; i++) {
			completedTasks.add(workers.submit(new Task(i * noOfTasks, i
					* noOfTasks + noOfComputation)));
		}

		for (Future<HashMap<String, Integer>> completedTask : completedTasks) {
			HashMap<String, Integer> tempMap = completedTask.get();
			System.out.println("completedTask => " + tempMap);
			result.putAll(tempMap);		
		}
		System.out.println("result => " + result);
		workers.shutdown();
	}
}

class Task implements Callable<HashMap<String, Integer>> {
	int start = 0;
	int end = 0;

	public Task(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public HashMap<String, Integer> call() throws Exception {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(int i = start; i<end; i++){
			map.put("key-" + i, i);
		}
		return map;
	}
}
