package graph;

import datastructure.*;

import stdlib.*;

public class DepthFirstOrder {
	 private boolean[] marked;          // marked[v] = has v been marked in dfs?
	 private int[] pre;                 // pre[v]    = preorder  number of v
	 private int[] post;                // post[v]   = postorder number of v
	 private Queue<Integer> preorder;   // vertices in preorder   
	 private Queue<Integer> postorder;  // vertices in postorder
	 private int preCounter;            // counter or preorder numbering   
	 private int postCounter;           // counter for postorder numbering


	    
	 public DepthFirstOrder(Digraph G) {
		 pre    = new int[G.V()];
		 post   = new int[G.V()];	     
		 postorder = new Queue<Integer>();	     
		 preorder  = new Queue<Integer>();
		 marked    = new boolean[G.V()];
		 for (int v = 0; v < G.V(); v++)
			 if (!marked[v]) dfs(G, v);
		 assert check();       
	 }   
	 
	 public DepthFirstOrder(EdgeWeightedDigraph G) {        
		 pre    = new int[G.V()];	        
		 post   = new int[G.V()];	        
		 postorder = new Queue<Integer>();
		 preorder  = new Queue<Integer>();
		 marked    = new boolean[G.V()];
		 for (int v = 0; v < G.V(); v++)
			 if (!marked[v]) dfs(G, v);
	}

	    // run DFS in digraph G from vertex v and compute preorder/postorder
	    
	 private void dfs(Digraph G, int v) {
		 marked[v] = true;	        
		 pre[v] = preCounter++;	        
		 preorder.enqueue(v);	        
		 for (int w : G.adj(v)) {	            
			 if (!marked[w]) {	                
				 dfs(G, w);
	         }	        
		 }	        
		 postorder.enqueue(v);	        
		 post[v] = postCounter++;	   
	 }

	    // run DFS in edge-weighted digraph G from vertex v and compute preorder/postorder	    
	 private void dfs(EdgeWeightedDigraph G, int v) {	        
		 marked[v] = true;	        
		 pre[v] = preCounter++;	        
		 preorder.enqueue(v);	        	        	        
		 for (DirectedEdge e : G.adj(v)) {	            
			 int w = e.to();	           
			 if (!marked[w]) {	                
				 dfs(G, w);	            
			 }	        
		 }	        
		 postorder.enqueue(v);	        
		 post[v] = postCounter++;	    
	 }
	 
	 public int pre(int v) {	        
		 validateVertex(v);	        
		 return pre[v];	    
	 }
	 	    
	 public int post(int v) {	        
		 validateVertex(v);	        
		 return post[v];	    
	 }
	    
	 public Iterable<Integer> post() {        
		 return postorder;    
	 }
	    
	 public Iterable<Integer> pre() {        
		 return preorder;    
	 }
    
	 public Iterable<Integer> reversePost() {	        
		 Stack<Integer> reverse = new Stack<Integer>();        
		 for (int v : postorder)            
			 reverse.push(v);	        
		 return reverse;    
	 }
	    // check that pre() and post() are consistent with pre(v) and post(v)
	    
	 private boolean check() {
        // check that post(v) is consistent with post()        
		 int r = 0;     
		 for (int v : post()) {	            
			 if (post(v) != r) {	                
				 StdOut.println("post(v) and post() inconsistent");	                
				 return false;	           
			 }           
			 r++;        
		 }
	        // check that pre(v) is consistent with pre(        
		 r = 0;	        
		 for (int v : pre()) {            
			 if (pre(v) != r) {	               
				 StdOut.println("pre(v) and pre() inconsistent");	               
				 return false;	            
			 }            
			 r++;	        
		 }
		 return true;	    
	 }

	    // throw an IllegalArgumentException unless {@code 0 <= v < V}
	 private void validateVertex(int v) {
		 int V = marked.length;
		 if (v < 0 || v >= V)
			 throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
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
