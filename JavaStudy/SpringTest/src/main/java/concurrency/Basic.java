package concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import concurrency.annotations.GuardedBy;
import concurrency.annotations.ThreadSafe;
@SuppressWarnings("unused")
public class Basic {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 1. Java Synchronized Blocks
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*	The synchronized keyword can be used to mark four different types of blocks:
		1.	Instance methods
		2.	Static methods
		3.	Code blocks inside instance methods
		4.	Code blocks inside static methods
*/

	private static class SynchronizedBlocks{
		
		static int count = 0;
		
		/*1.1 Synchronized Instance Methods*/
		public synchronized void add(int value){
			this.count += value;
		}
		
		
		/*1.2 Synchronized Static Methods*/
		
		// Synchronized static methods are synchronized on the class object of
		// the class the synchronized static method belongs to. Since only one
		// class object exists in the Java VM per class, only one thread can
		// execute inside a static synchronized method in the same class.

		// If the static synchronized methods are located in different classes,
		// then one thread can execute inside the static synchronized methods of
		// each class. One thread per class regardless of which static
		// synchronized method it calls.
		public static synchronized void add2(int value) {
			count += value;
		}
		  
		
		/*1.3 Synchronized Blocks in Instance Methods*/
		
		// Notice how the Java synchronized block construct takes an object in
		// parentheses. In the example "this" is used, which is the instance the
		// add method is called on. The object taken in the parentheses by the
		// synchronized construct is called a monitor object. The code is said
		// to be synchronized on the monitor object. A synchronized instance
		// method uses the object it belongs to as monitor object.
		public void add3(int value) {

			synchronized (this) {
				this.count += value;
			}
		}

		// Only one thread can execute inside a Java code block synchronized on
		// the same monitor object.
		
		// The following two examples are both synchronized on the instance they
		// are called on. They are therefore equivalent with respect to
		// synchronization:
		public class MyClass {

			public synchronized void log1(String msg1, String msg2) {
				log.writeln(msg1);
				log.writeln(msg2);
			}

			public void log2(String msg1, String msg2) {
				synchronized (this) {
					log.writeln(msg1);
					log.writeln(msg2);
				}
			}
		}
		static class log{public static void writeln(String msg2) {}}; //Mockup
	}
	
	
	/* 1.4 Synchronized Blocks in Static Methods*/
	
	// Here are the same two examples as static methods. These methods are
	// synchronized on the class object of the class the methods belong to:
	
	// Only one thread can execute inside any of these two methods at the same
	// time.
	
	// Had the second synchronized block been synchronized on a different object
	// thanMyClass.class, then one thread could execute inside each method at
	// the same time.
	
	public static class MyClass {

		public static synchronized void log1(String msg1, String msg2) {
			log.writeln(msg1);
			log.writeln(msg2);
		}

		public static void log2(String msg1, String msg2) {
			synchronized (MyClass.class) {
				log.writeln(msg1);
				log.writeln(msg2);
			}
		}
		 static class log{public static void writeln(String msg2) {}}; //Mockup
	}

	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 2. Thread Signaling
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*2.1 Signaling via Shared Objects*/
	
	// Thread A and B must have a reference to a shared MySignal instance for
	// the signaling to work. If thread A and B has references to different
	// MySignal instance, they will not detect each others signals. The data to
	// be processed can be located in a shared buffer separate from the MySignal
	// instance.
	public class MySignal {

		protected boolean hasDataToProcess = false;

		public synchronized boolean hasDataToProcess() {
			return this.hasDataToProcess;
		}

		public synchronized void setHasDataToProcess(boolean hasData) {
			this.hasDataToProcess = hasData;
		}

	}
	
	/*2.2 Busy Wait*/
	
	// Notice how the while loop keeps executing until hasDataToProcess()
	// returns true. This is called busy waiting. The thread is busy while
	// waiting.
	public class Client {

		protected MySignal sharedSignal;
		
		public void doSomething() {
			while (!sharedSignal.hasDataToProcess()) {
				// do nothing... busy waiting
			}
		}
	}
	
	
	/*2.3 wait(), notify() and notifyAll()*/
	
	// Busy waiting is not a very efficient utilization of the CPU in the
	// computer running the waiting thread, except if the average waiting time
	// is very small. Else, it would be smarter if the waiting thread could
	// somehow sleep or become inactive until it receives the signal it is
	// waiting for.
	// Java has a builtin wait mechanism that enable threads to become inactive
	// while waiting for signals. The class java.lang.Object defines three
	// methods, wait(), notify(), and notifyAll(), to facilitate this.
	
	public class MonitorObject {
	}

	public class MyWaitNotify {

		MonitorObject myMonitorObject = new MonitorObject();

