package bouncing;

import java.awt.Color;
import stdlib.*;

public class Particle {

	private static final double INFINITY = Double.POSITIVE_INFINITY;
	
	private double rx, ry;
	private double vx, vy;
	private int count;	
	private final double mass;
	private final double radius;
	private final Color color;
	
	public Particle(double rx, double ry, double vx, double vy, double mass, double radius, Color color) {
		this.rx = rx;
		this.ry = ry;
		this.vx = vx;
		this.vy = vy;
		this.mass = mass;
		this.radius = radius;
		this.color = color;
	}
	
	public Particle() {
		rx = StdRandom.uniform(0, 1.0);
		ry = StdRandom.uniform(0, 1.0);
		vx = StdRandom.uniform(-0.005, 0.005);
		vy = StdRandom.uniform(-0.005, 0.005);
		mass = 0.5;
		radius = 0.01;
		color = Color.BLACK;
	}
	
	public void move(double dt) {
		rx += vx * dt;
		ry += vy * dt;
	}
	
	public void draw() {
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, radius);
	}
	
	public int count() {
		return count;
	}
	
	public double timeToHit(Particle that) {
		if (this == that) return INFINITY;
		double dx = that.rx - this.rx;
		double dy = that.ry - this.ry;
		double dvx = that.vx - this.vx;
		double dvy = that.vy - this.vy;
		double dvdr = dvx * dx + dvy * dy;
		if (dvdr > 0) return INFINITY;
		double dvdv = dvx * dvx + dvy * dvy;
		double drdr = dx * dx + dy * dy;
		double sigma = this.radius + that.radius;
		double d = (dvdr * dvdr) - dvdv*(drdr - sigma * sigma);
		if (d < 0) return INFINITY;
		return -(dvdr + Math.sqrt(d))/dvdv;
	}
	
	public double timeTiHitVerticalWall() {
		if (vx < 0) return (radius - rx) / vx;
		else if (vx > 0) return (1.0 - radius - rx) / vx;
		else return INFINITY;
	}
	
	public double timeToHitHorizontalWall() {
		if (vy < 0) return (radius - ry) / vy;
		else if (vy > 0) return (1.0 - radius - ry) / vy;
		else return INFINITY;
	}
	
	public void bounceOff(Particle that) {
		double dx = that.rx - this.rx;
		double dy = that.ry - this.ry;
		double dvx = that.vx - this.vx;
		double dvy = that.vy - this.vy;
		double dvdr = dvx * dx + dvy * dy;
		double dist = this.radius + that.radius;
		
		double magnitude = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
		double fx = magnitude * dx / dist;
	    double fy = magnitude * dy / dist;

	    this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;
        
        this.count++;
        that.count++;
	}
	
	public void bounceOffVerticalWall() {
		vx = - vx;
		count++;
	}
	
	public void bounceOffHorizontalWall() {
		vy = - vy;
		count++;
	}
	
	public double kineticEnergy() {
		return 0.5 * mass * (vx * vx + vy * vy);
	}
}
