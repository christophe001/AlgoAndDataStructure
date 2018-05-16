package eightpuzzle;

import stdlib.*;

import java.awt.Font;

import datastructure.*;

public class Solver {
	private SearchNode last;
	public Solver(Board bd) {
		if (bd == null)
			throw new NullPointerException("null board detected");
		if (!bd.isSolvable()) 
			throw new IllegalArgumentException("Unsolvable");
		else {
			SearchNode initial = new SearchNode(bd, null);
			MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
			pq.insert(initial);
			SearchNode cur = pq.delMin();
			while (!cur.isGoal()) {
				for(Board neighbor : cur.board.neighbors()) {
					if (cur.prev == null) {
						pq.insert(new SearchNode(neighbor, cur));
					}
					else {
						if (!neighbor.equals(cur.prev.board)) {
							pq.insert(new SearchNode(neighbor, cur));
						}
					}
				}
				cur = pq.delMin();
			}
			last = cur;
		}
	}
	
	public int moves() {
		if (last != null)
			return last.moves;
		else return -1;
	}
	public Iterable<Board> solution() {
		if (last != null) {
			Stack<Board> solution = new Stack<Board>();
			SearchNode cur = last;
			while(cur != null) {
				solution.push(cur.board);
				cur = cur.prev;
			}
			return solution;
		}
		else return null;
	}
	
	private static class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private int moves;
		private SearchNode prev;
		public SearchNode(Board board, SearchNode prev) {
			this.board = board;
			this.prev = prev;
			if (prev == null) {
				this.moves = 0;
			}
			else this.moves = prev.moves + 1;
		}
		
		public boolean isGoal() {
			return board.isGoal();
		}
		
		public int compareTo(SearchNode that) {
			return this.board.manhattan() + this.moves - that.board.manhattan() - that.moves;
		}
	}
	
	public static void main(String[] args) {
		 // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] tiles = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            tiles[i][j] = in.readInt();
	    Board initial = new Board(tiles);
	    
	    // check if puzzle is solvable; if so, solve it and output solution
	    if (initial.isSolvable()) {
	        Solver solver = new Solver(initial);
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        int i = 0;
	        StdDraw.enableDoubleBuffering();
	        for (Board bd : solver.solution()) {
	            bd.draw();
	            StdDraw.setPenColor(StdDraw.BLACK);
	            StdDraw.setFont(new Font("SanSerif", Font.BOLD, 16));	            
	        	StdDraw.text(0.5 * N, -0.025 * N, "moves: " + Integer.toString(i++));
	            StdDraw.show();
	            StdDraw.pause(500);
	        }
	    }

	    // if not, report unsolvable
	    else {
	        StdOut.println("Unsolvable puzzle");
	    }
	}
}
