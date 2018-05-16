package datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;
import stdlib.*;

public class Stack<Item> implements Iterable<Item> {
	private Node first;
	private int size;
	public Stack() {
		size = 0;
		first = null;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	private class Node {
		Item obj;
		Node next;
	}
	
	public int size() {
		return size;
	}
	
	public Item pop() {
		if (isEmpty())
			throw new NoSuchElementException("stack underflow ");
		Item item = first.obj;
		first = first.next;
		size--;
		return item;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item item : this) {
			s.append(item);
			s.append(' ');
		}
		return s.toString();
	}
	
	public void push(Item item) {
		Node oldfirst = first;
		first = new Node();
		first.obj = item;
		first.next = oldfirst;
		size++;
	}
	
	public Item peek() {
		if (isEmpty())
			throw new NoSuchElementException("stack underflow ");
		return first.obj;
		
	}
	
	public Iterator<Item> iterator() {
		return new StackIterator();
		
	}
	
	private class StackIterator implements Iterator<Item> {
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
				throw new NoSuchElementException("iterator null!");
			Item item = current.obj;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args) {
		Stack<String> stack = new Stack<String>();
	    while (!StdIn.isEmpty()) {
	    	String item = StdIn.readString();
	        if (!item.equals("-")) stack.push(item);
	        else if (!stack.isEmpty()) StdOut.print(stack.pop() + " ");
	    }
	    for(String s : stack)
	    	StdOut.println(s);
	    StdOut.println("(" + stack.size() + " left on stack)");
	}
}
