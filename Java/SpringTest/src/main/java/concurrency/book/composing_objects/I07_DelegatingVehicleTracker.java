package concurrency.book.composing_objects;

import java.util.*;
import java.util.concurrent.*;
import java.awt.*;
import java.awt.Point;

import net.jcip.annotations.*;

/**
 * DelegatingVehicleTracker
 * 
 * Delegating thread safety to a ConcurrentHashMap
 *
 * the delegating version returns an unmodifiable but “live” view of the vehicle
 * locations. This means that if thread A calls getLocations and thread B later
 * modifies the location of some of the points, those changes are reflected in
 * the Map returned to thread A. As we remarked earlier, this can be a benefit
 * (more up-to-date data) or a liability (potentially inconsistent view of the
 * fleet), depending on your requirements.
 * 
 */
@ThreadSafe
public class I07_DelegatingVehicleTracker {
	private final ConcurrentMap<String, Point> locations;
	private final Map<String, Point> unmodifiableMap;

	public I07_DelegatingVehicleTracker(Map<String, Point> points) {
		locations = new ConcurrentHashMap<String, Point>(points);
		unmodifiableMap = Collections.unmodifiableMap(locations);
		/*
		 * Collections.unmodifiableMap() : Returns an unmodifiable view of the
		 * specified map. This method allows modules to provide users with
		 * "read-only" access to internal maps. Query operations on the returned
		 * map "read through" to the specified map, and attempts to modify the
		 * returned map, whether direct or via its collection views, result in
		 * an UnsupportedOperationException.
		 */
	}

	public Map<String, Point> getLocations() {
		return unmodifiableMap;
	}

	public Point getLocation(String id) {
		return locations.get(id);
	}

	public void setLocation(String id, int x, int y) {
		if (locations.replace(id, new Point(x, y)) == null)
			throw new IllegalArgumentException("invalid vehicle name: " + id);
	}

	// Alternate version of getLocations (Listing 4.8)
	/*
	 * If an unchanging view of the fleet is required, getLocations could
	 * instead return a shallow copy of the locations map. Since the contents of
	 * the Map are immutable, only the structure of the Map, not the contents,
	 * must be copied, as shown in Listing 4.8 (which returns a plain HashMap,
	 * since getLocations did not promise to return a thread-safe Map)
	 */
	public Map<String, Point> getLocationsAsStatic() {
		return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
	}
}