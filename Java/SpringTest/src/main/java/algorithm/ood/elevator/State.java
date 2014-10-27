package algorithm.ood.elevator;

public enum State {
	IDLE(0), UP(1), DOWN(2), OPEN_DOOR(3);
	
	private int value;
	private State (int value){
		this.value = value; 
	}
}
