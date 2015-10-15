package concurrency.showcases.pubsub;

public class Main {
	Buffer buffer;
	Producer producer;
	Consumer consumer;
	
	public void runNG(Buffer buffer){
		buffer = new IntBufferNG();
		producer = new Producer( buffer );
		consumer = new Consumer( buffer );
		producer.start();
		consumer.start();
	}

	public void runOK(){
		buffer = new IntBuffer();
		producer = new Producer( buffer );
		consumer = new Consumer( buffer );
		producer.start();
		consumer.start();		
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.runOK();
	}
}
