package graph;

import datastructure.*;

public class Prim implements MST {
	
	private boolean[] visited;
	private Queue<Edge> mst;
	private MinPQ<Edge> pq;
	private double weight;
	
	public Prim(EdgeWeightedGraph ewg) {
		visited = new boolean[ewg.V()];
		mst = new Queue<Edge>();
		pq = new MinPQ<Edge>();
		weight = 0.0;
		visit(ewg, 0);
		while(!pq.isEmpty() && mst.size() < ewg.V() - 1) {
			Edge e = pq.delMin();
			int v = e.either(), w = e.other(v);
			if (visited[v] && visited[w]) continue;
			mst.enqueue(e);
			weight += e.weight();
			if (!visited[v]) visit(ewg, v);
			if (!visited[w]) visit(ewg, w);
		}

	}
	
	private void visit(EdgeWeightedGraph ewg, int v) {
		visited[v] = true;
		for (Edge e : ewg.adj(v)) {
			if (!visited[e.other(v)])
				pq.insert(e);
		}
	}
	
	@Override
	public Iterable<Edge> edges() {
		// TODO Auto-generated method stub
		return mst;
	}

	@Override
	public double weight() {
		// TODO Auto-generated method stub
		return weight;
	}

}
