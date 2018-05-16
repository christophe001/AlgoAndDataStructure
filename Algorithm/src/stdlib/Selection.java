package stdlib;

public class Selection {
	public static <Key extends Comparable<Key>> void sort(Key[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			int min = i;
			for (int j = i + 1; j < n; j++) {
				if (less(a[j], a[min]))
					min = j;
			}
			exchange(a, i, min);
		}
	}
	
	private static <Key extends Comparable<Key>> boolean less(Key a, Key b) {
		return a.compareTo(b) < 0;
	}
	
	private static void exchange(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        sort(a);
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }
}
