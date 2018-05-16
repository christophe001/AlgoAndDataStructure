package autocomplete;

import java.util.Comparator;
import java.util.Arrays;

public class Term implements Comparable<Term> {
	private final String query;
	private final long weight;
	
	public Term(String query, long weight) {
		if (query == null)
			throw new NullPointerException("query is null");
		if (weight < 0)
			throw new IllegalArgumentException("weight is negative");
		this.query = query;
		this.weight = weight;
	}
	
	public static Comparator<Term> byReverseWeightOrder() {
		return new ByReverseWeightOrderComparator();
	}

	private static class ByReverseWeightOrderComparator implements Comparator<Term> {
		public int compare(Term a, Term b) {
			if (a.weight > b.weight) return -1;
			else if (a.weight == b.weight) return 0;
			else return 1;
		}
	}
	
	private static class ByPrefixOrderComparator implements Comparator<Term> {
		private final int len;
		public ByPrefixOrderComparator(int r) {
			len = r;
		}	
		public int compare(Term a, Term b) {
			String subA, subB;
			if (a.query.length() < len)
				subA = a.query;
			else subA = a.query.substring(0, len);
			if (b.query.length() < len)
				subB = b.query;
			else subB = b.query.substring(0, len);
			return subA.compareTo(subB);
		}
	}
	
	public static Comparator<Term> byPrefixOrder(int r) {
		if (r < 0) 
			throw new IllegalArgumentException("r cannot be negative");
		return new ByPrefixOrderComparator(r);
	}

	public int compareTo(Term that) {
		return this.query.compareTo(that.query);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(weight);
		s.append('\t');
		s.append(query);
		return s.toString();
	}
	
	public static void main(String[] args) {
		Term[] terms = {new Term("Debbie", 3), new Term("Abcd", 8), new Term("Cathy", 155555), new Term("Abbcd", 10000)};
		for (Term term : terms) System.out.println(term);
		System.out.println();
		
		Arrays.sort(terms, Term.byReverseWeightOrder());
		for (Term term : terms) System.out.println(term);
		System.out.println();
		
		Arrays.sort(terms, Term.byPrefixOrder(2));
		for (Term term : terms) System.out.println(term);
		System.out.println();
		
		Arrays.sort(terms);
		for (Term term : terms) System.out.println(term);
	}
}
