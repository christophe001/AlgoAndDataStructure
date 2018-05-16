package percolation;

import stdlib.StdIn;
import stdlib.StdOut;

public class QuickFind {
	private int[] id;
	private int count;
	
	public QuickFind(int N) {
		id = new int[N];
		count = N;
		for (int i = 0; i < N; i++)
			id[i] = i;
	}
	
	public int count() {
		return count;
	}
	
	private void validate(int p) {
		int n = id.length;
		if (p < 0 || p > n) {
			throw new IllegalArgumentException(
					"index " + p + " is not between 0 and " + (n-1)
					);
		}
	}
	
	public int find(int n) {
		validate(n);
		return id[n];
	}
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public void union(int p, int q) {
		validate(p);
		validate(q);
		int pid = id[p];
		int qid = id[q];
		for(int i = 0; i < id.length; i++)
			if(id[i]==pid) id[i] = qid;
		count--;
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		QuickFind qf = new QuickFind(n);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (qf.connected(p, q)) continue;
			qf.union(p, q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(qf.count() + " components");
	}
	
}
