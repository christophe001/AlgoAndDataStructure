package datastructure;

import java.util.Comparator;

public class MinPQ<Key extends Comparable<Key>> {
	
	private int size;
	private Key[] heap;
	private Comparator<Key> comparator;
	
	@SuppressWarnings("unchecked")
	public MinPQ() {
		size = 0;
		heap = (Key[]) new Comparable[1];
	}
	
	@SuppressWarnings("unchecked")
	public MinPQ(Key[] a) {
		if (a == null)
			throw new NullPointerException("argument cannot be null");
		size = a.length;
		heap = (Key[]) new Comparable[size + 1];
		for(int i = 1; i <= size; i++) 
			heap[i] = a[i-1];
	}
	
	@SuppressWarnings("unchecked")
	public MinPQ(int len) {
		size = 0;
		heap = (Key[]) new Comparable[len + 1];
	}
	
	private boolean less(int i, int j) {
		if (comparator == null)
			return heap[i].compareTo(heap[j]) < 0;
		else 
			return comparator.compare(heap[i], heap[j]) < 0;
	}
	
	@SuppressWarnings("unchecked")
	public MinPQ(int len, Comparator<Key> comp) {
		size = 0;
		heap = (Key[]) new Comparable[len + 1];
		this.comparator = comp;
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int n) {
		Key[] pq = (Key[]) new Comparable[n + 1];
		for(int i = 1; i <= size; i++)
			pq[i] = heap[i];
		heap = pq;
	}
	
	private void exch(int i, int j) {
		Key temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	private void swim(int k) {
		while (k > 1 && less(k, k/2)) {
			exch(k/2, k);
			k = k/2;
		}	
	}
	
	private void sink(int k) {
		while (2 * k <= size) {
			int j = 2 * k;
			if (j < size && less(j + 1, j)) j++;
			if (!less(j, k)) break;
			exch(k, j);
			k = j;
		}		
	}
	
	public void insert(Key v) {
		if (size == heap.length - 1)
			resize(heap.length * 2);
		heap[++size] = v;
		swim(size);
	}
	
	public Key delMin() {
		if (isEmpty())
			throw new NullPointerException("no more items in heap");
		Key item = heap[1];
		exch(1, size--);
		sink(1);
		heap[size + 1] = null;
		if ((size > 0) && (size <= heap.length / 4))
			resize(heap.length / 2 + 1);
		return item;	
	}
	
	public Key min() {
		return heap[1];
		
	}
	
	public int size() {
		return size;
	}
	
	
	public boolean isEmpty() {
		return size == 0;
	}
}