		public void doWait(){
	    synchronized(myMonitorObject){
	      try{
	        myMonitorObject.wait();
	      } catch(InterruptedException e){}
	    }
	  }

		public void doNotify() {
			synchronized (myMonitorObject) {
				myMonitorObject.notify();
			}
		}
	}
	
	
	/*2.4 Missed Signals*/
	
	// The methods notify() and notifyAll() do not save the method calls to them
	// in case no threads are waiting when they are called. The notify signal is
	// then just lost. Therefore, if a thread calls notify() before the thread
	// to signal has called wait(), the signal will be missed by the waiting
	// thread. This may or may not be a problem, but in some cases this may
	// result in the waiting thread waiting forever, never waking up, because
	// the signal to wake up was missed.
	//
	// To avoid losing signals they should be stored inside the signal class. In
	// the MyWaitNotify example the notify signal should be stored in a member
	// variable inside the MyWaitNotify instance. Here is a modified version of
	// MyWaitNotify that does this:
	
	public class MyWaitNotify2 {

		MonitorObject myMonitorObject = new MonitorObject();
		boolean wasSignalled = false;

		public void doWait(){
		    synchronized(myMonitorObject){
		      if(!wasSignalled){
		        try{
		          myMonitorObject.wait();
		         } catch(InterruptedException e){}
		      }
		      //clear signal and continue running.
		      wasSignalled = false;
		    }
		  }

		public void doNotify() {
			synchronized (myMonitorObject) {
				wasSignalled = true;
				myMonitorObject.notify();
			}
		}
	}

	
	/*2.5 Spurious Wakeups*/
	
	// For inexplicable reasons it is possible for threads to wake up even if
	// notify() and notifyAll() has not been called. This is known as spurious
	// wakeups. Wakeups without any reason.
	
	// If a spurious wakeup occurs in the MyWaitNofity2 class's doWait() method
	// the waiting thread may continue processing without having received a
	// proper signal to do so! This could cause serious problems in your
	// application.
	
	// Notice how the wait() call is now nested inside a while loop instead of
	// an if-statement. If the waiting thread wakes up without having received a
	// signal, the wasSignalled member will still be false, and the while loop
	// will execute once more, causing the awakened thread to go back to
	// waiting.
	public class MyWaitNotify3 {

		MonitorObject myMonitorObject = new MonitorObject();
		boolean wasSignalled = false;

		public void doWait() {
			synchronized (myMonitorObject) {
				while (!wasSignalled) {
					try {
						myMonitorObject.wait();
					} catch (InterruptedException e) {
					}
				}
				// clear signal and continue running.
				wasSignalled = false;
			}
		}

		public void doNotify() {
			synchronized (myMonitorObject) {
				wasSignalled = true;
				myMonitorObject.notify();
			}
		}
	}
	
	
	/* 2.6 Multiple Threads Waiting for the Same Signals*/
	
	// The while loop is also a nice solution if you have multiple threads
	// waiting, which are all awakened using notifyAll(), but only one of them
	// should be allowed to continue. Only one thread at a time will be able to
	// obtain the lock on the monitor object, meaning only one thread can exit
	// the wait() call and clear the wasSignalled flag. Once this thread then
	// exits the synchronized block in the doWait() method, the other threads
	// can exit the wait() call and check the wasSignalled member variable
	// inside the while loop. However, this flag was cleared by the first thread
	// waking up, so the rest of the awakened threads go back to waiting, until
	// the next signal arrives.
	
	
	/* 2.7 Don't call wait() on constant String's or global objects*/
	
	// The problem with calling wait() and notify() on the empty string, or any
	// other constant string is, that the JVM/Compiler internally translates
	// constant strings into the same object. That means, that even if you have
	// two different MyWaitNotify4 instances, they both reference the same empty
	// string instance. This also means that threads calling doWait() on the
	// first MyWaitNotify4 instance risk being awakened by doNotify() calls on
	// the second MyWaitNotify instance.
	
	public class MyWaitNotify4 {

		String myMonitorObject = "";
		boolean wasSignalled = false;

		public void doWait() {
			synchronized (myMonitorObject) {
				while (!wasSignalled) {
					try {
						myMonitorObject.wait();
					} catch (InterruptedException e) {
					}
				}
				// clear signal and continue running.
				wasSignalled = false;
			}
		}

		public void doNotify() {
			synchronized (myMonitorObject) {
				wasSignalled = true;
				myMonitorObject.notify();
			}
		}
	}
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 3. Deadlock
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	// A deadlock is when two or more threads are blocked waiting to obtain
	// locks that some of the other threads in the deadlock are holding.
	// Deadlock can occur when multiple threads need the same locks, at the same
	// time, but obtain them in different order.
	// Thread 1 locks A, waits for B
	// Thread 2 locks B, waits for A
	
