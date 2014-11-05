package cache;

import com.hazelcast.core.*;
import com.hazelcast.config.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
 
public class Locking {
	public static void main(String[] args) {
		System.out.println("Locking Start...");
		Config cfg = new Config();
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
		Lock lock = instance.getLock("theTime");

		try {
			while (true) {
				if (lock.tryLock(30, TimeUnit.SECONDS)) {
					try {
						while (true) {
							System.err.println(new Date());
							Thread.sleep(1000);
						}
					} finally {
						lock.unlock();
					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
