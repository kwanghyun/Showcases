package concurrency.book.composing_objects;

import java.util.*;

import net.jcip.annotations.*;

/**
 * MonitorVehicleTracker
 * <p/>
 * Monitor-based vehicle tracker implementation Since the view thread and the
 * updater threads will access the data model concurrently, it must be
 * thread-safe.
 *
 * Even though MutablePoint is not thread-safe, the tracker class is. Neither
 * the map nor any of the mutable points it contains is ever published. When we
 * need to a return vehicle locations to callers, the appropriate values are
 * copied using either the MutablePoint copy constructor or deepCopy, which
 * creates a new Map whose values are copies of the keys and values from the old
 * Map.
 * 
 * Note that deepCopy can't just wrap the Map with an unmodifiableMap, because
 * that protects only the collection from modification; it does not prevent
 * callers from modifying the mutable objects stored in it. For the same reason,
 * populating the HashMap in deepCopy via a copy constructor wouldn't work
 * either, because only the references to the points would be copied, not the
 * point objects themselves.
 * 
 * This implementation maintains thread safety in part by copying mutable data
 * before returning it to the client. This is usually not a performance issue,
 * but could become one if the set of vehicles is very large.[4] Another
 * consequence of copying the data on each call to getLocation is that the
 * contents of the returned collection do not change even if the underlying
 * locations change. Whether this is good or bad depends on your requirements.
 * It could be a benefit if there are internal consistency requirements on the
 * location set, in which case returning a consistent snapshot is critical, or a
 * drawback if callers require up-to-date information for each vehicle and
 * therefore need to refresh their snapshot more often.
 * 
 * Because deepCopy is called from a synchronized method, the tracker's
 * intrinsic lock is held for the duration of what might be a long-running copy
 * operation, and this could degrade the responsiveness of the user interface
 * when many vehicles are being tracked.
 */
@ThreadSafe
public class I04_MonitorVehicleTracker {
	@GuardedBy("this")
	private final Map<String, I05_MutablePoint> locations;

	public I04_MonitorVehicleTracker(Map<String, I05_MutablePoint> locations) {
		this.locations = deepCopy(locations);
	}

	public synchronized Map<String, I05_MutablePoint> getLocations() {
		return deepCopy(locations);
	}

	public synchronized I05_MutablePoint getLocation(String id) {
		I05_MutablePoint loc = locations.get(id);
		return loc == null ? null : new I05_MutablePoint(loc);
	}

	public synchronized void setLocation(String id, int x, int y) {
		I05_MutablePoint loc = locations.get(id);
		if (loc == null)
			throw new IllegalArgumentException("No such ID: " + id);
		loc.x = x;
		loc.y = y;
	}

	private static Map<String, I05_MutablePoint> deepCopy(Map<String, I05_MutablePoint> m) {
		Map<String, I05_MutablePoint> result = new HashMap<String, I05_MutablePoint>();

		for (String id : m.keySet())
			result.put(id, new I05_MutablePoint(m.get(id)));

		/*
		 * Collections.unmodifiableMap()
		 * 
		 * Returns an unmodifiable view of the specified map. This method allows
		 * modules to provide users with "read-only" access to internal maps.
		 * Query operations on the returned map "read through" to the specified
		 * map, and attempts to modify the returned map, whether direct or via
		 * its collection views, result in an UnsupportedOperationException.
		 */
		return Collections.unmodifiableMap(result);
	}
}