	/*Starvation*/
	// Starvation occurs when a thread is perpetually denied access to resources
	// it needs in order to make progress; the most commonly starved resource is
	// CPU cycles. Starvation in Java applications can be caused by
	// inappropriate use of thread priorities. It can also be caused by
	// executing nonterminating constructs (infinite loops or resource waits
	// that do not terminate) with a lock held, since other threads that need
	// that lock will never be able to acquire it.
	
	/*Livelock*/
	// Livelock is a form of liveness failure in which a thread, while not
	// blocked, still cannot make progress because it keeps retrying an
	// operation that will always fail. Livelock often occurs in transactional
	// messaging applications, where the messaging infrastructure rolls back a
	// transaction if a message cannot be processed successfully, and puts it
	// back at the head of the queue. If a bug in the message handler for a
	// particular type of message causes it to fail, every time the message is
	// dequeued and passed to the buggy handler, the transaction is rolled back.
	// Since the message is now back at the head of the queue, the handler is
	// called over and over with the same result. (This is sometimes called the
	// poison message problem.) The message handling thread is not blocked, but
	// it will never make progress either. This form of livelock often comes
	// from overeager error-recovery code that mistakenly treats an
	// unrecoverable error as a recoverable one.
	// The solution for this variety of livelock is to introduce some randomness
	// into the retry mechanism.
	
	/*Lock-ordering Deadlock.*/
	public class TreeNode {

		TreeNode parent = null;
		List<TreeNode> children = new ArrayList<TreeNode>();

		public synchronized void addChild(TreeNode child) {
			if (!this.children.contains(child)) {
				this.children.add(child);
				child.setParentOnly(this);
			}
		}

		public synchronized void addChildOnly(TreeNode child){
		    if(!this.children.contains(child)){
			      this.children.add(child);
			    }
		  }

		public synchronized void setParent(TreeNode parent) {
			this.parent = parent;
			parent.addChildOnly(this);
		}

		public synchronized void setParentOnly(TreeNode parent) {
			this.parent = parent;
		}
	}
	
	/*
	 * Thread 1: parent.addChild(child); //locks parent -->
	 * child.setParentOnly(parent);
	 * 
	 * Thread 2: child.setParent(parent); //locks child -->
	 * parent.addChildOnly()
	 * 
	 * First thread 1 calls parent.addChild(child). Since addChild() is
	 * synchronized thread 1 effectively locks the parent object for access from
	 * other treads. Then thread 2 calls child.setParent(parent). Since
	 * setParent() is synchronized thread 2 effectively locks the child object
	 * for acces from other threads.
	 */

	
	/*3.1 More Complicated Deadlocks*/
	
/*	
 * Thread 1  locks A, waits for B
	Thread 2  locks B, waits for C
	Thread 3  locks C, waits for D
	Thread 4  locks D, waits for A
*/
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4. Deadlock Prevention
	 * 
	 * 1.	Lock Ordering
	 * 2.	Lock Timeout
	 * 3.	Deadlock Detection
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*4.1 Lock Ordering*/
	
	// Thread 1:
	//
	// lock A
	// lock B
	//
	//
	// Thread 2:
	//
	// wait for A
	// lock C (when A locked)
	//
	//
	// Thread 3:
	//
	// wait for A
	// wait for B
	// wait for C
	
	// If a thread, like Thread 3, needs several locks, it must take them in the
	// decided order. It cannot take a lock later in the sequence until it has
	// obtained the earlier locks.
	
	// For instance, neither Thread 2 or Thread 3 can lock C until they have
	// locked A first. Since Thread 1 holds lock A, Thread 2 and 3 must first
	// wait until lock A is unlocked. Then they must succeed in locking A,
	// before they can attempt to lock B or C.
	
	// Lock ordering is a simple yet effective deadlock prevention mechanism.
	// However, it can only be used if you know about all locks needed ahead of
	// taking any of the locks. This is not always the case.
	
	
	/*4.1.1 Dynamic Lock-ordering Deadlock. Don’t Do this.*/
	static class DeadLockSample {
		// Warning: deadlock-prone!
		public static void transferMoney(Account fromAccount, Account toAccount,
				Integer amount) throws InsufficientFundsException {
			synchronized (fromAccount) {
				synchronized (toAccount) {
					if (fromAccount.getBalance().compareTo(amount) < 0)
						throw new InsufficientFundsException();
					else {
						fromAccount.debit(amount);
						toAccount.credit(amount);
					}
				}
			}
		}
	}
	
	//Helpers
	static class  Account{
		int balance;
		public Integer getBalance() {return balance;}
		public void debit(Integer amount) {
			this.balance = balance - amount;
		}
		public void credit(Integer amount) {
			this.balance = balance + amount;
		}}
	static class  InsufficientFundsException extends Exception{}

	
	/*4.1.2 Inducing a Lock Ordering to Avoid Deadlock..*/
	
