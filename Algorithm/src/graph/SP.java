package graph;

public interface SP {
	public double distTo(int v);
	
	public boolean hasPathTo(int v);
	
	Iterable <DirectedEdge> pathTo(int v);
}
