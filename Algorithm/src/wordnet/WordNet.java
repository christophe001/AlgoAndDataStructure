package wordnet;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class WordNet {
	public WordNet(String synsets, String hypernyms) {}
	
	public Iterable<String> nouns() {}
	
	public boolean isNoun(String word) {}
	
	// a synset (second field of synsets.txt) that is a shortest common ancestor
	// of noun1 and noun2 (defined below)
	public String sca(String noun1, String noun2) {}

	// distance between noun1 and noun2 (defined below)
	public int distance(String noun1, String noun2) {}

	// unit testing (required)
	public static void main(String[] args) {}
	
	
}