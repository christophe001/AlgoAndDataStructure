package datastructure;

import java.util.Comparator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedMinPQ<Key extends Comparable<Key>> implements Iterable<Key> {
	
	private int size;
	private int cnt;
	private Key[] heap;
	private Comparator<Key> comparator;
	
	public Iterator<Key> iterator() {
		return new PqIterator();
	}
	
	private class PqIterator implements Iterator<Key> {
		private int current = 1;
		
		public boolean hasNext() {
			return current <= cnt;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Key next() {
			if (!hasNext())
				throw new NoSuchElementException("iterator null!");
			return heap[current++];
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public FixedMinPQ() {
		size = 1;
		cnt = 0;
		heap = (Key[]) new Comparable[2];
	}
	
	@SuppressWarnings("unchecked")
	public FixedMinPQ(int n) {
		if (n <= 0) throw new IllegalArgumentException("size cannot be less than 1");
		size = n;
		cnt = 0;
		heap = (Key[]) new Comparable[n+1];
	}
	
	@SuppressWarnings("unchecked")
	public FixedMinPQ(int n, Comparator<Key> comp) {
		if (n <= 0) throw new IllegalArgumentException("size cannot be less than 1");
		size = n;
		cnt = 0;
		heap = (Key[]) new Comparable[n+1];
		comparator = comp;
	}
	
	private boolean less(int i, int j) {
		if (comparator == null) 
			return heap[i].compareTo(heap[j]) < 0;
		else 
			return comparator.compare(heap[i], heap[j]) < 0;
	}
	
	private void exch(int i, int j) {
		Key temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k/2, k);
			k = k/2;
		}	
	}
	
	private void sink(int k) {
		while (2 * k <= cnt) {
			int j = 2 * k;
			if (j < cnt && less(j, j + 1)) j++;
			if (!less(k, j)) break;
			exch(k, j);
			k = j;
		}		
	}
	
	public Key delMax() {
		if (isEmpty())
			throw new NullPointerException("no more items in heap");
		Key item = heap[1];
		exch(1, cnt--);
		sink(1);
		heap[cnt + 1] = null;
		return item;
	}
	
	public void insert(Key v) {
		if (notFull()) {
			heap[++cnt] = v;
			swim(cnt);
		}
		else {
			heap[0] = v;
			if (less(0, 1)) {
				exch(0, 1);
				sink(1);
			}
			heap[0] = null;
		}
	}
	
	public Key max() {
		return heap[1];
	}
	
	public boolean isEmpty() {
		return cnt == 0;
	}
	
	public boolean notFull() {
		return cnt < size;
	}

}
