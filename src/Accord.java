public class Accord {
	Note[] notes;
	double duree;
	double[] signal;

	/**
	 * Constructeur de la class Accord
	 * 
	 * @param note1
	 *            1 er note de l'accord
	 */
	public Accord(Note note1) {
		notes = new Note[4];
		notes[0] = new Note(note1);
		notes[1] = null;
		notes[2] = null;
		notes[3] = null;
		duree = note1.duree;
		signal = note1.signal.clone();
	}

	/**
	 * Ajoute une note à l'accord
	 * 
	 * @param not
	 *            Objet note à ajouter
	 */
	public void addNote(Note not) {
		boolean exit = false;
		// Pour tout objet note dans le tableau notes, faire
		for (int i = 0; i < notes.length && !exit; i++) {
			// Au premiére indice ayant pour valeur null on attribut la nouvelle
			// note
			if (notes[i] == null) {

				notes[i] = not;
				exit = true;
			}
		}
	}

	/**
	 * Joue l'accord
	 */
	public void play() {
		int count = 0;
		// Pour tout objet note dans le tableau notes, faire
		for (Note obj : notes) {
			// Si l'objet n'est pas null, on ajoute son signal au signal de
			// l'accord

			if (obj != null) {
				count++;

				for (int j = 0; j < signal.length; j++) {
					signal[j] += obj.signal[j];
				}

				for (int k = 0; k < signal.length; k++) {
					signal[k] /= ((double) count + 1);

				}
			}

		}

		// Création de l'instance du Thread d'affichage
		GuiThread guirunable = new GuiThread(signal);
		Thread guiThread = new Thread(guirunable);
		// Démarage du Thread d'affichage
		guiThread.start();
		// Lancement de la lecture du signal
		StdAudio.play(signal);

	}

	/**
	 * Permet de tester le bon fonctionnement des methode de la class Accord
	 * 
	 * @param args
	 *            Liste des arguments de la commande
	 */
	public static void main(String[] args) {

		Note newnot = Note.sToNote("do1", 1.0, 1.0, true);
		System.out.println(newnot.freq);
		Accord accord = new Accord(newnot);
		accord.play();

	}
}
