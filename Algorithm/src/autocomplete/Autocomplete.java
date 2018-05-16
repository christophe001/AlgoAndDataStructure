package autocomplete;

import java.util.Arrays;

import stdlib.*;

public class Autocomplete {
	private Term[] terms;
	
	public Autocomplete(Term[] terms) {
		if (terms == null)
			throw new NullPointerException("terms cannot be null");
		this.terms = new Term[terms.length];
		for (int i = 0; i < terms.length; i++)
			this.terms[i] = terms[i];
	}
	
	public Term[] allMatches(String prefix) {
		if (prefix == null)
			throw new NullPointerException("terms cannot be null");
		int r = prefix.length();
		Term key = new Term(prefix, 0);
		Arrays.sort(terms, Term.byPrefixOrder(r));
		int first = BinarySearchDeluxe.firstIndexOf(terms, key, Term.byPrefixOrder(r));
		int last = BinarySearchDeluxe.lastIndexOf(terms, key, Term.byPrefixOrder(r));
		if (first == -1 || last == -1 || last < first)
			return null;
		else {
			int n = last - first + 1;
			Term[] res = new Term[n];
			for (int i = first; i <= last; i++)
				res[i - first] = terms[i];
			Arrays.sort(res, Term.byReverseWeightOrder());
			return res;
		}
	}

	public int numberOfMatches(String prefix) {
		if (prefix == null)
			throw new NullPointerException("terms cannot be null");
		int r = prefix.length();
		Term key = new Term(prefix, 0);
		Arrays.sort(terms, Term.byPrefixOrder(r));
		int first = BinarySearchDeluxe.firstIndexOf(terms, key, Term.byPrefixOrder(r));
		int last = BinarySearchDeluxe.lastIndexOf(terms, key, Term.byPrefixOrder(r));
		if (first == -1)
			return 0;
		else return last - first + 1;
	}
	
	public static void main(String[] args) {

	    // read in the terms from a file
	    String filename = args[0];
	    In in = new In(filename);
	    int N = in.readInt();
	    Term[] terms = new Term[N];
	    for (int i = 0; i < N; i++) {
	        long weight = in.readLong();           // read the next weight
	        in.readChar();                         // scan past the tab
	        String query = in.readLine();          // read the next query
	        terms[i] = new Term(query, weight);    // construct the term
	    }

	    // read in queries from standard input and print out the top k matching terms
	    int k = Integer.parseInt(args[1]);
	    Autocomplete autocomplete = new Autocomplete(terms);
	    while (StdIn.hasNextLine()) {
	        String prefix = StdIn.readLine();
	        Term[] results = autocomplete.allMatches(prefix);
	        for (int i = 0; i < Math.min(k, results.length); i++)
	            StdOut.println(results[i]);
	    }
	}
}
