public class GuiThread implements Runnable {

	double[] signal;

	/**
	 * Constructeur du thread d'affichage
	 * 
	 * @param signalin
	 *            Signal du son joué
	 */

	public GuiThread(double[] signalin) {
		this.signal = signalin;
	}

	/**
	 * Démmare le thread
	 */
	@Override
	public void run() {
		// Intervalle entre chaque point considéré
		int ECHANTIONAGE = 50;
		// Définition du repére
		StdDraw.setXscale(0, signal.length);
		StdDraw.setYscale(-1, 1);
		// Désactivation des animation
		StdDraw.show(1);
		// Nettoyage de la fenetre
		StdDraw.clear();

		// Parcourt le signal et trace une ligne tout les ECHANTIONAGE points
		for (int i = 0; i + ECHANTIONAGE < signal.length; i += ECHANTIONAGE) {
			if(Math.abs(signal[i]) > 1d)
			{
				System.out.println(signal[i]);
			}
			StdDraw.line(i, signal[i], i + ECHANTIONAGE, signal[i
					+ ECHANTIONAGE]);
		}

	}

}
