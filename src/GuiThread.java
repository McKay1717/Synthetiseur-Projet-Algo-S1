public class GuiThread implements Runnable {

	double[] signal;

	public GuiThread(double[] signalin) {
		this.signal = signalin;
	}

	@Override
	public void run() {
		int ECHANTIONAGE = 1;
		StdDraw.setXscale(0, signal.length);
		StdDraw.setYscale(-1, 1);
		StdDraw.show(1);
		StdDraw.clear();

		for (int i = 0; i + ECHANTIONAGE < signal.length; i += ECHANTIONAGE) {
			StdDraw.line(i, signal[i], i + ECHANTIONAGE, signal[i
					+ ECHANTIONAGE]);
		}

	}

}