	// One way to induce an ordering on objects is to use
	// System.identityHashCode, which returns the value that would be returned
	// by Object.hashCode.
	
	// To prevent inconsistent lock ordering in this case, a third “tie
	// breaking” lock is used. By acquiring the tie-breaking lock before
	// acquiring either Account lock, we ensure that only one thread at a time
	// performs the risky task of acquiring two locks in an arbitrary order,
	// eliminating the possibility of deadlock (so long as this mechanism is
	// used consistently). If hash collisions were common, this technique might
	// become a concurrency bottleneck (just as having a single, program-wide
	// lock would), but because hash collisions with System.identityHashCode are
	// vanishingly infrequent, this technique provides that last bit of safety
	// at little cost.
	private static final Object tieLock = new Object();

	public static void transferMoney(final Account fromAcct, final Account toAcct,
			final Integer amount) throws InsufficientFundsException {
		class Helper {
			public void transfer() throws InsufficientFundsException {
				if (fromAcct.getBalance().compareTo(amount) < 0)
					throw new InsufficientFundsException();
				else {
					fromAcct.debit(amount);
					toAcct.credit(amount);
				}
			}
		}
		int fromHash = System.identityHashCode(fromAcct);
		int toHash = System.identityHashCode(toAcct);

		if (fromHash < toHash) {
			synchronized (fromAcct) {
				synchronized (toAcct) {
					new Helper().transfer();
				}
			}
		} else if (fromHash > toHash) {
			synchronized (toAcct) {
				synchronized (fromAcct) {
					new Helper().transfer();
				}
			}
		} else {
			synchronized (tieLock) {
				synchronized (fromAcct) {
					synchronized (toAcct) {
						new Helper().transfer();
					}
				}
			}
		}
	}
	
	
	/*4.1.3 Driver Loop that Induces Deadlock Under Typical Conditions.*/

	//Check main method
	
	
	/*4.1.4 Open Call*/
	// Calling a method with no locks held is called an open call [CPJ 2.4.1.3],
	// and classes that rely on open calls are more well-behaved and composable
	// than classes that make calls with locks held. Using open calls to avoid
	// deadlock is analogous to using encapsulation to provide thread safety:
	// while one can certainly construct a thread-safe program without any
	// encapsulation, the thread safety analysis of a program that makes
	// effective use of encapsulation is far easier than that of one that does
	// not. Similarly, the liveness analysis of a program that relies
	// exclusively on open calls is far easier than that of one that does not.
	// Restricting yourself to open calls makes it far easier to identify the
	// code paths that acquire multiple locks and therefore to ensure that locks
	// are acquired in a consistent order.
	
	/*Lock-ordering Deadlock Between Cooperating Objects. Don’t Do this.*/
	// Warning: deadlock-prone!
	
	
	class Taxi {
	    @GuardedBy("this") private Point location, destination;
	    private final Dispatcher dispatcher;

	    public Taxi(Dispatcher dispatcher) {
	        this.dispatcher = dispatcher;
	    }

	    public synchronized Point getLocation() {
	        return location;
	    }

	    public synchronized void setLocation(Point location) {
	        this.location = location;
	        if (location.equals(destination))
	            dispatcher.notifyAvailable(this);
	    }
	}

	class Dispatcher {
	    @GuardedBy("this") private final Set<Taxi> taxis;
	    @GuardedBy("this") private final Set<Taxi> availableTaxis;

	    public Dispatcher() {
	        taxis = new HashSet<Taxi>();
	        availableTaxis = new HashSet<Taxi>();
	    }

	    public synchronized void notifyAvailable(Taxi taxi) {
	        availableTaxis.add(taxi);
	    }

	    public synchronized Image getImage() {
	        Image image = new Image();
	        for (Taxi t : taxis)
	            image.drawMarker(t.getLocation());
	        return image;
	    }
	}

	//Mockups
	class Point{}
	class Image{public void drawMarker(Point location) {}}

	// Of course, Taxi and Dispatcher didn’t know that they were each half of a
	// deadlock waiting to happen. And they shouldn’t have to; a method call is
	// an abstraction barrier intended to shield you from the details of what
	// happens on the other side. But because you don’t know what is happening
	// on the other side of the call, calling an alien method with a lock held
	// is difficult to analyze and therefore risky.
	
	
	// Invoking an alien method with a lock held is asking for liveness trouble.
	// The alien method might acquire other locks (risking deadlock) or block
	// for an unexpectedly long time, stalling other threads that need the lock
	// you hold.
	
