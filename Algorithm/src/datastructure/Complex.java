package datastructure;

import stdlib.StdOut;

public final class Complex {
	private double r;
	private double theta;
	
	public Complex(double re, double im) {
		r 		= Math.sqrt(re * re + im * im);
		theta 	= Math.atan2(im, re);
	}
	
	public double re() {
		return r * Math.cos(theta);
	}
	
	public double im() {
		return r * Math.sin(theta);
	}
	
	public String toString() {
		return re() + " + " + im() + "i";
	}
	
	public Complex plus(Complex b) {
		double re = this.r * Math.cos(this.theta) + b.r * Math.cos(b.theta);
		double im = this.r * Math.sin(this.theta) + b.r * Math.sin(b.theta);
		return new Complex(re, im);
	}
	
	public Complex times(Complex b) {
		Complex c = new Complex(0, 0);
		c.r = this.r * b.r;
		c.theta = this.theta + b.theta;
		return c;
	}
	
	public double abs() {
		return r;
	}
	
	public static void main(String[] args) {
		Complex a = new Complex(5.0, 6.0);
		StdOut.println("a = " + a);
		
		Complex b = new Complex(-2.0, 3.0);
		StdOut.println("b = " + b);
		
		StdOut.println("a + b = " + a.plus(b));
		StdOut.println("a * b = " + a.times(b));
	}
}
