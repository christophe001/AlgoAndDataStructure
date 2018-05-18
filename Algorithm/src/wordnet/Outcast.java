package wordnet;

import stdlib.*;

public class Outcast {

	private WordNet wordnet;
	
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}         
	
	public String outcast(String[] nouns) {
		int best = 0;
		String outlast = nouns[0];
		for (String first : nouns) {
			int dis = 0;
			for (String second : nouns) {
				dis += wordnet.distance(first, second);
			}
			if (dis > best) {
				best = dis;
				outlast = first;
			}
		}
		return outlast;
	}   // given an array of WordNet nouns, return an outcast

	public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}
