package kdtree;

import datastructure.Queue;

import datastructure.FixedMinPQ;

public class KdTreeST<Value> {
	
	private Node root;
	
	private final RectHV domain;
	
	public KdTreeST() {
		domain = new RectHV();
		root = null;
	}
	
	private class Node {		
		private Point2D point;
		private Value val;
		private Node left, right;
		private int axis;
		private int size;
		private RectHV rect;
		public Node(Point2D p, Value val, RectHV rect, int ax, int size) {
			this.point = p;
			this.val = val;
			this.axis = ax;
			this.size = size;
			this.rect = rect;
		}
		
		public RectHV leftRect() {
			if (axis == 0)
				return new RectHV(this.rect.xmin(), this.rect.ymin(), this.point.x(), this.rect.ymax());
			else 
				return new RectHV(this.rect.xmin(), this.rect.ymin(), this.rect.xmax(), this.point.y());
		}
		
		public RectHV rightRect() {
			if (axis == 0)
				return new RectHV(this.point.x(), this.rect.ymin(), this.rect.xmax(), this.rect.ymax());
			else 
				return new RectHV(this.rect.xmin(), this.point.y(), this.rect.xmax(), this.rect.ymax());
		}
		
		public double compare(Point2D p) {
			if (axis == 0)
				return point.x() - p.x();
			else 
				return point.y() - p.y();
		}
		
		public double compare(Node node, int ax) {
			if (ax == 0)
				return this.point.x() - node.point.x();
			else 
				return this.point.y() - node.point.y();
		}
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	private int size(Node node) {
		if (node == null)
			return 0;
		else return node.size;
	}
	
	public int size() {
		return size(root);
	}
	
	public void delete(Point2D p) {
		if (p == null) throw new IllegalArgumentException("calls delete with null point");
		root = delete(root, p);
	}
	
	private Queue<Node> getNodes(Node node) {
		Queue<Node> q = new Queue<Node>();
		getNodes(node, q);
		return q;
	}
	
	private void getNodes(Node node, Queue<Node> q) {
		if (node == null) return;
		q.enqueue(node);
		getNodes(node.left, q);
		getNodes(node.right, q);
	}
	
	private Node minNodes(Queue<Node> q, int axis) {
		Node n = q.peek();
		for (Node node : q) {
			if (node.compare(n, axis) < 0) {
				n = node;
			}
		}
		return n;
	}
	
	private Node delete(Node node, Point2D p) {
		if (node == null) return null;
		if (node.point.equals(p)) {
			Queue<Node> q = getNodes(node.right);
			Node t = node;
			Node rt = minNodes(q, node.axis);
			node = new Node(rt.point, rt.val, t.rect,  t.axis, 1);
			node.left = t.left;
			node.size = 1 + size(node.left);
			for (Node n : q) {
				if (!n.point.equals(node.point)) {
					put(node, n.point, n.val, node.rect, node.axis);
				}
			}
		}
		else if (node.compare(p) < 0) {
			node.right = delete(node.right, p);
		}
		else {
			node.left = delete(node.left, p);
		}
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}
	
	private Node put(Node node, Point2D p, Value val, RectHV rect, int axis) {
		if (node == null)
			return new Node(p, val, rect, axis, 1);
		if (node.compare(p) < 0) {
			node.right = put(node.right, p, val, node.rightRect(), 1 - axis);
		}
		else if (node.compare(p) > 0) {
			node.left = put(node.left, p, val, node.leftRect(),  1 - axis);
		}
		else {
			node.val = val;
		}
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}
	
	public void put(Point2D p, Value val) {
		if (p == null) throw new IllegalArgumentException("calls put with null point");
		if (val == null) {
			delete(p);
			return;
		}
		else {
			root = put(root, p, val, domain, 0);
		}
	}
	
	private Value get(Point2D p, Node node) {
		if (node == null) return null;
		if (node.compare(p) < 0) {
			return get(p, node.right);
		}
		else if (node.compare(p) > 0) {
			return get(p, node.left);
		}
		else return node.val;
	}
	
	public Value get(Point2D p) {
		return get(p, root);
	}
	
	public boolean contains(Point2D p) {
		return get(p, root) != null;
	}
	
	private void points(Node node, Queue<Point2D> ls) {
		if (node == null) return;
		ls.enqueue(node.point);
		points(node.left, ls);
		points(node.right, ls);
	}
	
	public Iterable<Point2D> points() {
		Queue<Point2D> ls = new Queue<Point2D>();
		points(root, ls);
		return ls;
	}
	
	private void range(Node node, RectHV rect, Queue<Point2D> q) {
		if (node == null) return;
		if (rect.contains(node.point)) {
			q.enqueue(node.point);
		}
		if (rect.intersects(node.leftRect())) {
			range(node.left, rect, q);
		}
		if (rect.intersects(node.rightRect())) {
			range(node.right, rect, q);
		}
		
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> q = new Queue<Point2D>();
		range(root, rect, q);
		return q;
	}
	
	private void getNearest(Node node, Point2D p, FixedMinPQ<Point2D> pq) {
		if (node == null) return;
		if (pq.notFull()) {
			pq.insert(node.point);
			getNearest(node.left, p, pq);
			getNearest(node.right, p, pq);
		}
		else {
			if (node.rect.distanceTo(p) > pq.max().distanceTo(p))
				return;
			else {
				pq.insert(node.point);
				getNearest(node.left, p, pq);
				getNearest(node.right, p, pq);
			}
		}
	}
	
	public Iterable<Point2D> nearest(Point2D p, int k) {
		if (k < 1) throw new IllegalArgumentException("k cannot be smaller than 1");
		FixedMinPQ<Point2D> pq = new FixedMinPQ<Point2D>(k, p.distanceToOrder());
		getNearest(root, p, pq);
		return pq;
	}
	
	public Point2D nearest(Point2D p) {
		return nearest(p, 1).iterator().next();
	}
	
	//public Iterable<Point2D> nearest(Point2D p, int k) {}
	
	public static void main(String[] args) {
		
	}
}
