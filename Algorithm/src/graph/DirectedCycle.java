package graph;

import datastructure.Stack;

public class DirectedCycle {

	private boolean[] visited;
	private boolean[] onstack;
	private Stack<Integer> cycle;
	private int[] edgeto;
	
	public DirectedCycle(Digraph digraph) {
		visited = new boolean[digraph.V()];
		onstack = new boolean[digraph.V()];
		edgeto = new int[digraph.V()];
		for (int v = 0; v < digraph.V(); v++ ) {
			if (!visited[v]) dfs(digraph, v);
		}
	}
	
	private void dfs(Digraph digraph, int v) {
		onstack[v] = true;
		visited[v] = true;
		for (int w : digraph.adj(v)) {
			if (this.hasCycle()) return;
			else if (!visited[w]) {
				edgeto[w] = v;
				dfs(digraph, w);
			}
			else if (onstack[w]) {
				cycle = new Stack<Integer>();
				for (int x = v; x!= w; x = edgeto[x])
					cycle.push(x);
				cycle.push(w);
				cycle.push(v);
			}
		}
		onstack[v] = false;
	}
	
	public boolean hasCycle() {
		return cycle != null;
	}
	
	public Iterable<Integer> cycle() {
		return cycle;
	}
	
}
