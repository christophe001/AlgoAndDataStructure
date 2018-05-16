package percolation;

import java.awt.Font;

import stdlib.*;
public class PercolationVisualizer {
	private static final int DELAY = 100;
	public static void draw(Percolation pc) {
		int n = pc.size();
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setXscale(-0.05 * n, 1.05 * n);
		StdDraw.setYscale(-0.05 * n, 1.05 * n);
		StdDraw.filledSquare(n/2.0, n/2.0, n/2.0);
		
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (pc.isFull(row, col))
					StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				else if (pc.isOpen(row, col))
					StdDraw.setPenColor(StdDraw.WHITE);
				else StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.filledSquare(col + 0.5, n - row - 0.5, 0.45);
			}
		}
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.text(0.25*n, -0.025*n, pc.numberOfOpenSites() + " open sites");
	    if (pc.percolates()) 
	        StdDraw.text(0.75*n, -0.025*n, "percolates");
	    else                          
	        StdDraw.text(0.75*n, -0.025*n, "does not percolate");
	}
	
	private static void simulateFromFile(String filename) {
		In in = new In(filename);
		int n = in.readInt();
		Percolation pc = new Percolation(n);
		
		StdDraw.enableDoubleBuffering();
		
		draw(pc);
		StdDraw.show();
		StdDraw.pause(DELAY);
		
		while (!in.isEmpty()) {
			int row = in.readInt();
			int col = in.readInt();
			pc.open(row, col);
			draw(pc);
			StdDraw.show();
			StdDraw.pause(DELAY);
		}
	}
	
	public static void main(String[] args) {
		String filename = args[0];
		simulateFromFile(filename);
	}
}
