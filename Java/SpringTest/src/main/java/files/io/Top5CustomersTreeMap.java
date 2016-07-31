package files.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
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
public class Top5CustomersTreeMap {

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

	public TreeMap<String, Double> getTop5Customers() throws InterruptedException, ExecutionException {
//		String currDir =  System.getProperty("user.dir");

		ExecutorService workers = Executors.newFixedThreadPool(3);
		ArrayList<Future<HashMap<String, Double>>> results = new ArrayList<Future<HashMap<String, Double>>>();
		HashMap<String, Double> aggregator = new HashMap<String, Double>();
		TreeMap<String, Double> sortedList = new TreeMap<String, Double>(new ValueComparator(aggregator));
		TreeMap<String, Double> top5 = new TreeMap<String, Double>(new ValueComparator(aggregator));
		
		for (int i = 1; i <= 3; i++) {
			File file = new File("data" + i + ".csv");
			results.add(workers.submit(new CustomerInfoMapTask(file)));
		}

		workers.shutdown();

		for (Future<HashMap<String, Double>> result : results) {
			aggregator.putAll(result.get());
		}

		sortedList.putAll(aggregator);
		int idx = 0;
		for (Map.Entry<String, Double> custInfo : sortedList.entrySet()) {
			top5.put(custInfo.getKey(), custInfo.getValue());
			idx++;
			if (idx == 5)
				break;
		}

		return top5;
	}

	class ValueComparator implements Comparator<String> {
		HashMap<String, Double> baseMap;

		public ValueComparator(HashMap<String, Double> map) {
			this.baseMap = map;
		}

		public int compare(String cust_id1, String cust_id2) {
			return (int) (baseMap.get(cust_id2) - baseMap.get(cust_id1));
		}
	}

	public static void main(String[] args) {
		Top5CustomersTreeMap obj = new Top5CustomersTreeMap();
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
