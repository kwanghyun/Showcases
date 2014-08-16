package concurrency.test.run;

public class Test {
	private int count;
	private boolean start = true;
	
	public Test(int count) {
		if (count < 0)
			throw new IllegalArgumentException(count + " < 0");
		this.count = count;
	}

	public synchronized void doSomething() {
		while (!start) {
			// do nothing... busy waiting
			System.out.println("do nothing... busy waiting");
		}
	}

	public synchronized void setStop() {
		this.start = true;
	}

	public static void main(String args[]) throws InterruptedException {
		Test latch = new Test(10000);
		latch.countDown();

		latch.await();
	}

	private void await() {
		// TODO Auto-generated method stub
		
	}

	private void countDown() {
		// TODO Auto-generated method stub
		
	}
}