	/*Alien Method*/
	// From the perspective of a class C, an alien method is one whose behavior is
	// not fully specified by C. This includes methods in other classes as well as
	// overrideable methods (neither private nor final) in C itself. Passing an
	// object to an alien method must also be considered publishing that object.
	// Since you can’t know what code will actually be invoked, you don’t know
	// that the alien method won’t publish the object or retain a reference to  it
	// that might later be used from another thread.
	
	
	/*Using Open Calls to Avoiding Deadlock Between Cooperating Objects.*/
	
	// Taxi and Dispatcher in Listing 10.5 can be easily refactored to use open
	// calls and thus eliminate the deadlock risk. This involves shrinking the
	// synchronized blocks to guard only operations that involve shared state,
	// as in Listing 10.6. Very often, the cause of problems like those in
	// Listing 10.5 is the use of synchronized methods instead of smaller
	// synchronized blocks for reasons of compact syntax or simplicity rather
	// than because the entire method must be guarded by a lock. (As a bonus,
	// shrinking the synchronized block may also improve scalability as well.)
	@ThreadSafe
	class Taxi2 {
	    @GuardedBy("this") private Point location = null, destination = null;
	    private final Dispatcher2 dispatcher = null;
//	    ...
	    public synchronized Point getLocation() {
	        return location;
	    }

	    public void setLocation(Point location) {
	        boolean reachedDestination;
	        synchronized  (this) {
	            this.location = location;
	            reachedDestination = location.equals(destination);
	        }
	        if (reachedDestination)
	            dispatcher.notifyAvailable(this);
	    }
	}

	@ThreadSafe
	class Dispatcher2 {
	    @GuardedBy("this") private final Set<Taxi2> taxis = null;
	    @GuardedBy("this") private final Set<Taxi2> availableTaxis = null;
//	    ...
	    public synchronized void notifyAvailable(Taxi2 taxi2) {
	        availableTaxis.add(taxi2);
	    }

	    public Image getImage() {
	        Set<Taxi2> copy;
	        synchronized  (this) {
	            copy = new HashSet<Taxi2>(taxis);
	        }
	        Image image = new Image();
	        for (Taxi2 t : copy)
	            image.drawMarker(t.getLocation());
	        return image;
	    }
	}

	// Strive to use open calls throughout your program. Programs that rely on
	// open calls are far easier to analyze for deadlock-freedom than those that
	// allow calls to alien methods with locks held.
	
	
	
	/*4.2 Lock Timeout*/
	
/*	Thread 1 locks A
	Thread 2 locks B

	Thread 1 attempts to lock B but is blocked
	Thread 2 attempts to lock A but is blocked

	Thread 1's lock attempt on B times out
	Thread 1 backs up and releases A as well
	Thread 1 waits randomly (e.g. 257 millis) before retrying.

	Thread 2's lock attempt on A times out
	Thread 2 backs up and releases B as well
	Thread 2 waits randomly (e.g. 43 millis) before retrying.*/
	
	// In the above example Thread 2 will retry taking the locks about 200
	// millis before Thread 1 and will therefore likely succeed at taking both
	// locks. Thread 1 will then wait already trying to take lock A. When Thread
	// 2 finishes, Thread 1 will be able to take both locks too (unless Thread 2
	// or another thread takes the locks in between).

	// An issue to keep in mind is, that just because a lock times out it does
	// not necessarily mean that the threads had deadlocked. It could also just
	// mean that the thread holding the lock (causing the other thread to time
	// out) takes a long time to complete its task.
	//
	// A problem with the lock timeout mechanism is that it is not possible to
	// set a timeout for entering a synchronized block in Java. You will have to
	// create a custom lock class or use one of the Java 5 concurrency
	// constructs in the java.util.concurrency package.
	
	
	/*4.3 Deadlock Detection*/
	// Deadlock detection is a heavier deadlock prevention mechanism aimed at
	// cases in which lock ordering isn't possible, and lock timeout isn't feasible.

	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 5. Locks in Java
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	// A lock is a thread synchronization mechanism like synchronized blocks
	// except locks can be more sophisticated than Java's synchronized blocks.
	// Locks (and other more advanced synchronization mechanisms) are created
	// using synchronized blocks, so it is not like we can get totally rid of
	// the synchronized keyword.
	
	public class Lock {

		private boolean isLocked = false;

		public synchronized void lock() throws InterruptedException {
			while (isLocked) {
				wait();
			}
			isLocked = true;
		}

		public synchronized void unlock() {
			isLocked = false;
			notify();
		}
	}
	
	public class Counter {

		private Lock lock = new Lock();
		private int count = 0;

		public int inc() {
			try {
				lock.lock();
			} catch (InterruptedException e) {
			}
			int newCount = ++count;
			lock.unlock();
			return newCount;
		}
	}

	
	/*5.1 Lock Reentrance*/
	
