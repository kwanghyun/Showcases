package concurrency.book.composing_objects;

import net.jcip.annotations.*;

/**
 * Point
 * <p/>
 * Immutable Point class used by DelegatingVehicleTracker
 *
 * Point is thread-safe because it is immutable. Immutable values can be freely
 * shared and published, so we no longer need to copy the locations when
 * returning them.
 */
@Immutable
public class Point {
	public final int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}