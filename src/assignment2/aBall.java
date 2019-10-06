package assignment2;

import java.awt.Color;
import acm.graphics.GOval;

public class aBall extends Thread {

	private static final double g = 9.8; // MKS gravitational constant 9.8 m/s^2
	private static final double Pi = 3.141592654; // To convert degrees to radians
	private static final double k = 0.0016;// constant K value
	private static final double ETHR = 0.1;

	private double theta, bSize, bLoss;
	private Color bColor;
	private GOval myBall;

	private int WIDTH, HEIGHT;
	private double scale;

	private double Xi, Yi, X, Y, XLast, YLast;
	private double Vt, Vox, Voy, Vo, Vx, Vy;
	private double KEx, KEy, totalEnergy = 1;
	private double time;
	private boolean canLoop = true;
	private final double TICK = 0.1;
	private int ScrX, ScrY;

	public aBall(double Xi, double Yi, double Vo, double theta, double bSize, Color bColor, double bLoss, double scale,
			int WIDTH, int HEIGHT) {
		this.Xi = Xi;
		this.Yi = Yi;
		this.Vo = Vo;
		this.theta = theta;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.HEIGHT = HEIGHT;
		this.WIDTH = WIDTH;
		this.scale = scale;

		createBall();
		initializeParameters();
	}

	private void createBall() {
		myBall = new GOval(gUtil.meterToPixels(scale, Xi), HEIGHT - gUtil.meterToPixels(scale, Yi * 2),
				gUtil.meterToPixels(scale, bSize * 2), gUtil.meterToPixels(scale, bSize * 2));
		myBall.setFilled(true);
		myBall.setFillColor(this.bColor);
	}

	private void initializeParameters() {
		// Initial parameters
		Vt = g / (4 * Pi * this.bSize * this.bSize * k); // Terminal velocity
		Vox = Vo * Math.cos(this.theta * Pi / 180);// Initial velocity in X
		Voy = Vo * Math.sin(this.theta * Pi / 180);// Initial velocity in Y
	}

	private void calculateVariables() {
		if (Vy < 0 && Y <= bSize) {
			KEx = 0.5 * Vx * Vx * (1 - bLoss); // Kinetic energy in X direction after collision
			KEy = 0.5 * Vy * Vy * (1 - bLoss); // Kinetic energy in Y direction after collision
			Vox = Math.sqrt(2 * KEx); // Resulting horizontal velocity
			Voy = Math.sqrt(2 * KEy); // Resulting vertical velocity
			Xi = XLast;// the offset will be equal to the last coordinate minus the beginning of the
						// simulation
			time = 0;// reset the time
			Yi = bSize;
			YLast = bSize;
			XLast = 0;

			if ((KEx + KEy) < totalEnergy && (KEx + KEy) > ETHR) {
				totalEnergy = KEx + KEy;
			} else {
				canLoop = !canLoop;
			}

		}

		X = Xi + Vox * Vt / g * (1 - Math.exp(-g * time / Vt));// calculate X
		Y = Yi + Vt / g * (Voy + Vt) * (1 - Math.exp(-g * time / Vt)) - Vt * time;// Calculate Y
		Vx = (X - XLast) / TICK;// Calculate horizontal velocity
		Vy = (Y - YLast) / TICK;// calculate vertical velocity

		ScrX = gUtil.meterToPixels(scale, X);// convert to screen units
		ScrY = (HEIGHT - gUtil.meterToPixels(scale, (Y + bSize)));// convert to screen units
		XLast = X;// save last X
		YLast = Y;// save last Y

		myBall.setLocation(ScrX, ScrY);
	}

	public void start() {
		while (canLoop) {
			calculateVariables();
			time += TICK;
			try { // pause for 50 milliseconds
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public GOval getBall() {
		return myBall;
	}
}
