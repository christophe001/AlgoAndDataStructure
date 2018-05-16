package stdlib;

public class HeapSort {

	public static <Key extends Comparable<Key> > void sort(Key[] a) {
		int N = a.length;
		for(int k = N/2 - 1; k >= 0; k--) {
			sink(a, k, N);
		}
		while (N > 1) {
			exch(a, 0, --N);
			sink(a, 0, N);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void sink(Comparable[] a, int i, int N) {
		while(2 * i + 2 <= N) {
			int j = 2 * i + 1;
			if (j < N - 1 && less(a, j, j+1)) j++;
			if (!less(a, i, j)) break;
			exch(a, i, j);
			i = j;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean less(Comparable[] a, int i, int j) {
		return a[i].compareTo(a[j]) < 0;
	}
	
	private static void exch(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void main(String[] args) {
		int[] nums = StdRandom.permutation(20);
		Integer[] a = new Integer[20];
		for (int i = 0; i < a.length; i++ )
			a[i] = Integer.valueOf(nums[i]);
        sort(a);
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }
}
