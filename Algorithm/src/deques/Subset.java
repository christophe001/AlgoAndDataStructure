package deques;

import stdlib.*;

public class Subset {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			rq.enqueue(item);
		}
		
		while (k != 0) {
			StdOut.println(rq.dequeue());
			k--;
		}
	}
}
