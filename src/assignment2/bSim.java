package assignment2;

import java.awt.Color;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram {

	private static final int WIDTH = 1200; // n.b. screen coordinates
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final double SCALE = HEIGHT / 100; // pixels per meter
	private static final int NUMBALLS = 5; // # balls to simulate <------------------------------------- =100
	private static final double MINSIZE = 1.0; // Minumum ball radius (meters)
	private static final double MAXSIZE = 10.0; // Maximum ball radius (meters)
	private static final double EMIN = 0.1; // Minimum loss coefficient
	private static final double EMAX = 0.6; // Maximum loss coefficient
	private static final double VoMIN = 40.0; // Minimum velocity (meters/sec)
	private static final double VoMAX = 50.0; // Maximum velocity (meters/sec)
	private static final double ThetaMIN = 80.0; // Minimum launch angle (degrees)
	private static final double ThetaMAX = 100.0; // Maximum launch angle (degrees)
	private static final int GP_HEIGHT = 3; // Maximum launch angle (degrees)

	public void run() {

		setupDisplay();
		for (int i = 0; i < NUMBALLS; i++) {
			
			RandomGenerator rgen = new RandomGenerator();
			double Vo = rgen.nextDouble(VoMIN, VoMAX);
			double theta = rgen.nextDouble(ThetaMIN, ThetaMAX);
			double bSize = (int) rgen.nextDouble(MINSIZE, MAXSIZE);
			double bLoss = rgen.nextDouble(EMIN, EMAX);
			Color bColor = rgen.nextColor();

			aBall ball = new aBall(0, bSize, Vo, theta, bSize, bColor, bLoss, SCALE, WIDTH, HEIGHT);
			add(ball.getBall());
			ball.start();
		}
	}

	private void setupDisplay() {
		this.resize(WIDTH, HEIGHT + OFFSET);

		GRect plane = new GRect(0, HEIGHT, WIDTH, GP_HEIGHT);
		plane.setFilled(true);
		plane.setColor(Color.BLACK);
		add(plane);
	}
}
