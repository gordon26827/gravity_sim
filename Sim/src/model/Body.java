package model;

import java.awt.Color;

public class Body {
	public double[] pos, vel, acc;
	double mass;
	
	public Color color;
	
	public Body(double[] pos, double[] vel, double mass, Color color) {
		this.pos = pos;
		this.vel = vel;
		this.mass = mass;
		this.color = color;
		this.acc = new double[2];
	}
	
	void tick() {
		for (int i = 0; i < 2; i++) {
			vel[i] += acc[i];
			pos[i] += vel[i] + acc[i] / 2;
			acc[i] = 0;
		}
	}
	
	void apply_force(double force, double theta) {
		double a = force / mass;
		acc[0] += Math.cos(theta) * a;
		acc[1] += Math.sin(theta) * a;
	}
}
