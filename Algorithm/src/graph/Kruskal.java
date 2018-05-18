package graph;

import datastructure.*;

import percolation.*;

public class Kruskal implements MST {
	
	private Queue<Edge> mst;
	
	private double weight;
	
	public Kruskal(EdgeWeightedGraph ewg) {
		mst = new Queue<Edge>();
		weight = 0.0;
		MinPQ<Edge> pq = new MinPQ<Edge>();
		for (Edge e : ewg.edges())
			pq.insert(e);
		QuickUnion qu = new QuickUnion(ewg.V());
		while(!pq.isEmpty() && mst.size() < ewg.V() - 1) {
			Edge e = pq.delMin();
			int v = e.either(); 
			int w = e.other(v);
			if (!qu.connected(v, w)) {
				qu.union(v, w);
				mst.enqueue(e);
				weight += e.weight();
			}
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
