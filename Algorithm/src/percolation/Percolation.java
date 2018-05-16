package percolation;

import stdlib.*;

public class Percolation {
	private QuickWeightedUnion qwu;
	private int numOpenSites;
	private boolean[] states;
	private int size;
	
	public int size() {
		return size;
	}
	
	public Percolation(int N) {
		size = N;
		numOpenSites = 0;
		qwu = new QuickWeightedUnion(N * N + 1);
		states = new boolean[N * N];
		for (int i = 1; i <= N; i++) {
			qwu.union(0, i);
		}
	}
	
	public void open(int row, int col) {
		if (isOpen(row, col))
			return;
		else {
			int i = row * size + col + 1;
			states[i - 1] = true;
			numOpenSites++;
			if (row > 0) {
				if (isOpen(row - 1, col))
					qwu.union(i, i - size);
			}
			if (row < size - 1) {
				if (isOpen(row + 1, col))
					qwu.union(i, i + size);
			}					
			if (col > 0) {
				if (isOpen(row, col - 1))
					qwu.union(i, i -1);
			}
			if (col < size - 1) {
				if (isOpen(row, col + 1))
					qwu.union(i, i+1);
			}
		}
	}
	
	private void validate(int p) {
		if (p < 0 || p >= size)
			throw new IllegalArgumentException(
					"index " + p + "is not between 0 and " + size);
	}
	
	public boolean isOpen(int row, int col) {
		validate(row);
		validate(col);
		int i = row * size + col;
		return states[i];
	}
	
	public boolean isFull(int row, int col) {
		validate(row);
		validate(col);
		int i = row * size + col + 1;
		return qwu.connected(0, i) && isOpen(row, col);
	}
	
	public int numberOfOpenSites() {
		return numOpenSites;
	}
	
	public boolean percolates() {
		for (int i = 0; i < size; i++) {
			if (isFull(size - 1, i))
				return true;
		}
		return false;	
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		Percolation pc = new Percolation(n);
		while (!(StdIn.isEmpty())) {
			int row = StdIn.readInt();
			int col = StdIn.readInt();
			pc.open(row, col);
		}
		PercolationVisualizer.draw(pc);
		StdDraw.show();
	}
}
