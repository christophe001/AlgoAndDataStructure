package datastructure;

import stdlib.StdOut;

public class Vector {
	
	private int d;
	
	private double[] data;
	
	public Vector(int d) {
		this.d = d;
		data = new double[d];
	}
	
	public Vector(double... a) {
		d = a.length;
		data = new double[d];
		for(int i = 0; i < d; i++)
			data[i] = a[i];
	}
	
	public Vector(Vector that) {
		d = that.d;
		data = new double[d];
		for(int i = 0; i < d; i++)
			this.data[i] = that.data[i];
	}
	
	public int length() {
		return d;
	}
	
	public int dimension() {
		return d;
	}
	
	public double dot(Vector that) {
		if (this.d != that.d)
			throw new IllegalArgumentException("dimensions of vectors don't match");
		double res = 0;
		for(int i = 0; i < d; i++) {
			res += this.data[i] * that.data[i];
		}
		return res;
	}
	
	public double norm() {
		return Math.sqrt(this.dot(this));
	}

	public double distanceTo(Vector that) {
		if (this.d != that.d)
			throw new IllegalArgumentException("dimensions of vectors don't match");
		return this.minus(that).norm();
	}
	
	public Vector plus(Vector that) {
		if (this.d != that.d)
			throw new IllegalArgumentException("dimensions of vectors don't match");
		Vector v = new Vector(this.d);
		for(int i = 0; i < this.d; i++)
			v.data[i] = this.data[i] + that.data[i];
		return v;
	}	
	
	
	public Vector minus(Vector that) {
		if (this.d != that.d)
			throw new IllegalArgumentException("dimensions of vectors don't match");
		Vector v = new Vector(this.d);
		for(int i = 0; i < this.d; i++)
			v.data[i] = this.data[i] - that.data[i];
		return v;
	}
	
	public double at(int i) {
		if (i < 0 || i >= d)
			throw new IllegalArgumentException("illegal dimension");
		return data[i];
	}
	
	public Vector scale(double a) {
		Vector c = new Vector(this.d);
		for(int i = 0; i < this.d; i++)
			c.data[i] = this.data[i] * a;
		return c;
	}
	
	public Vector direction() {
		if (this.norm() == 0)
			throw new ArithmeticException("Zero-vector has no direction");
		return this.scale(1.0/this.norm());
	}
	
	public String toString() {
	    StringBuilder s = new StringBuilder();
	    for (int i = 0; i < d; i++)
	        s.append(data[i] + " ");
	    return s.toString();
	}

	public static void main(String[] args) {
	    double[] xdata = { 1.0, 2.0, 3.0, 4.0 };
	    double[] ydata = { 5.0, 2.0, 4.0, 1.0 };
	    Vector x = new Vector(xdata);
	    Vector y = new Vector(ydata);

	    StdOut.println("   x       = " + x);
	    StdOut.println("   y       = " + y);

	    Vector z = x.plus(y);
	    StdOut.println("   z       = " + z);

	    z = z.scale(10.0);
	    StdOut.println(" 10z       = " + z);

	    StdOut.println("  |x|      = " + x.norm());
	    StdOut.println(" <x, y>    = " + x.dot(y));
	    StdOut.println("dist(x, y) = " + x.distanceTo(y));
	    StdOut.println("dir(x)     = " + x.direction());
	}
}
