import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import simulation.Simulateur;

public class TestAffichage {

	public static void main(String[] args) throws FileNotFoundException, DataFormatException {
		// crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);
        // crée le simulateur, en l'associant à la fenêtre graphique précédente
        String nomCarte = new String("./cartes/desertOfDeath-20x20.map");
        Simulateur simulateur = new Simulateur(gui, nomCarte);
	}

}
