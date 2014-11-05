package cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class TopicListener implements MessageListener<String> {

	private ExecutorService exec = Executors.newFixedThreadPool(10);
	
	public void onMessage(final Message<String> msg) {
		exec.execute(new Runnable(){
			public void run() {
				System.out.println("Received: " + msg.getMessageObject() );	
			}
		});
		
	}
}
