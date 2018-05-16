package deques;

import java.util.Iterator;
import java.util.NoSuchElementException;
import stdlib.*;

public class Deque<Item> implements Iterable<Item> {
	private int n;
	private Node first;
	private Node last;
	
	private class Node {
		Item item;
		Node prev;
		Node next;
	}
	
	public Deque() {
		n = 0;
		first = null;
		last = null;
	}
	
	public boolean isEmpty() {
		return first == null;		
	}
	
	public int size() {
		return n;
	}
	
	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException("cannot add null item");
		if (isEmpty()) {
			first = new Node();
			first.item = item;
			last = first;
		}
		else {
			Node oldfirst = first;
			first = new Node();
			first.item = item;
			first.prev = null;
			first.next = oldfirst;
			oldfirst.prev = first;
		}
		n++;
	}
	
	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException("cannot add null item");
		if (isEmpty()) {
			last = new Node();
			last.item = item;
			first = last;
		}
		else {
			Node oldlast = last;
			last = new Node();
			last.item = item;
			last.prev = oldlast;
			last.next = null;
			oldlast.next = last;
		}
		n++;
	}
	
	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException("deque underflow");
		Item item = first.item;
		first = first.next;
		if (isEmpty()) {
			last = null;
		}
		else {
			first.prev = null;
		}
		n--;
		return item;
	}
	
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException("deque underflow");
		Item item = last.item;
		last = last.prev;
		if (last == null) {
			first = null;
		}
		else {
			last.next = null;
		}
		n--;
		return item;
	}
	
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item i : this) {
			s.append(i);
			s.append(' ');
		}
		return s.toString();
	}
	
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		Deque<String> deque = new Deque<String>();
		while(!StdIn.isEmpty()) {
			String item = StdIn.readString();
			 if (!item.equals("-") && !item.equals("#")){
	            	deque.addLast(item);
	            //	q.addFirst(item);
	            }
	            else if (item.equals("-")){
	            	StdOut.print(deque.removeFirst() + " ");
	            }
	            else if(item.equals("#")){
	            	StdOut.print(deque.removeLast() + " ");
	            }
	        }
	        StdOut.println("(" + deque.size() + " left on queue)");
	}

}
