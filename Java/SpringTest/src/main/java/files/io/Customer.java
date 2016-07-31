package files.io;

public class Customer{
	String id;
	int expense;

	public Customer(String id, int expense) {
		super();
		this.id = id;
		this.expense = expense;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", expense=" + expense + "]";
	}

}