	// Synchronized blocks in Java are reentrant. This means, that if a Java
	// thread enters a synchronized block of code, and thereby take the lock on
	// the monitor object the block is synchronized on, the thread can enter
	// other Java code blocks synchronized on the same monitor object.
	
	public class Reentrant {

		public synchronized void outer() {
			inner();
		}

		public synchronized void inner() {
			// do something
		}
	}
	
	// Notice how both outer() and inner() are declared synchronized, which in
	// Java is equivalent to a synchronized(this) block. If a thread calls
	// outer() there is no problem calling inner() from inside outer(), since
	// both methods (or blocks) are synchronized on the same monitor object
	// ("this"). If a thread already holds the lock on a monitor object, it has
	// access to all blocks synchronized on the same monitor object. This is
	// called reentrance. The thread can reenter any block of code for which it
	// already holds the lock
	
	
	// The lock implementation shown earlier is not reentrant. If we rewrite the
	// Reentrantclass like below, the thread calling outer() will be blocked
	// inside the lock.lock()in the inner() method.
	
	public class Lock2 {

		boolean isLocked = false;
		Thread lockedBy = null;
		int lockedCount = 0;

		public synchronized void lock() throws InterruptedException {
			Thread callingThread = Thread.currentThread();
			while (isLocked && lockedBy != callingThread) {
				wait();
			}
			isLocked = true;
			lockedCount++;
			lockedBy = callingThread;
		}

		public synchronized void unlock() {
			if (Thread.currentThread() == this.lockedBy) {
				lockedCount--;

				if (lockedCount == 0) {
					isLocked = false;
					notify();
				}
			}
		}

		// ...
	}
	
	
	
	/*5.2 Read & Write Lock Java Implementation*/
	
	// The rules for write access are implemented in the lockWrite() method. A
	// thread that wants write access starts out by requesting write access
	// (writeRequests++). Then it will check if it can actually get write
	// access. A thread can get write access if there are no threads with read
	// access to the resource, and no threads with write access to the resource.
	
	// How many threads have requested write access doesn't matter.
	// It is worth noting that both unlockRead() and unlockWrite()
	// callsnotifyAll() rather than notify(). To explain why that is, imagine
	// the following situation:
	
	// Inside the ReadWriteLock there are threads waiting for read access, and
	// threads waiting for write access. If a thread awakened by notify() was a
	// read access thread, it would be put back to waiting because there are
	// threads waiting for write access. However, none of the threads awaiting
	// write access are awakened, so nothing more happens. No threads gain
	// neither read nor write access. By calling noftifyAll() all waiting
	// threads are awakened and check if they can get the desired access.
	
	// Calling notifyAll() also has another advantage. If multiple threads are
	// waiting for read access and none for write access, and unlockWrite() is
	// called, all threads waiting for read access are granted read access at
	// once - not one by one.

	public class ReadWriteLock {

		private int readers = 0;
		private int writers = 0;
		private int writeRequests = 0;

		public synchronized void lockRead() throws InterruptedException {
			while (writers > 0 || writeRequests > 0) {
				wait();
			}
			readers++;
		}

		public synchronized void unlockRead() {
			readers--;
			notifyAll();
		}

		public synchronized void lockWrite() throws InterruptedException {
			writeRequests++;

			while (readers > 0 || writers > 0) {
				wait();
			}
			writeRequests--;
			writers++;
		}

		public synchronized void unlockWrite() throws InterruptedException {
			writers--;
			notifyAll();
		}
	}
	
	
	// The rules for write access are implemented in the lockWrite() method. A
	// thread that wants write access starts out by requesting write access
	// (writeRequests++). Then it will check if it can actually get write
	// access. A thread can get write access if there are no threads with read
	
	// access to the resource, and no threads with write access to the resource.
	// How many threads have requested write access doesn't matter.
	// It is worth noting that both unlockRead() and unlockWrite()
	// callsnotifyAll() rather than notify(). To explain why that is, imagine
	// the following situation:
	
	// Inside the ReadWriteLock there are threads waiting for read access, and
	// threads waiting for write access. If a thread awakened by notify() was a
	// read access thread, it would be put back to waiting because there are
	// threads waiting for write access. However, none of the threads awaiting
	// write access are awakened, so nothing more happens. No threads gain
	// neither read nor write access. By calling noftifyAll() all waiting
	// threads are awakened and check if they can get the desired access.
	
	// Calling notifyAll() also has another advantage. If multiple threads are
	// waiting for read access and none for write access, and unlockWrite() is
	// called, all threads waiting for read access are granted read access at
	// once - not one by one.
	
	
	/*5.3 Read / Write Lock Reentrance*/
	
	// The ReadWriteLock class shown earlier is not reentrant. If a thread that
	// has write access requests it again, it will block because there is
	// already one writer - itself. Furthermore, consider this case:
	
