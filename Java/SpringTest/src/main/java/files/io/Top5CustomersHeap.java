package files.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*There is 3 text file, like file1.txt, file2.txt etc. 
 * Every file contains customer id, product id and expenses. 
 * Now write java code which will return 5 persons who spent most 
 * in these three files. Customer id can be duplicate all over files.
 * 
 * */
public class Top5CustomersHeap {

	class CustomerInfoMapTask implements Callable<HashMap<String, Double>> {
		File file;

		public CustomerInfoMapTask(File file) {
			this.file = file;
		}

		@Override
		public HashMap<String, Double> call() throws Exception {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			HashMap<String, Double> cMap = new HashMap<String, Double>();

			String line;
			while ((line = reader.readLine()) != null) {
				String[] strArr = line.split(",");
				String cutomer_id = strArr[0];
				double expense = Double.parseDouble(strArr[2]);
				if (cMap.containsKey(cutomer_id)) {
					cMap.put(cutomer_id, cMap.get(cutomer_id) + expense);
				} else {
					cMap.put(cutomer_id, expense);
				}
			}
			return cMap;
		}
	}

	public Queue<HeapNode> getTop5Customers() throws InterruptedException, ExecutionException {
		// String currDir = System.getProperty("user.dir");

		ExecutorService workers = Executors.newFixedThreadPool(3);
		ArrayList<Future<HashMap<String, Double>>> results = new ArrayList<Future<HashMap<String, Double>>>();
		HashMap<String, Double> aggregator = new HashMap<String, Double>();

		Queue<HeapNode> top5 = new PriorityQueue<>();

		for (int i = 1; i <= 3; i++) {
			File file = new File("data" + i + ".csv");
			results.add(workers.submit(new CustomerInfoMapTask(file)));
		}

		workers.shutdown();

		for (Future<HashMap<String, Double>> result : results) {
			aggregator.putAll(result.get());
		}

		for (Entry<String, Double> entry : aggregator.entrySet()) {
			top5.offer(new HeapNode(entry.getKey(), entry.getValue()));
			if (top5.size() > 5) {
				HeapNode n = top5.poll();
			}

		}

		return top5;
	}

	class HeapNode implements Comparable<HeapNode> {
		String customerId;
		double expense;

		public HeapNode(String id, double expense) {
			this.customerId = id;
			this.expense = expense;
		}

		@Override
		public int compareTo(HeapNode n) {
			return (int) (this.expense - n.expense);
		}

		@Override
		public String toString() {
			return "[id = " + customerId + ", expense = " + expense + "] ";
		}

	}

	public static void main(String[] args) {
		Top5CustomersHeap obj = new Top5CustomersHeap();
		try {
			long start = System.currentTimeMillis();
			System.out.println(obj.getTop5Customers());
			long end = System.currentTimeMillis();
			System.out.println("Elapsed Time : " + (end - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
