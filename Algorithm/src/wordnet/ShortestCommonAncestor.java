package wordnet;

import stdlib.*;

import graph.*;

public class ShortestCommonAncestor {
	
	private DirectedCycle dc;
	
	private final Digraph digraph;
// constructor takes a rooted DAG as argument
	
	public ShortestCommonAncestor(Digraph G) {
		digraph = new Digraph(G);
		dc = new DirectedCycle(G);
		if (dc.hasCycle())
			throw new IllegalArgumentException("Digaph has cycle");
	}


	
	private void checkInput(int i) {
		if (i < 0 || i >= digraph.V())
			throw new IllegalArgumentException("vertex " + i + " is not between 0 and " + (digraph.V()-1));
	}
	
	private void checkInput(Iterable<Integer> vertices) {
		if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = digraph.V();
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
	}
	   // length of shortest ancestral path between v and w
	public int length(int v, int w) {
		checkInput(v);
		checkInput(w);
		BreadthFirstDirectedPaths vb = new BreadthFirstDirectedPaths(digraph, v);
		BreadthFirstDirectedPaths wb = new BreadthFirstDirectedPaths(digraph, w);
		return sal(vb,wb)[1];	
	}
	   // a shortest common ancestor of vertices v and w
	public int ancestor(int v, int w) {
		checkInput(v);
		checkInput(w);	
		BreadthFirstDirectedPaths vb = new BreadthFirstDirectedPaths(digraph, v);
		BreadthFirstDirectedPaths wb = new BreadthFirstDirectedPaths(digraph, w);
		return sal(vb, wb)[0];
	}
	
	private int[] sal(BreadthFirstDirectedPaths a, BreadthFirstDirectedPaths b) {
		int len = Integer.MAX_VALUE;
		int id = -1;
		for (int v = 0; v < digraph.V(); v++) {
			if (a.hasPathTo(v) && b.hasPathTo(v)) {
				int l = a.distTo(v) + b.distTo(v);
				if (l < len) {
					len = l;
					id = v;
				}
			}
		}
		int[] res = new int[2];
		res[0] = id;
		res[1] = len;
		return res;
	}
	
	   // length of shortest ancestral path of vertex subsets A and B
	public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
		checkInput(subsetA);
		checkInput(subsetB);
		BreadthFirstDirectedPaths sab = new BreadthFirstDirectedPaths(digraph, subsetA);
		BreadthFirstDirectedPaths sbb = new BreadthFirstDirectedPaths(digraph, subsetB);
		return sal(sab, sbb)[1];
	}

	   // a shortest common ancestor of vertex subsets A and B
	public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
		checkInput(subsetA);
		checkInput(subsetB);
		BreadthFirstDirectedPaths sab = new BreadthFirstDirectedPaths(digraph, subsetA);
		BreadthFirstDirectedPaths sbb = new BreadthFirstDirectedPaths(digraph, subsetB);
		return sal(sab, sbb)[0];
	}

	   // unit testing (required)
	public static void main(String[] args) {}
}