	// 1. Thread 1 gets read access.
	// 2. Thread 2 requests write access but is blocked because there is one
	// reader.
	// 3. Thread 1 re-requests read access (re-enters the lock), but is blocked
	// because there is a write request
	
	// In this situation the previous ReadWriteLock would lock up - a situation
	// similar to deadlock. No threads requesting neither read nor write access
	// would be granted so.
	// To make the ReadWriteLock reentrant it is necessary to make a few
	// changes. Reentrance for readers and writers will be dealt with
	// separately.
	
	
	/* 5.3.1 Read Reentrance*/
	// To make the ReadWriteLock reentrant for readers we will first establish
	// the rules for read reentrance:
	// • A thread is granted read reentrance if it can get read access (no
	// writers or write requests), or if it already has read access (regardless
	// of write requests).

	
	public class ReadWriteLock2 {

		private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

		private int writers = 0;
		private int writeRequests = 0;

		public synchronized void lockRead() throws InterruptedException {
			Thread callingThread = Thread.currentThread();
			while (!canGrantReadAccess(callingThread)) {
				wait();
			}

			readingThreads.put(callingThread,
					(getReadAccessCount(callingThread) + 1));
		}

		public synchronized void unlockRead() {
			Thread callingThread = Thread.currentThread();
			int accessCount = getReadAccessCount(callingThread);
			if (accessCount == 1) {
				readingThreads.remove(callingThread);
			} else {
				readingThreads.put(callingThread, (accessCount - 1));
			}
			notifyAll();
		}

		private boolean canGrantReadAccess(Thread callingThread){
		    if(writers > 0)            return false;
		    if(isReader(callingThread)) return true;
		    if(writeRequests > 0)      return false;
		    return true;
		  }

		private int getReadAccessCount(Thread callingThread) {
			Integer accessCount = readingThreads.get(callingThread);
			if (accessCount == null)
				return 0;
			return accessCount.intValue();
		}

		private boolean isReader(Thread callingThread) {
			return readingThreads.get(callingThread) != null;
		}

	}

	
	/*5.3.2 Write Reentrance*/
	
//	Write reentrance is granted only if the thread has already write access. 
	
	public class ReadWriteLock3 {

		private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

		private int writeAccesses = 0;
		private int writeRequests = 0;
		private Thread writingThread = null;

		public synchronized void lockWrite() throws InterruptedException {
			writeRequests++;
			Thread callingThread = Thread.currentThread();
			while (!canGrantWriteAccess(callingThread)) {
				wait();
			}
			writeRequests--;
			writeAccesses++;
			writingThread = callingThread;
		}

		public synchronized void unlockWrite() throws InterruptedException {
			writeAccesses--;
			if (writeAccesses == 0) {
				writingThread = null;
			}
			notifyAll();
		}

		private boolean canGrantWriteAccess(Thread callingThread) {
			if (hasReaders())
				return false;
			if (writingThread == null)
				return true;
			if (!isWriter(callingThread))
				return false;
			return true;
		}

		private boolean hasReaders() {
			return readingThreads.size() > 0;
		}

		private boolean isWriter(Thread callingThread) {
			return writingThread == callingThread;
		}
	}

	
	
	/*5.3.3 Read to Write Reentrance*/
	
	// Sometimes it is necessary for a thread that have read access to also
	// obtain write access. For this to be allowed the thread must be the only
	// reader.
	
	public class ReadWriteLock4 {

		private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

		private int writeAccesses = 0;
		private int writeRequests = 0;
		private Thread writingThread = null;

		public synchronized void lockWrite() throws InterruptedException {
			writeRequests++;
			Thread callingThread = Thread.currentThread();
			while (!canGrantWriteAccess(callingThread)) {
				wait();
			}
			writeRequests--;
			writeAccesses++;
			writingThread = callingThread;
		}

		public synchronized void unlockWrite() throws InterruptedException {
			writeAccesses--;
			if (writeAccesses == 0) {
				writingThread = null;
			}
			notifyAll();
		}

		private boolean canGrantWriteAccess(Thread callingThread) {
			if (isOnlyReader(callingThread))
				return true;
			if (hasReaders())
				return false;
			if (writingThread == null)
				return true;
			if (!isWriter(callingThread))
				return false;
			return true;
		}

		private boolean hasReaders() {
			return readingThreads.size() > 0;
		}

		private boolean isWriter(Thread callingThread) {
			return writingThread == callingThread;
		}

		private boolean isOnlyReader(Thread callingThread) {
			return readingThreads.size() == 1 && readingThreads.get(callingThread) != null;
		}

	}

	
	/*5.3.4 Write to Read Reentrance*/
	
	// Sometimes a thread that has write access needs read access too. A writer
	// should always be granted read access if requested. If a thread has write
	// access no other threads can have read nor write access, so it is not
	// dangerous.
	
