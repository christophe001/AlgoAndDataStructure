package wordnet;

import java.util.NoSuchElementException;

import stdlib.*;

import graph.Digraph;

import java.util.HashMap;

import datastructure.Bag;

public class WordNet {
	
	private HashMap<Integer, String> id2set;
	
	private HashMap<String, Bag<Integer>> noun2ids;
	
	private Digraph digraph;
	
	private ShortestCommonAncestor sca;
	
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new IllegalArgumentException("Empty arguments");
		id2set = new HashMap<Integer, String>();
		noun2ids = new HashMap<String, Bag<Integer>>();
		try {
			In Synsets = new In(synsets);
			In Hypernyms = new In(hypernyms);
			while (Synsets.hasNextLine()) {
				String[] syn = Synsets.readLine().split(",");
				int id = Integer.valueOf(syn[0]);
				String nouns = syn[1];
				String[] ns = nouns.split(" ");
				id2set.put(id, nouns);
				for (String n : ns) {
					Bag<Integer> ids = new Bag<Integer>();
					if (noun2ids.containsKey(n)) {
						for (int i : noun2ids.get(n)) 
							ids.add(i);
					}
					ids.add(id);
					noun2ids.put(n, ids);
				}
			}
			digraph = new Digraph(id2set.size());
			while (Hypernyms.hasNextLine()) {
				String[] hyp = Hypernyms.readLine().split(",");
				int v = Integer.valueOf(hyp[0]);
				int w = Integer.valueOf(hyp[1]);
				digraph.addEdge(v, w);
			}
			sca = new ShortestCommonAncestor(digraph);
		}
		catch (NoSuchElementException e) {
			throw new IllegalArgumentException("invalid file format", e);
		}
		
	}
	
	public Iterable<String> nouns() {
		return noun2ids.keySet();
	}
	
	public boolean isNoun(String word) {
		return noun2ids.containsKey(word);
	}
	
	// a synset (second field of synsets.txt) that is a shortest common ancestor
	// of noun1 and noun2 (defined below)
	public String sca(String noun1, String noun2) {
		if (isNoun(noun1) && isNoun(noun2)) {
			Bag<Integer> id1 = noun2ids.get(noun1);
			Bag<Integer> id2 = noun2ids.get(noun2);
			int a = sca.ancestorSubset(id1, id2);
			return id2set.get(a);
		}
		else throw new IllegalArgumentException("noun doesn't exist");
	}

	// distance between noun1 and noun2 (defined below)
	public int distance(String noun1, String noun2) {
		if (isNoun(noun1) && isNoun(noun2)) {
			Bag<Integer> id1 = noun2ids.get(noun1);
			Bag<Integer> id2 = noun2ids.get(noun2);
			int l = sca.lengthSubset(id1, id2);
			return l; 
		}
		else throw new IllegalArgumentException("noun doesn't exist");
		
	}

	// unit testing (required)
	public static void main(String[] args) {}
	
	
}
