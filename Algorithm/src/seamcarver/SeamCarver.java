package seamcarver;

import stdlib.Picture;

import java.awt.Color;


public class SeamCarver {
	// create a seam carver object based on the given picture
	private Picture pic;
	
	private double[][] energy;
	
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
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				energy[i][j] = energy(i, j);
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
				+ colorDiffSquare(pic.get(x, yu), pic.get(xr, yd));
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
	

	
	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		int[] seam = new int[height()];
		calcEnergy();

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
