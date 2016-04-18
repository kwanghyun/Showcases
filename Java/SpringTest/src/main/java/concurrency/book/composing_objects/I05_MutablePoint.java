package concurrency.book.composing_objects;

import net.jcip.annotations.*;

/**
 * MutablePoint
 * <p/>
 * Mutable Point class similar to java.awt.Point
 *
 */
@NotThreadSafe
public class I05_MutablePoint {
    public int x, y;

    public I05_MutablePoint() {
        x = 0;
        y = 0;
    }

    public I05_MutablePoint(I05_MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }
}
