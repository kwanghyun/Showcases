package algorithm.graph;

import java.util.HashMap;
import java.util.Map;

/*
 * Given a list of tickets, find itinerary in order using the given list.
 * 
 * Example:
 * Input :
	"Chennai" -> "Banglore"
	"Bombay" -> "Delhi"
	"Goa"    -> "Chennai"
	"Delhi"  -> "Goa"
 * 
 * Output: Bombay->Delhi, Delhi->Goa, Goa->Chennai, Chennai->Banglore,
 */
public class TravelTickets {
	/*
	 * One Solution is to build a graph and do Topological Sorting of the graph.
	 * Time complexity of this solution is O(n).
	 */
	//TODO Implement with TopologySort
	/*
	 * 1) Create a HashMap of given pair of tickets. Let the created HashMap be
	 * 'dataset'. Every entry of 'dataset' is of the form "from->to" like
	 * "Chennai" -> "Banglore"
	 * 
	 * 2) Find the starting point of itinerary. a) Create a reverse HashMap. Let
	 * the reverse be 'reverseMap' Entries of 'reverseMap' are of the form
	 * "to->form". Following is 'reverseMap' for above example. "Banglore"->
	 * "Chennai" "Delhi" -> "Bombay" "Chennai" -> "Goa" "Goa" -> "Delhi"
	 * 
	 * b) Traverse 'dataset'. For every key of dataset, check if it is there in
	 * 'reverseMap'. If a key is not present, then we found the starting point.
	 * In the above example, "Bombay" is starting point.
	 * 
	 * 3) Start from above found starting point and traverse the 'dataset' to
	 * print itinerary.
	 */

	// This function populates 'result' for given input 'dataset'
	private static void printResult(Map<String, String> dataSet) {
		// To store reverse of given map
		Map<String, String> reverseMap = new HashMap<String, String>();

		// To fill reverse map, iterate through the given map
		for (Map.Entry<String, String> entry : dataSet.entrySet())
			reverseMap.put(entry.getValue(), entry.getKey());

		// Find the starting point of itinerary
		String start = null;
		for (Map.Entry<String, String> entry : dataSet.entrySet()) {
			if (!reverseMap.containsKey(entry.getKey())) {
				start = entry.getKey();
				break;
			}
		}

		// If we could not find a starting point, then something wrong
		// with input
		if (start == null) {
			System.out.println("Invalid Input");
			return;
		}

		// Once we have starting point, we simple need to go next, next
		// of next using given hash map
		String to = dataSet.get(start);
		while (to != null) {
			System.out.print(start + "->" + to + ", ");
			start = to;
			to = dataSet.get(to);
		}
	}

	public static void main(String[] args) {
		Map<String, String> dataSet = new HashMap<String, String>();
		dataSet.put("Chennai", "Banglore");
		dataSet.put("Bombay", "Delhi");
		dataSet.put("Goa", "Chennai");
		dataSet.put("Delhi", "Goa");

		printResult(dataSet);
	}

}
