package model;

import java.util.*;

public class System {
	private static double G = 6.6743 / Math.pow(10, 11);
	private ArrayList<Body> bodies;
	
	public System() {
		bodies = new ArrayList<Body>();
	}

	public void tick(int times) {
		int size = bodies.size();

		for (int i = 0; i < size; i++) {
			
			for (int j = i + 1; j < size; j++) {
				calc_f_g(bodies.get(i), bodies.get(j));
			}
			
			bodies.get(i).tick(times);
		}
	}
	
	private static void calc_f_g(Body b1, Body b2) {
		double x1 = b2.pos[0] - b1.pos[0], y1 = b2.pos[1] - b1.pos[1];
		double force = G * b1.mass * b2.mass / (Math.pow(x1, 2) + Math.pow(y1, 2));
		
		b1.apply_force(force, Math.atan2(y1, x1));
		b2.apply_force(force, Math.atan2(-y1, -x1));
	}
	
	public void create_body(double[] pos, double[] vel, double mass) {
		bodies.add(new Body(pos, vel, mass));
	}
}
