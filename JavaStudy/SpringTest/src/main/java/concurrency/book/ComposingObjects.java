package concurrency.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import concurrency.annotations.Book;
import concurrency.annotations.GuardedBy;
import concurrency.annotations.NotThreadSafe;
import concurrency.annotations.ThreadSafe;

@Book(name = "Java Concurrency in Practice", chapter = "Chapter 4")
public class ComposingObjects {
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4.1. Simple Thread-safe Counter Using the Java Monitor Pattern.
	 * Java monitor pattern encapsulates all its mutable state 
	 * and guards it with the object¡¯s own intrinsic lock.
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@ThreadSafe
	public final class Counter {
	    @GuardedBy("this")  private long value = 0;

	    public synchronized  long getValue() {
	        return value;
	    }
	    public  synchronized  long increment() {
	        if (value == Long.MAX_VALUE)
	            throw new IllegalStateException("counter overflow");
	        return ++value;
	    }
	}


	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4.2 Instance Confinement
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@ThreadSafe
	class Person{
		//Need to be tread-safe too
	}
	
	@ThreadSafe
	public class PersonSet {
		@GuardedBy("this")
		private final Set<Person> mySet = new HashSet<Person>();

		public synchronized void addPerson(Person p) {
			mySet.add(p);
		}

		public synchronized boolean containsPerson(Person p) {
			return mySet.contains(p);
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4.2.1 Java Monitor Pattern
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@ThreadSafe
	class MutablePoint{
		public int x,y;
		
		public MutablePoint(){
			x = 0; 
			y=0;
		}
		public MutablePoint(MutablePoint point){
			this.x = point.x;
			this.y = point.y;
		}
	}
	
	class MonitorVehicleTracker{
		
		private final Map<String, MutablePoint> locations;
		
		public MonitorVehicleTracker(Map<String, MutablePoint> locations){
			this.locations = deepCopy(locations);
		}
		
		public synchronized Map<String, MutablePoint> getLocations() {
			return deepCopy(locations);
		}
		
		public synchronized MutablePoint getLocation(String id){
			MutablePoint loc = locations.get(id);
			return loc == null ? null : new MutablePoint(loc);
		}
		
		public synchronized void setLocation(String id, int x, int y){
			MutablePoint loc = locations.get(id);
			if(loc == null)
				throw new IllegalArgumentException("No such Id: " + id);
			loc.x = x;
			loc.y = y;
		}
		
		private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> map){
			
			Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();
			for(String id : map.keySet())
				result.put(id, new MutablePoint(map.get(id)));
			return Collections.unmodifiableMap(result);
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4.3 Delegating Thread Safety
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@ThreadSafe
	public class ImmutablePoint{
		
		/*
		 * ImmutablePoint is thread-safe because it is immutable. Immutable values can be freely
		 * shared and published, so we no longer need to copy the locations when returning them.
		 * */
		
		public final int x, y;
		
		public ImmutablePoint(int x, int y){
			this.x = x; this.y = y;
		}
	}
	
	public class DelegatingVehicleTracker{
		private final ConcurrentMap<String, ImmutablePoint> locations;
		private final Map<String, ImmutablePoint> unmodifiableMap;
		
		public DelegatingVehicleTracker(Map<String, ImmutablePoint> points){
			locations = new ConcurrentHashMap<String, ImmutablePoint>(points);
			unmodifiableMap = Collections.unmodifiableMap(locations);
		}
		
		/*
		 * If we had used the MutablePoint class instead of Point, we would be
		 * letting getLocations() publish a reference to mutable state that is
		 * not thread-safe.
		 */ 
		public Map<String, ImmutablePoint> getLocations(){
			return unmodifiableMap;
		}

		/*
		 * While the monitor version returned a snapshot of the locations, the
		 * delegating version returns an unmodifiable but "live" view of the
		 * vehicle locations. This means that if thread A calls getLocations()
		 * and treand B later modifies the location of some of the points, those
		 * changes are reflected in the Map returned to thread A
		 */
		
		public Map<String, ImmutablePoint> getStaticCopyLocations(){
			return Collections.unmodifiableMap(new HashMap<String, ImmutablePoint>(locations));
		}
		
		/*
		 * getStaticCopyLocations() could instead return a shallow copy of the
		 * locations map. Since the contents of the Map are immutable, only the
		 * structure of the Map, not the contents, must be copied.
		 */
		
		public ImmutablePoint getLocation(String id){
			return locations.get(id);
		}
		
		public void setLocation(String id, int x, int y){
			if(locations.replace(id, new ImmutablePoint(x,y)) == null) 
				throw new IllegalArgumentException("Invalid vehicle name : " + id);
		}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4.3.3 When Delegation Fails
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public class NumberRange {
		// INVARIANT: lower <= upper
		private final AtomicInteger lower = new AtomicInteger(0);
		private final AtomicInteger upper = new AtomicInteger(0);

		public void setLower(int i) {
			// Warning -- unsafe check-then-act
			if (i > upper.get())
				throw new IllegalArgumentException("can't set lower to " + i + " > upper");
			lower.set(i);
		}

		public void setUpper(int i) {
			// Warning -- unsafe check-then-act
			if (i < lower.get())
				throw new IllegalArgumentException("can't set upper to " + i + " < lower");
			upper.set(i);
		}

		public boolean isInRange(int i) {
			return (i >= lower.get() && i <= upper.get());
		}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4.3.5 Example: Vehicle Tracker that Publishes Its State
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@ThreadSafe
	public class SafePoint {
	    @GuardedBy("this") private int x, y;

	    private SafePoint(int[] a) { this(a[0], a[1]); }

	    public SafePoint(SafePoint p) { this(p.get()); }

	    public SafePoint(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }

	    public synchronized int[] get() {
	        return new int[] { x, y };
	    }

	    public synchronized void set(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	}

	/*
	 * Vehicle Tracker that Safely Publishes Underlying State.
	 * PublishingVehicleTracker derives its thread safety from delegation to an
	 * underlying ConcurrentHashMap, but this time the contents of the Map are
	 * thread-safe mutable points rather than immutable ones.
	 */
	@ThreadSafe
	public class PublishingVehicleTracker {
		private final Map<String, SafePoint> locations;
		private final Map<String, SafePoint> unmodifiableMap;

		public PublishingVehicleTracker(Map<String, SafePoint> locations) {
			this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
			this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
		}

		public Map<String, SafePoint> getLocations() {
			return unmodifiableMap;
		}

		public SafePoint getLocation(String id) {
			return locations.get(id);
		}

		public void setLocation(String id, int x, int y) {
			if (!locations.containsKey(id))
				throw new IllegalArgumentException("invalid vehicle name: "
						+ id);
			locations.get(id).set(x, y);
		}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4.4. Adding Functionality to Existing Thread-safe Classes
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@ThreadSafe
	public class BetterVector<E> extends Vector<E> {
		public synchronized boolean putIfAbsent(E x) {
			boolean absent = !contains(x);
			if (absent)
				add(x);
			return absent;
		}
	}
	
	@NotThreadSafe
	public class ListHelper<E> {
	    public List<E> list = Collections.synchronizedList(new ArrayList<E>());
		//...
	    /*
	     * The problem is that it synchronizes on the wrong lock.
	     * */
	    public synchronized boolean putIfAbsent(E x) {
	        boolean absent = !list.contains(x);
	        if (absent)
	            list.add(x);
	        return absent;
	    }
	}

	/* 4.15. Implementing Put-if-absent with Client-side Locking. */
	@ThreadSafe
	public class ListHelper2<E> {
	    public List<E> list = Collections.synchronizedList(new ArrayList<E>());
//	    ...
	    public boolean putIfAbsent(E x) {
	        synchronized (list)  {
	            boolean absent = !list.contains(x);
	            if (absent)
	                list.add(x);
	            return absent;
	        }
	    }
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 4.4.2. Composition
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/*
	 * 4.16. Implementing Put-if-absent Using Composition. ImprovedList adds an
	 * additional level of locking using its own intrinsic lock. It does not
	 * care whether the underlying List is thread-safe, because it provides its
	 * own consistent locking that provides thread safety even if the List is
	 * not thread-safe or changes its locking implementation.
	 */
	@ThreadSafe
	public abstract class ImprovedList<T> implements List<T> {
	    private final List<T> list;

	    public ImprovedList(List<T> list) { this.list = list; }

	    public synchronized boolean putIfAbsent(T x) {
	        boolean contains = list.contains(x);
	        if (contains)
	            list.add(x);
	        return !contains;
	    }

	    public synchronized void clear() { list.clear(); }
	    // ... similarly delegate other List methods
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
