package kdtree;

import stdlib.*;

public class NearestNeighborVisualizer {
	public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
 
        // initialize the two data structures with point from standard input
        PointST<Integer> brute = new PointST<Integer>();
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
            brute.put(p, i);
        }
 
        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true) {
 
            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);
 
            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.002);
            for (Point2D p : brute.points())
                p.draw();
 
            // draw in red the nearest neighbor according to the brute-force algorithm
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.RED);
            if (!brute.isEmpty())
                brute.nearest(query).draw();
            StdDraw.setPenRadius(0.01);
 
            // draw in blue the nearest neighbor according to the kd-tree algorithm
            StdDraw.setPenColor(StdDraw.BLUE);
            if (!kdtree.isEmpty()) {
            	Iterable<Point2D> neigh = kdtree.nearest(query, 10);
                for (Point2D p : neigh)
                	p.draw();
            }
            StdDraw.show();
            //StdDraw.pause(20);
        }
    }
}
