package functions;

import java.awt.Color;

import datastructure.Complex;
import stdlib.*;

public class Mandelbrot {
	public static int mand(Complex z0, int max) {
		Complex z = z0;
		for (int t = 0; t < max; t++) {
			if (z.abs() > 2.0) return t;
			z = z.times(z).plus(z0);
		}
		return max;
	}
	
	public static void main(String[] args) {
		double xc = Double.parseDouble(args[0]);
		double yc = Double.parseDouble(args[1]);
		double size = Double.parseDouble(args[2]);
		
		int n = 512;
		int max = 255;
		Picture pic = new Picture(n,n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double x0 = xc - size / 2 + size * i / n;
				double y0 = yc - size / 2 + size * j / n;
				Complex z0 = new Complex(x0, y0);
				int gray = max- mand(z0, max);
				Color color = new Color(gray, gray, gray);
				pic.set(i, n-1-j, color);
			}
		}
		pic.show();
	}
}
