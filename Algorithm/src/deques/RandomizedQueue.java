package deques;

import stdlib.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int n;
	private Item[] s;
	
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		n = 0;
		s = (Item[]) new Object[1];
	}
	
	private void resize(int length) {
		@SuppressWarnings("unchecked")
		Item[] copy = (Item[]) new Object[length];
		for (int i = 0; i < n; i++)
			copy[i] = s[i];
		s = copy;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException("null item detected");
		if (s.length == n)
			resize(2 * s.length);
		s[n++] = item; 
	}
	
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("empty queue");
		int id = StdRandom.uniform(n);
		Item item = s[id];
		if (id != n - 1) {
			s[id] = s[n-1];
		}
		s[--n] = null;
		if (n > 0 && n <= s.length/4) 
			resize(s.length/2);
		return item;	
	}
	
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException("empty queue");
		int id = StdRandom.uniform(n);
		return s[id];
	}
	
	public Iterator<Item> iterator() {
		return new RandomIterator();
	}
	
	private class RandomIterator implements Iterator<Item> {
		private int[] order;
		private int   i;
		
		RandomIterator() { 
			order = StdRandom.permutation(n);
			i = 0;
		}
		
		@Override
		public boolean hasNext() {
			return i < n;
		}
		
		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = s[order[i++]];
			return item;
		}
	}
	
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while(!StdIn.isEmpty()) {
			String item = StdIn.readString();
			 if (!item.equals("-") && !item.equals("#")){
	            	rq.enqueue(item);
	            //	q.addFirst(item);
	            }
	            else if (item.equals("-")){
	            	StdOut.print(rq.dequeue() + " ");
	            }
	            else if(item.equals("#")){
	            	StdOut.print(rq.dequeue() + " ");
	            }
	        }
	        StdOut.println("(" + rq.size() + " left on queue)");
	}
}
