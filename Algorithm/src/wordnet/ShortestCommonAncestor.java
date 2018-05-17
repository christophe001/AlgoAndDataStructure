package wordnet;

import stdlib.*;

import graph.*;

public class ShortestCommonAncestor {
	
	private DirectedCycle dc;
	
	private Digraph digraph;
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
	   // length of shortest ancestral path between v and w
	public int length(int v, int w) {
		checkInput(v);
		checkInput(w);
		
	}

	   // a shortest common ancestor of vertices v and w
	public int ancestor(int v, int w) {
		checkInput(v);
		checkInput(w);	
		
	}

	   // length of shortest ancestral path of vertex subsets A and B
	public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {}

	   // a shortest common ancestor of vertex subsets A and B
	public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {}

	   // unit testing (required)
	public static void main(String[] args) {}
}
