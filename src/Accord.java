public class Accord {
	Note[] notes;
	double duree;
	double[] signal;

	public Accord(Note note1) {
		notes = new Note[4];
		notes[0] = new Note(note1);
		notes[1] = null;
		notes[2] = null;
		notes[3] = null;
		duree = note1.duree;
		signal = note1.signal.clone();
	}

	public void addNote(Note not) {
		for (Note obj : notes) {
			if (obj == null) {
				obj = not;
				break;
			}
		}
	}

	public void play() {
		for (Note obj : notes) {
			if (obj != null) {
				for (int j = 0; j < signal.length; j++) {
					signal[j] += obj.signal[j];
					signal[j] /= notes.length;

				}
			}

		}
		GuiThread guirunable = new GuiThread(signal);
		Thread guiThread = new Thread(guirunable);
		guiThread.start();
		StdAudio.play(signal);

	}

	public static void main(String[] args) {
		;
		Note newnot = Note.sToNote("do1", 1.0, 1.0, true);
		System.out.println(newnot.freq);
		Accord accord = new Accord(newnot);
		accord.play();

	}
}