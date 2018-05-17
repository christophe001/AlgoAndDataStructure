package graph;

import datastructure.*;

public class DepthFirstOrder {

	
	private Stack<Integer> postOrder;
	private boolean[] visited;
	
	public DepthFirstOrder(Digraph digraph) {
		this.visited = new boolean[digraph.V()];
		this.postOrder = new Stack<Integer>();
		for (int v = 0; v < digraph.V(); v++) {
			if (!visited[v]) dfs(digraph, v);
		}
	}
	
	public void dfs(Digraph digraph, int v) {
		visited[v] = true;
		for (int w : digraph.adj(v)) {
			if (!visited[w]) dfs(digraph, w);
		}
		postOrder.push(v);
	}
	
	public Iterable<Integer> reversePost() { 
		return postOrder; 
	}

	public static void main(String[] args) {
		Digraph dg = new Digraph(7);
		dg.addEdge(0, 1);
		dg.addEdge(0, 2);
		dg.addEdge(0, 5);
		dg.addEdge(1, 4);
		dg.addEdge(3, 2);
		dg.addEdge(3, 4);
		dg.addEdge(3, 5);
		dg.addEdge(3, 6);
		dg.addEdge(5, 2);
		dg.addEdge(6, 0);
		dg.addEdge(6, 4);
		DepthFirstOrder tp1 = new DepthFirstOrder(dg);
		System.out.println(tp1.reversePost().toString());
		Digraph g = new Digraph(13);
		g.addEdge(0, 1);
		g.addEdge(0, 5);
		g.addEdge(2, 0);
		g.addEdge(2, 3);
		g.addEdge(3, 2);
		g.addEdge(3, 5);
		g.addEdge(4, 2);
		g.addEdge(4, 3);
		g.addEdge(5, 4);
		g.addEdge(6, 0);
		g.addEdge(6, 4);
		g.addEdge(6, 8);
		g.addEdge(6, 9);
		g.addEdge(7, 6);
		g.addEdge(7, 9);
		g.addEdge(8, 6);
		g.addEdge(9, 10);
		g.addEdge(9, 11);
		g.addEdge(10, 12);
		g.addEdge(11, 4);
		g.addEdge(11, 12);
		g.addEdge(12, 9);
		System.out.println(g.toString());
		DirectedCycle dc = new DirectedCycle(g);
		if (dc.hasCycle()) {
			System.out.println("has cycle: ");
			System.out.println(dc.cycle().toString());
		}
		Digraph gr = g.reverse();
		DepthFirstOrder tp = new DepthFirstOrder(gr);
		System.out.println(tp.reversePost().toString());
		
	}
}
