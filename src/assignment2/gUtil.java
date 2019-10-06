package assignment2;

public final class gUtil {

	public static int meterToPixels(double scale, double meter) {
		return (int) (meter * scale);
	}

	public static int pixelsToMeter(double scale, double pixel) {
		return (int) (pixel / scale);
	}
}
