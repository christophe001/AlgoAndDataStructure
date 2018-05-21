package seamcarver;

import stdlib.Picture;

import java.awt.Color;

import datastructure.*;

public class SeamCarver {
	// create a seam carver object based on the given picture
	private Picture pic;
	
	private double[][] energy;
	
	private int[][] prev;
	
	private double[][] distTo;
	
	public SeamCarver(Picture picture) {
		pic = new Picture(picture);
	}               
	
	// current picture
	public Picture picture() {
		return pic;
	}                         
	
	// width of current picture
	public int width() {
		return pic.width();
	}                         
	   
	// height of current picture
	public int height() {
		return pic.height();
	}                  
	   
	private void calcEnergy() {
		energy = new double[width()][height()];
		prev = new int[width()][height()];
		distTo = new double[width()][height()];
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				energy[i][j] = energy(i, j);
				distTo[i][j] = Double.POSITIVE_INFINITY;
				prev[i][j] = -1;
			}
		}
	}
	
	private double colorDiffSquare(Color c1, Color c2) {
		double diff = Math.pow(c1.getRed() - c2.getRed(), 2)
				+ Math.pow(c1.getGreen() - c2.getGreen(), 2)
				+ Math.pow(c1.getBlue() - c2.getBlue(), 2);
		return diff;
	}
	
	private void validateX(int x) {
		if (x < 0 || x > pic.width() - 1)
			throw new IllegalArgumentException("x coordinate out of range");
	}
	
	private void validateY(int y) {
		if (y < 0 || y > pic.height() - 1)
			throw new IllegalArgumentException("y coordindate out of range");
	}
	
	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		validateX(x);
		validateY(y);
		int xl = x > 0 ? x - 1 : width() - 1;
		int xr = x < width() - 1 ? x + 1 : 0;
		int yu = y > 0 ? y - 1 : height() - 1;
		int yd = y < height() - 1 ? y + 1 : 0;
		double diff =  colorDiffSquare(pic.get(xl, y), pic.get(xr, y)) 
				+ colorDiffSquare(pic.get(x, yu), pic.get(x, yd));
		return Math.sqrt(diff);
	}               
	  
	private void transpose() {
		Picture p = new Picture(height(), width());
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				p.set(j, i, pic.get(i, j));
			}
		}
		pic = p;
	}
	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		transpose();
		int[] seam = findVerticalSeam();
		transpose();
		return seam;
	}
	
	// find smallest energy in prev line
	private int minPrev(int i, int j) {
		if (i == 0)
			return distTo[i][j-1] < distTo[i+1][j-1] ? i : i + 1;
		else if (i == width() - 1)
			return distTo[i-1][j-1] < distTo[i][j-1] ? i-1 : i;
		else {
			double m = distTo[i-1][j-1];
			int k = i-1;
			if (distTo[i][j-1] < m) {
				m = distTo[i][j-1];
				k = i;
			}
			if (distTo[i+1][j-1] < m) {
				m = distTo[i+1][j-1];
				k = i+1;
			}
			return k;
		}
	}
	
	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		int[] seam = new int[height()];
		calcEnergy();
		for (int i = 0; i < width(); i++) {
			distTo[i][0] = energy[i][0];
		}
		for (int j = 1; j < height(); j++) {
			for (int i = 0; i < width(); i++) {
				prev[i][j] = minPrev(i, j);
				distTo[i][j] = energy[i][j] + distTo[prev[i][j]][j - 1];	
			}
		}
		double minVal = Double.POSITIVE_INFINITY;
		int emin = -1;
		for (int ix = 0; ix < width(); ix++) {
			if (distTo[ix][height()-1] < minVal) {
				minVal = distTo[ix][height()-1];
				emin = ix;
			}
		}
		for (int h = height() - 1; h >= 0; h--) {
			seam[h] = emin;
			emin = prev[emin][h];
		}
		return seam;
	}                
	   
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		Picture p = new Picture(pic.width(), pic.height() - 1);
		for (int i = 0; i < p.width(); i++) {
			for (int j = 0; j < p.height(); j++) {
				if (j < seam[i])
					p.set(i, j, pic.get(i, j));
				else if (j > seam[i]) 
					p.set(i, j - 1, pic.get(i, j));
			}
		}
		pic = p;
	}  
	   
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		Picture p = new Picture(pic.width() - 1, pic.height() - 1);
		for (int i = 0; i < p.width(); i++) {
			for (int j = 0; j < p.height(); j++) {
				if (i < seam[j])
					p.set(i, j, pic.get(i, j));
				else if (i > seam[j]) 
					p.set(i - 1, j, pic.get(i, j));
			}
		}
		pic = p;
	}     
	
	//  unit testing (required)
	public static void main(String[] args) {}
	
}
