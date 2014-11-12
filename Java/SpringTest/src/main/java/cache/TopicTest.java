package cache;

import java.util.Date;
import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.transaction.impl.Transaction;

public class TopicTest {

	public static void main(String[] args) throws Exception{
		HazelcastInstance hzi = Hazelcast.newHazelcastInstance();
		
		ITopic<String> topicList = hzi.getTopic("testTopic");
		topicList.addMessageListener(new TopicListener());
		
		while(true){
			topicList.publish(new Date() + "[" + hzi.getCluster().getLocalMember() + "]");
			Thread.sleep(1000);
		}
		
	}
}
