package graph;

import datastructure.*;

import stdlib.*;

public class Dijkstra implements SP {
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	private int V;
	private IndexMinPQ<Double> pq;
	
	public Dijkstra(EdgeWeightedDigraph digraph, int s) {
        for (DirectedEdge e :digraph.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
		V = digraph.V();
		distTo = new double[digraph.V()];
		edgeTo = new DirectedEdge[digraph.V()];
		pq= new IndexMinPQ<Double>(V);
		for (int i = 0; i < digraph.V(); i++)
			distTo[i] = Double.POSITIVE_INFINITY;
		validateVertex(s);
		distTo[s] = 0;
		pq.insert(s, 0.0);
		while(!pq.isEmpty()) {
			int v = pq.delMin();
			for(DirectedEdge e : digraph.adj(v)) {
				relax(e);
			}
		}
	}
	
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }
    
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }
    
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
    
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
	
	private void validateVertex(int i) {
		if (i < 0 || i >= V)
			throw new IllegalArgumentException("index out of bount");
	}
	
	public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[1]);

        // compute shortest paths
        Dijkstra sp = new Dijkstra(G, s);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }

	
}
