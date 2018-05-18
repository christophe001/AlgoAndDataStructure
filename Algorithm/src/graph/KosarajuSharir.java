package graph;

public class KosarajuSharir {
	private boolean[] visited;
	private int[] id;
	private int count;
	
	public KosarajuSharir(Digraph digraph) {
		visited = new boolean[digraph.V()];
		id = new int[digraph.V()];
		count = 0;
		DepthFirstOrder dfo = new DepthFirstOrder(digraph.reverse());
		for (int v : dfo.reversePost()) {
			if (!visited[v])  {
				dfs(digraph, v);
				count++;
			}
		}
	}
	
	private void dfs(Digraph digraph, int v) {
		visited[v] = true;
		id[v] = count;
		for (int w : digraph.adj(v)) {
			if (!visited[w]) {
				dfs(digraph, w);
			}
		}
	}
	
	public boolean connected(int v, int w) {
		return id[v] == id[w];
	}
	
	public int components() {
		return count;
	}
}
