package algorithm.ood.elevator;

import java.util.Stack;

public class PersonElevator extends Elevator {

	/*
	 * Stack is LIFO. When weight is over the maximum of elevator can take, the
	 * last person need to get out
	 */
	Stack<Person> pessangers = new Stack<Person>();

	public boolean addPessanger(Person person) {
		if (this.getCurrentWeight() + person.getWeight() <= this.getMaxWeight()) {
			pessangers.add(person);
		} else {
			try {
				// Might use Exception but Checked exception break OCP, so not using it.
				// doBeep()..
				// TODO wait and notify
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public int checkCurrentWeight(){
		//heartbeat to check current weight or wait to data pushed.
		return 0;
	}

	public Stack<Person> getPessangers() {
		return pessangers;
	}

	public void setPessangers(Stack<Person> pessangers) {
		this.pessangers = pessangers;
	}

}
