package algorithm.ood.call;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CallHandler {
	static final int LEVELS = 3; // we have 3 levels of employees
	static final int NUM_FRESHERS = 5; // we have 5 freshers
	ArrayList<Employee>[] employeeLevels = new ArrayList[LEVELS];
	// queues for each call��s rank
	Queue<Call>[] callQueues = new LinkedList[LEVELS];

	public CallHandler() { 
		/* ... */
	}

	Employee getCallHandler(Call call) {
		for (int level = call.rank; level < LEVELS - 1; level++) {
			ArrayList<Employee> employeeLevel = employeeLevels[level];
			for (Employee emp : employeeLevel) {
				if (emp.free) {
					return emp;
				}
			}
		}
		return null;
	}

	// routes the call to an available employee, or adds to a queue

	void dispatchCall(Call call) {
		// try to route the call to an employee with minimal rank
		Employee emp = getCallHandler(call);
		if (emp != null) {
			emp.ReceiveCall(call);
		} else {
			// place the call into queue according to its rank
			callQueues[call.rank].add(call);
		}
	}

	void getNextCall(Employee e) {
		/* ... */
	} // look for call for e��s rank
}
