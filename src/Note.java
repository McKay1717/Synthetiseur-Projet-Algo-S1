import java.util.Arrays;

public class Note {
	final static double[] fondFreq = { 32.70, 65.41, 130.81, 261.63, 523.25,
			1046.50, 2093.00, 4186.01 };
	final static String[] tons = { "do", "re", "mi", "fa", "sol", "la", "si" };
	final static int[] haut = { 0, 2, 4, 5, 7, 9, 11 };
	public String toneBase;
	public char alter;
	public int octave;
	public double freq;
	public double duree;
	public double amp;
	double[] signal;

	/**
	 * constructeur permettant de déclarer/allouer une note
	 * 
	 * @param tB
	 *            Le Ton do, re, mi, fa, sol, si
	 * @param alt
	 *            L'ateration b ou #
	 * @param oct
	 *            L'octave
	 * @param dur
	 *            La durée
	 * @param amp
	 *            L'amplitude de la note
	 */
	public Note(String tB, char alt, int oct, double dur, double amp) {
		duree = dur;
		alter = alt;
		toneBase = tB;
		octave = oct;
		freq = freqTone(toneBase, alter, octave);
		signal = new double[(int) Math.round(duree * StdAudio.SAMPLE_RATE)];
		double coef = 2.0 * Math.PI * freq;
		for (int i = 0; i < signal.length; i++) {
			signal[i] = amp * Math.sin(coef * i / StdAudio.SAMPLE_RATE);
		}
	}

	/**
	 * Contructeur de copie
	 * 
	 * @param oldNote
	 *            La note à copier
	 */
	public Note(Note oldNote) {
		duree = oldNote.duree;
		alter = oldNote.alter;
		toneBase = oldNote.toneBase;
		octave = oldNote.octave;
		amp = oldNote.amp;
		freq = oldNote.freq;
		signal = oldNote.signal.clone();
	}

	/**
	 * méthode main() de test de la classe Note
	 * 
	 * @param args
	 *            Liste des arguments de la commande
	 */
	public static void main(String[] args) {
		int i, oct;
		if (args.length < 1)
			oct = 3;
		else
			oct = Integer.parseInt(args[0]);
		for (i = 0; i < 7; i++) {
			Note not = new Note(tons[i], ' ', oct, 1.0, 1.0);
			System.out.print(not.toneBase + ", octave " + not.octave + "  f0 ="
					+ fondFreq[not.octave] + "Hz, F =");
			System.out.format("%.2f Hz%n", not.freq);
			not.play();
		}
	}

	/***
	 * Retroune la note décrite en prametre
	 * 
	 * @param toneBase
	 *            le ton voulu, ex : la si
	 * @param alter
	 *            L'ateration b ou #
	 * @param octave
	 *            L'octave de la note voulu
	 * @return la frequance de la note décrite en parametre sous forme d'un réel
	 */
	private static double freqTone(String toneBase, char alter, int octave) {
		double freq = -1;
		int notePos = -1;
		for (int i = 0; i < tons.length; i++) {
			if (toneBase.contains(tons[i])) {
				freq = fondFreq[octave] * Math.pow(2.0, haut[i] / 12.0);

				notePos = i;
			}

		}
		switch (alter) {

		case '#':
			if (notePos != (tons.length - 1)) {
				freq = freq * Math.pow(2, 1.0 / 12.0);
			} else {
				freq = fondFreq[octave];
			}
			break;
		case 'b':
			if (notePos != 0) {
				freq = freq * Math.pow(2, -1.0 / 12.0);
			} else {
				freq = fondFreq[octave] * Math.pow(2.0, 11 / 12.0);
			}
			break;

		}

		return freq;
	}

	/**
	 * Joue la note
	 */
	public void play() {
		StdAudio.play(signal);
	}

	public static Note sToNote(String tonalite, double amplitude, double duree,
			boolean harmon) {
		char alter = ' ';
		int octave = 0;
		String ton = "";
		char[] Tonalitetab = tonalite.toCharArray();
		// Debug Note
		char[] oct = { '0', '1', '2', '3', '4', '5', '6', '7' };
		for (int i = 0; i < Tonalitetab.length; i++) {
			if (Tonalitetab[i] == 'b') {
				alter = 'b';
				for (int j = 0; j < i; j++) {
					ton += Tonalitetab[j];
				}
			}
			if (Tonalitetab[i] == '#') {
				alter = 'b';
				for (int j = 0; j < i; j++) {
					ton += Tonalitetab[j];
				}
			}
			for (int k = 0; k < oct.length; k++) {
				if (Tonalitetab[i] == oct[k]) {

					octave = Integer.parseInt("" + oct[k]);
				}
			}
		}

		if (ton.length() == 0) {
			ton = tonalite;
		}
		String fomatedton = "";
		char[] tonCharArray = ton.toCharArray();
		for (int m = 0; !(tonCharArray[m] == alter || tonCharArray[m] == ("" + octave)
				.toCharArray()[0]); m++) {

			fomatedton += tonCharArray[m];

		}
		ton = fomatedton.trim();

		Note note = new Note(ton, alter, octave, duree, amplitude);
		if (harmon) {
			double[][] Harmosignal = new double[3][(int) (StdAudio.SAMPLE_RATE * duree)];
			double coef = 2.0 * Math.PI * note.freq;
			Arrays.fill(Harmosignal[0], 0);
			Arrays.fill(Harmosignal[1], 0);
			Arrays.fill(Harmosignal[2], 0);

			for (int j = 0; j < Harmosignal[0].length; j++) {
				Harmosignal[0][j] = amplitude / 4.0
						* Math.sin(coef * (0.5) * j / StdAudio.SAMPLE_RATE);
			}
			for (int k = 0; k < Harmosignal[1].length; k++) {
				Harmosignal[1][k] = amplitude / 4.0
						* Math.sin(coef * (2.0) * k / StdAudio.SAMPLE_RATE);
			}
			for (int l = 0; l < Harmosignal[2].length; l++) {
				Harmosignal[2][l] = amplitude / 8.0
						* Math.sin(coef * (3.0) * l / StdAudio.SAMPLE_RATE);
			}
			for (int g = 0; g < note.signal.length - 1; g++) {
				note.signal[g] += (Harmosignal[0][g] + Harmosignal[1][g] + Harmosignal[2][g]) / 3.0;
			}

		}
		return note;

	}

	public static double faceToDuration(String figure, int tempo) {
		double Figurevalue = 0;
		switch (figure) {
		case "noire":
			Figurevalue = 1;
			break;
		case "blanche":
			Figurevalue = 2;
			break;
		case "ronde":
			Figurevalue = 4;
			break;
		case "croche":
			Figurevalue = 0.5;
			break;
		case "double-croche":
			Figurevalue = 0.25;
			break;
		}
		return (1.0 / (tempo / 60.0)) * Figurevalue;
	}
}