import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/******************************************************************************
 * Compilation: javac Synthe.java Execution: java Synthe input.txt tempo
 * Dependencies: StdAudio.java Accord.java Note.java
 *
 *
 *
 ******************************************************************************/

public class Synthe {

	static int tempo;
	static boolean harm = false;
	static boolean guitar = false;

	public static void main(String[] args) {
		// input.useLocale(Locale.ENGLISH);
		if (args.length < 2) {
			System.out
					.println("Usage : java Synthe <fichier> <tempo> <harm/guitar>");
			System.exit(-1);
		}
		tempo = Integer.parseInt(args[1]);
		if (args.length == 3) {
			if (args[2].equals("harm"))
				harm = true;
			else if (args[2].equals("guitar"))
				guitar = true;
		}

		try {
			BufferedReader fichier = new BufferedReader(new FileReader(args[0]));
			String ligne;
			while ((ligne = fichier.readLine()) != null) {
				playLigne(ligne, harm, guitar);
			}
			fichier.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.exit(0);
	}

	/*
	 * Méthode playLigne a ajouter ici
	 */
	public static void playLigne(String ligne, boolean harm, boolean guitar) {
		Accord acc;
		Note[] notetab = new Note[4];
		// Découpage de la chaine en un tableaux de chaine
		String[] tab = ligne.split(",");
		// Récupération de l'amplitude
		double amp = Double.parseDouble(tab[tab.length - 1]);
		// Récupération du type de note
		String face = tab[tab.length - 2];
		// Evaluation de la durée de l'accord en fonction du type de note
		double duration = Note.faceToDuration(face, tempo);
		// Génération des notes
		for (int i = tab.length - 3; i >= 0; i--) {
			notetab[i] = Note.sToNote(tab[i], amp, duration, harm);
		}

		// Création d'une instance de Accord
		acc = new Accord(notetab[0]);
		// Ajout des autres notes à l'accord
		for (int j = 1; j < notetab.length; j++) {
			acc.addNote(notetab[j]);
		}

		// Joue l'accord
		acc.play();

	}
}
