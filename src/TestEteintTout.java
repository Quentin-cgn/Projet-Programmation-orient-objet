import java.awt.Color;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import enums.Direction;
import evenement.Deplacement;
import evenement.Deversage;
import evenement.EteintIncendie;
import evenement.Remplissage;
import gui.GUISimulator;
import nouvellesException.CaseInaccessibleException;
import simulation.ChefPompier;
import simulation.DonneesSimulation;
import simulation.Simulateur;

public class TestEteintTout {

	public static void main(String[] args) throws FileNotFoundException, DataFormatException, CaseInaccessibleException {
		// crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);
        // crée le simulateur, en l'associant à la fenêtre graphique précédente
        String nomCarte = new String("./cartes/mushroomOfHell-20x20.map");
        Simulateur simulateur = new Simulateur(gui, nomCarte);
        simulateur.getChefPompier().affecteIncendies();
	}

}
