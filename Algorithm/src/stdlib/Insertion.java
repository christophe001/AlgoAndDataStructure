package stdlib;

public class Insertion {

	public static <Key extends Comparable<Key>> void sort(Key[] a) {
		int n = a.length;
		for(int i = 1; i < n; i++) {
			for (int j = i; j > 0; j--) {
				if (a[j-1].compareTo(a[j]) > 0) {
					exchange(a, j-1, j);
				}
				else break;
			}
		}
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
