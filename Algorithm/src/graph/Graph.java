package graph;

import datastructure.*;

import java.util.NoSuchElementException;

import stdlib.*;

public class Graph {
	private final int V;
	private int E;
	private Bag<Integer>[] adj;
	
	@SuppressWarnings("unchecked")
	public Graph(int v) {
		if (v < 0)
			throw new IllegalArgumentException("Invalid vertices count!");
		this.V = v;
		this.E = 0;
		adj = (Bag<Integer>[]) new Bag[v];
		for (int i = 0; i < v; i++) {
			adj[i] = new Bag<Integer>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Graph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w); 
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
		
	}

    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }
	
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		E++;
		adj[v].add(w);
		adj[w].add(v);
	}

	public int V() {
		return V;
	}
	
	public int E() {
		return E;
	}
	
	private void validateVertex(int v) {
		if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));	
	}
	
	public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }
	
	public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }
	
	public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
