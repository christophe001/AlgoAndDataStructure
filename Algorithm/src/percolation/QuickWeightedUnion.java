package percolation;
import stdlib.StdIn;
import stdlib.StdOut;

public class QuickWeightedUnion {
	private int[] parent;
	private int[] size;
	private int count;
	
	public QuickWeightedUnion(int N) {
		parent = new int[N];
		size = new int[N];
		count = N;
		for(int i = 0; i < N; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}
	
	public int count() {
		return count;
	}
	
	private void validate(int p) {
		int n = parent.length;
		if(p < 0 || p > parent.length)
			throw new IllegalArgumentException(
					"index " + p + " is not between 0 and " + (n-1)
					);
	}
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public int find(int n) {
		validate(n);
		while(n != parent[n]) 
			n = parent[n];
		return n;
	}
	
	public void union(int p, int q) {
		validate(p);
		validate(q);
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		if (size[i] < size[j]) {
			parent[i] = j;
			size[j] += size[i];
			count--;
		}
		else {
			parent[j] = i;
			size[i] += size[j];
			count--;
		}
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		QuickWeightedUnion qwu = new QuickWeightedUnion(n);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (qwu.connected(p, q)) continue;
			qwu.union(p, q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(qwu.count() + " components");
	}
}
