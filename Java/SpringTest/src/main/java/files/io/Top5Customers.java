package files.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
 * TreeMap insert => Olog(n), find => O(1)
 * HashMap insert => O(1), find => O(1)
 * */
public class Top5Customers {

	public HashMap<String, Double> getCustomerMap(File file) throws IOException {
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

	public Callable<HashMap<String, Double>> processTask(final File file) {

		return new Callable<HashMap<String, Double>>() {
			public HashMap<String, Double> call() throws Exception {
				return getCustomerMap(file);
			}
		};
	}

	public HashMap<String, Double> getTop5Customers() throws InterruptedException,
			ExecutionException {

		ExecutorService workers = Executors.newFixedThreadPool(3);
		ArrayList<Future<HashMap<String, Double>>> results = new ArrayList<Future<HashMap<String, Double>>>();
		HashMap<String, Double> aggregator = new HashMap<String, Double>();
		HashMap<String, Double> top5 = new HashMap<String, Double>(5);

		for (int i = 1; i <= 3; i++) {
			File file = new File("C:/TestFiles/cust" + i + ".txt");
			results.add(workers.submit(processTask(file)));
		}

		workers.shutdown();

		for (Future<HashMap<String, Double>> result : results) {
			aggregator.putAll(result.get());
		}


		for (int i = 0; i < 5; i++) {
			double maxExp = 0;
			String maxCust_id = "";
			
			for (Map.Entry<String, Double> custInfo : aggregator.entrySet()) {
				String cust_id = custInfo.getKey();
				double exp = custInfo.getValue();
				if(maxExp < exp){
					maxExp = exp;
					maxCust_id = cust_id;
				}
			}
			top5.put(maxCust_id, maxExp);
			aggregator.remove(maxCust_id);
		}
		return top5;
	}
	
	public static void main(String[] args) {
		Top5Customers obj = new Top5Customers();
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
