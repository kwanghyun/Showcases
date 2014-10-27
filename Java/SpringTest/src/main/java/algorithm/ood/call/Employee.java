package algorithm.ood.call;

class Employee {
	CallHandler callHandler;
	int rank; // 0- fresher, 1 - technical lead, 2 - product manager
	boolean free;

	Employee(int rank) {
		this.rank = rank;
	}

	void ReceiveCall(Call call) {/* /* ... */ }

	void CallHandled(Call call) { /* ... */
	} // call is complete

	void CannotHandle(Call call) { // escalate call
		call.rank = rank + 1;
		callHandler.dispatchCall(call);
		free = true;
		callHandler.getNextCall(this); // look for waiting call
	}
}

class Fresher extends Employee {
	public Fresher() {
		super(0);
	}
}

class TechLead extends Employee {
	public TechLead() {
		super(1);
	}
}

class ProductManager extends Employee {
	public ProductManager() {
		super(2);
	}
}
