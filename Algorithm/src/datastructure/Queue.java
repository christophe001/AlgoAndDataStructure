package datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;
import stdlib.*;

public class Queue<Item> implements Iterable<Item> {
	private int n;
	private Node first;
	private Node last;
	
	private class Node {
		Item obj;
		Node next;
	}
	
	public Queue() {
		n = 0;
		first = null;
		last = null;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int length() {
		return n;
	}
	
	public int size() {
		return n;
	}
	
	public void enqueue(Item item) {
		Node oldlast = last;
		last = new Node();
		last.obj = item;
		last.next = null;
		if (isEmpty())
			first = last;
		else oldlast.next = last;
		n++;
	}
	
	public Item peek() {
		if (isEmpty())
			throw new NoSuchElementException("queue underflow");
		return first.obj;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item item : this) {
			s.append(item);
			s.append(' ');
		}
		return s.toString();
	}
	
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("queue underflow");
		Item item = first.obj;
		first = first.next;
		n--;
		if (isEmpty()) 
			last = null;
		return item;
		
	}
	
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}
	
	private class QueueIterator implements Iterator<Item> {
		private Node current = first;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException("queue underflow");
			Item item = current.obj;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }

}
