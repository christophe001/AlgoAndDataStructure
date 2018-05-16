package stdlib;

public class QuickSort {
	
	private static <Key extends Comparable<Key>> int partition(Key[] a, int lo, int hi) {
		int i = lo, j = hi + 1;
		while (true) {
			while (less(a[++i], a[lo]))
				if (i == hi) break;
			while (less(a[lo], a[--j]))
				if (j == lo) break;
			if (i >= j) break;
			exchange(a, i, j);
		}
		return j;
	}

	private static void exchange(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	private static <Key extends Comparable<Key>> boolean less(Key a, Key b) {
		return a.compareTo(b) < 0;
	}
	
	public static <Key extends Comparable<Key>> void sort(Key[] a) {
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
	}
	
	private static <Key extends Comparable<Key>> void sort(Key[] a, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}
}
