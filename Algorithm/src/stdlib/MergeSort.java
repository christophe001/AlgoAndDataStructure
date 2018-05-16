package stdlib;

public class MergeSort {

	public static <Key extends Comparable<Key>> void sort(Key[] a) {
		@SuppressWarnings("unchecked")
		Key[] aux = (Key[]) new Object[a.length];
		sort(a, aux, 0, a.length - 1);
	}
	
	private static <Key extends Comparable<Key>> void merge(Key[] a, Key[] aux, int lo, int mid, int hi) {
		for (int k = lo; k <= hi; k++)
			aux[k] = a[k];
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid) 					a[k] = aux[j++];
			else if (j > hi)	            a[k] = aux[i++];
			else if (less(aux[i], aux[j]))  a[k] = aux[i++];
			else							a[k] = aux[j++];
		}
	}
	
	private static <Key extends Comparable<Key>> boolean less(Key a, Key b) {
		return a.compareTo(b) < 0;
	}
	
	private static <Key extends Comparable<Key>> void sort(Key[] a, Key[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo)/2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		if (less(a[mid], a[mid+1])) return;
		merge(a, aux, lo, mid, hi);
	}
}
