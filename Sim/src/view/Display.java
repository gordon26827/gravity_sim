package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import model.*;

@SuppressWarnings("serial")
public class Display extends JPanel implements Runnable {
	private Point lastPoint;
	private static Sim sim;
	private Thread thread;
	private static JFrame frame;
	
	private boolean running;
	
	public Display() {
		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				lastPoint = new Point(e.getX(), e.getY());
				System.out.println(lastPoint);
			}
		});
		
		sim = new Sim();
	}
	
	private synchronized void start() {
		if(running) return;
		
		thread = new Thread(this);
		thread.start();
		
		running = true;
		
	}
	
	private synchronized void stop() {
		if(!running) return;
	
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}
	
	public void run() {
		double duration = 0;
		int seconds = 1000;
		
		while (running) {
			sim.tick(seconds);
			render();
			
			duration += seconds;
			// System.out.println("Days: " + (duration / 86400));
		}
		
		stop();
	}

	private void render() {
		BufferStrategy bs = frame.getBufferStrategy();
		Graphics g = (Graphics2D) bs.getDrawGraphics();
		int width = frame.getWidth(), height = frame.getHeight();
		int halfWidth = width / 2, halfHeight = height / 2;
		int x, y, rad = 10, halfRad = rad / 2;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, halfHeight, width, halfHeight);
		g.drawLine(halfWidth, 0, halfWidth, height);
		
		ArrayList<Body> bodies = sim.get_bodies();
		for (Body body : bodies) {
			x = (int)(Math.round(body.pos[0] / 1000000000)) + halfWidth;
			y = (int)(Math.round(body.pos[1] / 1000000000)) + halfHeight;
			g.setColor(body.color);
			g.fillOval(x - halfRad, y - halfRad, rad, rad);
			
			g.setColor(Color.MAGENTA);
			g.drawLine(x, y, x + (int)(body.acc[0] * 1E10), y + (int)(body.acc[1] * 1E10));
			
			g.setColor(Color.RED);
			g.drawLine(x, y, x + (int)(body.vel[0] / 2000), y + (int)(body.vel[1]) / 2000);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		double theta, rad, vel, mass;
		Display display = new Display();
		
		frame = new JFrame("Space Simulator");
		frame.getContentPane().add(display, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setMinimumSize(new Dimension(200, 200));
		frame.setVisible(true);
		frame.createBufferStrategy(2);
		
		/* Sun */
		theta = 0;
		rad = 0;
		vel = 0;
		mass = 1.989E30;
		
		sim.create_body2(theta, rad, vel, mass, Color.ORANGE);
		
		/* Earth: */
		theta = 30;
		rad = 1.51E11;
		vel = 30000;
		mass = 5.97E24;
		
		sim.create_body2(theta, rad, vel, mass, Color.BLUE);
		
		/* Mars */
		theta = 180;
		rad = 2.5E11;
		vel = 24000;
		mass = 6.4171E23;
		
		sim.create_body2(theta, rad, vel, mass, Color.RED);
		
		/* Venus */
		theta = 75;
		rad = 1.08E11;
		vel = 35000;
		mass = 4.867E24;
		
		sim.create_body2(theta, rad, vel, mass, Color.GREEN);
		
		/* Mercury */
		theta = 100;
		rad = 5.69E10;
		vel = 48000;
		mass = 3.285E23;
		
		sim.create_body2(theta, rad, vel, mass, Color.GRAY);
		
		display.start();
	}
}
