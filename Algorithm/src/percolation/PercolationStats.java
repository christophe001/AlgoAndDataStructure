package percolation;

import stdlib.*;

public class PercolationStats {
	private int times;
	private int size;
	private int total_sites;
	private int[] percolation;
	private double[] pcs;
	public PercolationStats(int N, int T) {
		times = T;
		size = N;
		total_sites = N * N;
		percolation = new int[T];
		pcs = new double[T];
		for (int i = 0; i < T; i++) {
			Percolation pc = new Percolation(N);
			while (!pc.percolates()) {
				int row = StdRandom.uniform(N);
				int col = StdRandom.uniform(N);
				pc.open(row, col);
			}
			percolation[i] = pc.numberOfOpenSites();
			pcs[i] = (double)percolation[i] /total_sites;
		}
	}
	
	public int size() {
		return size;
	}
	
	public double mean() {
		return StdStats.mean(pcs);
	}
	
	public double stddev() {
		return StdStats.stddev(pcs);
		
		
	}
	
	public double confidenceLow() {
		return mean() - 1.96 * (stddev()/Math.sqrt(times));
	}
	
	public double confidenceHigh() {
		return mean() + 1.96 * (stddev()/Math.sqrt(times));	
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		int t = StdIn.readInt();
		Stopwatch timer1 = new Stopwatch();
		PercolationStats pcs = new PercolationStats(n, t);
		double time = timer1.elapsedTime();
		StdOut.println(
				"Example values after creating PercolationStats("
				+ n + ", " + t + ")");
		StdOut.println("total time:       = " + time + " seconds");
		StdOut.println("mean()            = " + pcs.mean());
		StdOut.println("stddev()          = " + pcs.stddev());
		StdOut.println("confidenceLow()   = " + pcs.confidenceLow());
		StdOut.println("confidenceHigh()  = " + pcs.confidenceHigh());
		Percolation expl = new Percolation(n);
		
		while (!expl.percolates()) {
			int row = StdRandom.uniform(n);
			int col = StdRandom.uniform(n);
			expl.open(row, col);
		}
		PercolationVisualizer.draw(expl);
		StdDraw.show();
	}
}
