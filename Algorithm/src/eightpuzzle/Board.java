package eightpuzzle;

import java.awt.Font;

import datastructure.Stack;
import stdlib.*;

public class Board {
	// construct a board from an N-by-N array of tiles
	// (where tiles[i][j] = tile at row i, column j)
	private final int[][] tiles;
	private final int N;
	private final int kx, ky;
    public Board(int[][] tiles) {
    	N = tiles.length;
    	this.tiles = new int[N][N];
    	int px = 0, py = 0;
    	for(int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			this.tiles[i][j] = tiles[i][j];
    			if (this.tiles[i][j] == 0) {
    				px = i;
    				py = j;
    			}
    		}
    	}
    	kx = px;
    	ky = py;
    }
        
    private void validate(int i) {
    	if (i < 0 || i >= N)
    		throw new IndexOutOfBoundsException("index out of bounds");
    }
 // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
    	validate(i);
    	validate(j);
    	return tiles[i][j];
    }
    
    // board size N
    public int size() {
    	return N;
    }
    
    // number of tiles out of place
    public int hamming() {
    	int cnt = 0;
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			if (tiles[i][j] != 0 && tiles[i][j] != i * N + j + 1) {
    					cnt++;
    			}
    		}
    	}
    	return cnt;
    }
    
    private int getManhattanDistance(int i, int j) {
    	int num = tiles[i][j];
    	if (num == 0) return 0;
    	int ipos = (num - 1) / N;
    	int jpos = (num - 1) % N;
    	return Math.abs(ipos - i) + Math.abs(jpos - j);
    }
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int cnt = 0;
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++)
    			cnt += getManhattanDistance(i, j);
    	}
    	return cnt;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
    	return hamming() == 0;
    }
    
    // is this board solvable?
    public boolean isSolvable() {
    	if (N % 2 == 1) {
    		if (getInversion() % 2 == 0) return true;
    		else return false;
    	}
    	else {
    		int parity = getInversion() + kx;
    		if (parity % 2 == 1) return true;
    		else return false;
    	}
    }
    
    private int getInversion() {
    	int cnt = 0;
    	for(int i = 0; i < N * N; i++) {
    		int num = tiles[i / N][i % N];
    		if (num != 0) {
    			for(int j = 0; j < i; j++) {
    				if (tiles[j / N][j % N] > num)
    					cnt++;
    			}
    		}
    	}
    	return cnt;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
    	if (y == this) return true;
    	if (y == null || y.getClass() != this.getClass()) return false;
    	if (((Board)y).N != this.N) return false;
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			if (((Board)y).tiles[i][j] != this.tiles[i][j])
    				return false;
    		}
    	}
    	return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
    	Stack<Board> neighbors = new Stack<Board>();
    	for(int i = 0; i < 4; i++) {
    		Board bd = move(i);
    		if (bd != null) {
    			neighbors.push(bd);
    		}
    	}
    	return neighbors;
    }
    
    private void swap(int[][] data, int i0, int j0, int i1, int j1) {
    	validate(i0);
    	validate(i1);
    	validate(j0);
    	validate(j1);
    	int temp = data[i0][j0];
    	data[i0][j0] = data[i1][j1];
    	data[i1][j1] = temp;
    }
    
    private Board move(int id) {
    	if (id < 0 || id > 3) return null;
    	int dx, dy;
    	switch(id) {
    		case 0: dx = -1; dy = 0; 
    				break;
    		case 1: dx = 0; dy = -1; 
    				break;
    		case 2: dx = 1; dy = 0;
    				break;
    		case 3: dx = 0; dy = 1;
    				break;
    		default: dx = -1; dy = -1;
    				break;
    	}
    	int px = kx + dx, py = ky + dy;
    	if (px < 0 || px >= N) return null;
    	if (py < 0 || py >= N) return null;
    	int[][] data = new int[N][N];
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			data[i][j] = tiles[i][j];
    		}
    	}
    	swap(data, kx, ky, px, py);
    	return new Board(data);
    	
    }
    // draw the board
    public void draw() {
    	StdDraw.clear();
    	StdDraw.setPenColor(StdDraw.BLACK);
    	StdDraw.setXscale(-0.05 * N, 1.05 * N);
    	StdDraw.setYscale(-0.05 * N, 1.05 * N);
    	StdDraw.filledSquare(N/2.0, N/2.0, N/2.0);
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			if (tiles[i][j] != 0) {
    				StdDraw.setPenColor(StdDraw.WHITE);
    				StdDraw.filledSquare(j + 0.5, N - i - 0.5, 0.45);
    				StdDraw.setFont(new Font("SansSerif", Font.BOLD, 24));
    				StdDraw.setPenColor(StdDraw.BLACK);
    				StdDraw.text(j + 0.5, N - i - 0.5, Integer.toString(tiles[i][j]));
    			}
    		}
    	}
    }
    
	// string representation of this board (in the output format specified below)
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append(N + "\n");
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++)
    			s.append(String.format("%2d ", tiles[i][j]));
    		s.append("\n");
    	}
    	return s.toString();
    }
    // unit testing (not graded)
    public static void main(String[] args) {
    	int N = Integer.parseInt(args[0]);
    	int[][] arr = new int[N][N];
    	int[] a = StdRandom.permutation(N * N);
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			arr[i][j] = a[i * N + j];
    		}
    	}
    	Board board = new Board(arr);
    	Stack<Board> neighbors = (Stack<Board>)board.neighbors();
    	for(Board b : neighbors)
    		StdOut.println(b.toString());
    	board.draw();
    	StdDraw.setPenColor(StdDraw.BLACK);
    	String s;
    	if (board.isSolvable())
    		s = "Solvable | Neighbors: " + neighbors.size();
    	else s = "Unsolvable | Neighbors: " + neighbors.size();
    	StdDraw.setFont(new Font("SanSerif", Font.ITALIC, 16));
    	StdDraw.text(0.5 * N, -0.025 * N, s);
    	StdDraw.show();
    	StdDraw.pause(3000);
    }
}
