package autocomplete;

import java.util.Comparator;
import java.util.Collections;

public class BinarySearchDeluxe {
	// Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
    	if (a == null || key == null || comparator == null)
    		throw new NullPointerException("null argument");
    	if (comparator.compare(a[0], key) > 0 || comparator.compare(a[a.length - 1], key) < 0)
    		return -1;
    	else return firstIndexOf(a, key, 0, a.length - 1, comparator);
    }
    
    private static <Key> int firstIndexOf(Key[] a, Key key, int lo, int hi, Comparator<Key> comparator) {
    	if (hi <= lo + 1) {
    		if (comparator.compare(a[lo], key) == 0)
    			return lo;
    		else return hi;
    	}
    	int mid = lo + (hi - lo) / 2;
    	if (comparator.compare(a[mid], key) < 0)
    		return firstIndexOf(a, key, mid + 1, hi, comparator);
    	else return firstIndexOf(a, key, lo, mid, comparator);
    	
    }
    
    private static <Key> int lastIndexOf(Key[] a, Key key, int lo, int hi, Comparator<Key> comparator) {
    	if (hi <= lo + 1) {
    		if (comparator.compare(a[hi], key) == 0)
    			return hi;
    		else return lo;
    	}
    	int mid = lo + (hi - lo) / 2;
    	if (comparator.compare(a[mid], key) > 0)
    		return lastIndexOf(a, key, lo, mid - 1, comparator);
    	else return lastIndexOf(a, key, mid, hi, comparator);
    }
    
    // Returns the index of the last key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
    	if (a == null || key == null || comparator == null)
    		throw new NullPointerException("null argument");
    	if (comparator.compare(a[0], key) > 0 || comparator.compare(a[a.length - 1], key) < 0)
    		return -1;
    	else return lastIndexOf(a, key, 0, a.length - 1, comparator);
    }
    
    public static void main(String[] args) {
    	Integer[] numbers = {10, 10, 10, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 1, 1, 1};
    	System.out.print(BinarySearchDeluxe.firstIndexOf (numbers, 10, Collections.reverseOrder()) + "\t");
    	System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 10, Collections.reverseOrder()));
    	System.out.print(BinarySearchDeluxe.firstIndexOf (numbers, 9, Collections.reverseOrder()) + "\t");
    	System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 9, Collections.reverseOrder()));
    	System.out.print(BinarySearchDeluxe.firstIndexOf (numbers, 4, Collections.reverseOrder()) + "\t");
    	System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 4, Collections.reverseOrder()));
    	System.out.print(BinarySearchDeluxe.firstIndexOf (numbers, 0, Collections.reverseOrder()) + "\t");
    	System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 0, Collections.reverseOrder()));
    	System.out.print(BinarySearchDeluxe.firstIndexOf (numbers, 11, Collections.reverseOrder()) + "\t");
    	System.out.println(BinarySearchDeluxe.lastIndexOf(numbers, 11, Collections.reverseOrder()));
    }
}
