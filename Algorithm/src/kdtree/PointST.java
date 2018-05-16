package kdtree;
import datastructure.Stack;

public class PointST<Value> extends ST<Point2D, Value> {
	
	public PointST() {
		super();
	}
	
	public Iterable<Point2D> points() {
		return keys();
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) 
			throw new IllegalArgumentException("calls range() with null rect");
		Stack<Point2D> contains = new Stack<Point2D>();
		for (Point2D p : points()) {
			if (rect.contains(p))
				contains.push(p);
		}
		return contains;
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException("calls nearest() with null point");
		double dis = Double.MAX_VALUE;
		Point2D nearest = p;
		for (Point2D pt : points()) {
			if (p.distanceTo(pt) < dis) {
				dis = p.distanceTo(pt);
				nearest = pt;
			}
		}
		return nearest;
	}
	
	public static void main(String[] args) {
		
	}
}
