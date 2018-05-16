package kdtree;

import java.util.Arrays;
import java.util.Comparator;

import stdlib.StdDraw;
import stdlib.StdRandom;

/**
 *  The {@code Point2D} class is an immutable data type to encapsulate a
 *  two-dimensional point with real-value coordinates.
 *  <p>
 *  Note: in order to deal with the difference behavior of double and 
 *  Double with respect to -0.0 and +0.0, the Point2D constructor converts
 *  any coordinates that are -0.0 to +0.0.
 *  <p>
 *  @author chris
 */

public final class Point2D implements Comparable<Point2D>{
	/**
	 *  Compare two points by <em>x</em>-coordinate
	 */
	public static final Comparator<Point2D> XORDER = new XOrder();
	
	/**
	 *  Compare two points by <em>y</em>-coordinate
	 */
	public static final Comparator<Point2D> YORDER = new YOrder();
	
	/**
	 *  Compare two points by radius
	 */
	public static final Comparator<Point2D> RORDER = new ROrder();
	
	private final double x, y;
	
	public Point2D(double x, double y) {
		if (Double.isInfinite(x) || Double.isInfinite(y))
			throw new IllegalArgumentException("Coordinates must be finite. ");
		if (Double.isNaN(x) || Double.isNaN(y))
			throw new IllegalArgumentException("Coordinates must not be NaN. ");
		if (x == 0.0) 	this.x = 0.0;
		else 			this.x = x;
		if (y == 0.0) 	this.y = 0.0;
		else 			this.y = y;		
	}
	
	/**Returns x-coordinate
	 * @return <em>x</em> coordinate
	 */
	public double x() {
		return this.x;
	}
	
	/**Returns y-coordinate
	 * @return <em>y</em> coordinate
	 */
	public double y() {
		return this.y;
	}
	
	/**Returns radius
	 * @return <em>r</em>= sqrt(x*x + y*y)
	 */
	
	public double r() {
		return Math.sqrt(x * x + y * y);
	}
	
	/**Returns theta
	 * @return angle of the point(in radians) in polar coordinates 	 
	 * */
	public double theta() {
		return Math.atan2(y, x);
	}
	
	private double angleTo(Point2D that) {
		double dx = that.x - this.x;
		double dy = that.y - this.y;
		return Math.atan2(dy, dx);
	}
	
	public static int ccw(Point2D a, Point2D b, Point2D c) {
		double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		if		 (area2 < 0) 	return -1;
		else if	 (area2 > 0) 	return 1;
		else 					return 0;
	}
	
	public static double area2(Point2D a, Point2D b, Point2D c) {
		return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
	}
	
	public double distanceTo(Point2D that) {
		double dx = that.x - this.x;
		double dy = that.y - this.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public double distanceSquaredTo(Point2D that) {
		double dx = that.x - this.x;
		double dy = that.y - this.y;
		return dx * dx + dy * dy;
	}
	

	public int compareTo(Point2D that) {
	    if (this.y < that.y) return -1;
	    if (this.y > that.y) return +1;
	    if (this.x < that.x) return -1;
	    if (this.x > that.x) return +1;
	    return 0;
	}

	/**
	 * Compares two points by polar angle (between 0 and 2&pi;) with respect to this point.
	 *
	 * @return the comparator
	 */
	public Comparator<Point2D> polarOrder() {
	    return new PolarOrder();
	}

	/**
	 * Compares two points by atan2() angle (between –&pi; and &pi;) with respect to this point.
	 *
	 * @return the comparator
	 */
	
	public Comparator<Point2D> atan2Order() {
	    return new Atan2Order();
	}

	/**
	 * Compares two points by distance to this point.
	 *
	 * @return the comparator
	 */
	public Comparator<Point2D> distanceToOrder() {
	    return new DistanceToOrder();
	}

	// compare points according to their x-coordinate
	private static class XOrder implements Comparator<Point2D> {
	    public int compare(Point2D p, Point2D q) {
	        if (p.x < q.x) return -1;
	        if (p.x > q.x) return +1;
	        return 0;
	    }
	}

	// compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.y < q.y) return -1;
            if (p.y > q.y) return +1;
            return 0;
        }
    }

    // compare points according to their polar radius
    private static class ROrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double delta = (p.x*p.x + p.y*p.y) - (q.x*q.x + q.y*q.y);
            if (delta < 0) return -1;
            if (delta > 0) return +1;
            return 0;
        }
    }
	 
    // compare other points relative to atan2 angle (bewteen -pi/2 and pi/2) they make with this Point
    private class Atan2Order implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double angle1 = angleTo(q1);
            double angle2 = angleTo(q2);
            if      (angle1 < angle2) return -1;
            else if (angle1 > angle2) return +1;
            else                      return  0;
        }
    }

    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    private class PolarOrder implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double dx1 = q1.x - x;
            double dy1 = q1.y - y;
            double dx2 = q2.x - x;
            double dy2 = q2.y - y;
            if      (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
            else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
            else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                if      (dx1 >= 0 && dx2 < 0) return -1;
                else if (dx2 >= 0 && dx1 < 0) return +1;
                else                          return  0;
            }
            else return -ccw(Point2D.this, q1, q2);     // both above or below
            // Note: ccw() recomputes dx1, dy1, dx2, and dy2
        }
    }

    // compare points according to their distance to this point
    private class DistanceToOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double dist1 = distanceSquaredTo(p);
            double dist2 = distanceSquaredTo(q);
            if      (dist1 < dist2) return -1;
            else if (dist1 > dist2) return +1;
            else                    return  0;
        }
    }


    /**       
     * Compares this point to the specified point.
     *       
     * @param  other the other point
     * @return {@code true} if this point equals {@code other};
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Point2D that = (Point2D) other;
        return this.x == that.x && this.y == that.y;
    }

    /**
     * Return a string representation of this point.
     * @return a string representation of this point in the format (x, y)
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
	
    /**
     * Returns an integer hash code for this point.
     * @return an integer hash code for this point
     */
    @Override
    public int hashCode() {
        int hashX = ((Double) x).hashCode();
        int hashY = ((Double) y).hashCode();
        return 31*hashX + hashY;
    }

    /**
     * Plot this point using standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Plot a line from this point to that point using standard draw.
     * @param that the other point
     */
    public void drawTo(Point2D that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }


    /**
     * Unit tests the point data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int x0 = Integer.parseInt(args[0]);
        int y0 = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[2]);
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();

        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points[i] = new Point2D(x, y);
            points[i].draw();
        }

        // draw p = (x0, x1) in red
        Point2D p = new Point2D(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        p.draw();

        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Arrays.sort(points, p.polarOrder());
        for (int i = 0; i < n; i++) {
            p.drawTo(points[i]);
            StdDraw.show();
            StdDraw.pause(100);
        }
    }
}
