package model;

class Body {
	double[] pos, vel, acc;
	double mass;
	
	Body(double[] pos, double[] vel, double mass) {
		this.pos = pos;
		this.vel = vel;
		this.mass = mass;
		this.acc = new double[2];
	}
	
	void tick(int factor) {
		for (int i = 0; i < 2; i++) {
			vel[i] += acc[i] * factor;
			pos[i] += vel[i] * factor;
		}
	}
	
	void apply_force(double force, double theta) {
		double a = force / mass;
		acc[0] += Math.cos(theta) * a;
		acc[1] += Math.sin(theta) * a;
	}
}