	public class ReadWriteLock5 {

		//Mockups
		private int writeRequests = 0;
		private Thread writingThread = null;

		private boolean canGrantReadAccess(Thread callingThread) {
			if (isWriter(callingThread))
				return true;
			if (writingThread != null)
				return false;
			if (isReader(callingThread))
				return true;
			if (writeRequests > 0)
				return false;
			return true;
		}

	    //Mockups
		private boolean isReader(Thread callingThread) {return false;}
		private boolean isWriter(Thread callingThread) {return false;}

	}

	
	/* 5.3.5 Fully Reentrant ReadWriteLock*/
	
	public class ReadWriteLockFinal {

		private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

		private int writeAccesses = 0;
		private int writeRequests = 0;
		private Thread writingThread = null;

		public synchronized void lockRead() throws InterruptedException {
			Thread callingThread = Thread.currentThread();
			while (!canGrantReadAccess(callingThread)) {
				wait();
			}

			readingThreads.put(callingThread,
					(getReadAccessCount(callingThread) + 1));
		}

		private boolean canGrantReadAccess(Thread callingThread) {
			if (isWriter(callingThread))
				return true;
			if (hasWriter())
				return false;
			if (isReader(callingThread))
				return true;
			if (hasWriteRequests())
				return false;
			return true;
		}

		public synchronized void unlockRead() {
			Thread callingThread = Thread.currentThread();
			if (!isReader(callingThread)) {
				throw new IllegalMonitorStateException(
						"Calling Thread does not"
								+ " hold a read lock on this ReadWriteLock");
			}
			int accessCount = getReadAccessCount(callingThread);
			if (accessCount == 1) {
				readingThreads.remove(callingThread);
			} else {
				readingThreads.put(callingThread, (accessCount - 1));
			}
			notifyAll();
		}

		public synchronized void lockWrite() throws InterruptedException {
			writeRequests++;
			Thread callingThread = Thread.currentThread();
			while (!canGrantWriteAccess(callingThread)) {
				wait();
			}
			writeRequests--;
			writeAccesses++;
			writingThread = callingThread;
		}

		public synchronized void unlockWrite() throws InterruptedException {
			if (!isWriter(Thread.currentThread())) {
				throw new IllegalMonitorStateException(
						"Calling Thread does not"
								+ " hold the write lock on this ReadWriteLock");
			}
			writeAccesses--;
			if (writeAccesses == 0) {
				writingThread = null;
			}
			notifyAll();
		}

		private boolean canGrantWriteAccess(Thread callingThread) {
			if (isOnlyReader(callingThread))
				return true;
			if (hasReaders())
				return false;
			if (writingThread == null)
				return true;
			if (!isWriter(callingThread))
				return false;
			return true;
		}

		private int getReadAccessCount(Thread callingThread) {
			Integer accessCount = readingThreads.get(callingThread);
			if (accessCount == null)
				return 0;
			return accessCount.intValue();
		}

		private boolean hasReaders() {
			return readingThreads.size() > 0;
		}

		private boolean isReader(Thread callingThread) {
			return readingThreads.get(callingThread) != null;
		}

		private boolean isOnlyReader(Thread callingThread) {
			return readingThreads.size() == 1
					&& readingThreads.get(callingThread) != null;
		}

		private boolean hasWriter() {
			return writingThread != null;
		}

		private boolean isWriter(Thread callingThread) {
			return writingThread == callingThread;
		}

		private boolean hasWriteRequests() {
			return this.writeRequests > 0;
		}

	}
	
	
	
	
	
	public static void main(String[] args) {

		final int NUM_THREADS = 20;
		final int NUM_ACCOUNTS = 5;
		final int NUM_ITERATIONS = 1000000;

		final Random rnd = new Random();
		final Account[] accounts = new Account[NUM_ACCOUNTS];

		for (int i = 0; i < accounts.length; i++)
			accounts[i] = new Account();

		class TransferThread extends Thread {
			public void run() {
				
				for (int i = 0; i < NUM_ITERATIONS; i++) {
					int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
					int toAcct = rnd.nextInt(NUM_ACCOUNTS);
					Integer amount = new Integer(rnd.nextInt(1000));
					try {
						System.out.println("Account [" + fromAcct + "] -> Account [" + toAcct + "]");
						DeadLockSample.transferMoney(accounts[fromAcct], accounts[toAcct], amount); //DeadLock sample.
//						transferMoney(accounts[fromAcct], accounts[toAcct], amount); // Tie Lock sample - DeadLock Prevention.
					} catch (InsufficientFundsException e) {
					}
				}
			}
		}
		for (int i = 0; i < NUM_THREADS; i++)
			new TransferThread().start();

	}

}
