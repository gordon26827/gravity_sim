package model;

import java.awt.Color;
import java.util.*;

public class Sim {
	private static double G = 6.6743 / Math.pow(10, 11);
	private ArrayList<Body> bodies;
	
	public Sim() {
		bodies = new ArrayList<Body>();
	}

	public void tick(int times) {
		int size = bodies.size();
		int i, j;

		for (int t = 0; t < times; t++) {
			for (i = 0; i < size; i++) {
				bodies.get(i).tick();
				
				for (j = i + 1; j < size; j++) {
					calc_f_g(bodies.get(i), bodies.get(j));
				}
				
				// bodies.get(i).tick(times);
			}
		}
	}
	
	private static void calc_f_g(Body b1, Body b2) {
		double x1 = b2.pos[0] - b1.pos[0], y1 = (b2.pos[1] - b1.pos[1]);
		double force = G * b1.mass * b2.mass / (Math.pow(x1, 2) + Math.pow(y1, 2));
		double theta = Math.atan2(y1, x1) + Math.PI;
		
		b1.apply_force(force, theta);
		b2.apply_force(force, theta);
	}
	
	public void create_body(double[] pos, double[] vel, double mass, Color color) {
		bodies.add(new Body(pos, vel, mass, color));
	}
	
	public void create_body2(double theta, double r, double v, double mass, Color color) {
		double[] pos = new double[2];
		double[] vel = new double[2];
		
		theta = -Math.toRadians(theta);
		
		pos[0] = Math.cos(theta) * r;
		pos[1] = Math.sin(theta) * r;
		
		theta -= Math.PI / 2;
		
		vel[0] = Math.cos(theta) * v;
		vel[1] = Math.sin(theta) * v;

		bodies.add(new Body(pos, vel, mass, color));
	}
	
	public ArrayList<Body> get_bodies() {
		return bodies;
	}
}
