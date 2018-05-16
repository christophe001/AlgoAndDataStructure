package percolation;
import stdlib.StdIn;
import stdlib.StdOut;

public class QuickUnion {
	private int[] parent;
	private int count;
	
	private void validate(int p) {
		int n = parent.length;
		if (p < 0 || p > n) {
			throw new IllegalArgumentException(
					"index " + p + " is not between 0 and " + (n-1)
					);
		}
	}
	
	public QuickUnion(int N) {
		parent = new int[N];
		count = N;
		for (int i = 0; i < N; i++)
			parent[i] = i;
	}
	
	public int count(){
		return count;
	}
	
	public int find(int n) {
		validate(n);
		while (parent[n] != n)
			n = parent[n];
		return n;
	}
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public void union(int p, int q) {
		validate(p);
		validate(q);
		int pid = find(p);
		int qid = find(q);
		if (pid == qid) return;
		else {
			parent[pid] = qid;
			count--;
		}
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		QuickUnion qu = new QuickUnion(n);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (qu.connected(p, q)) continue;
			qu.union(p, q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(qu.count() + " components");
	}
}
