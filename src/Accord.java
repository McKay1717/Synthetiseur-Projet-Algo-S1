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
		// Pour tout objet note dans le tableau notes, faire
		for (Note obj : notes) {
			// Au première indice ayant pour valeur null on attribut la nouvelle
			// note
			if (obj == null) {
				obj = not;
				break;
			}
		}
	}

	/**
	 * Joue l'accord
	 */
	public void play() {
		// Pour tout objet note dans le tableau notes, faire
		for (Note obj : notes) {
			// Si l'objet n'est pas null, on ajoute son signal au signal de
			// l'accord
			if (obj != null) {
				for (int j = 0; j < signal.length; j++) {
					signal[j] += obj.signal[j];
					signal[j] /= notes.length;

				}
			}

		}
		// Création de l'instance du Thread d'affichage
		GuiThread guirunable = new GuiThread(signal);
		Thread guiThread = new Thread(guirunable);
		// Déamarage du Thread d'affichage
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