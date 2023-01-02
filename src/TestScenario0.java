import java.awt.Color;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import robots.Robot;
import simulation.ChefPompier;
import simulation.DonneesSimulation;
import simulation.Simulateur;
import enums.Direction;
import evenement.Deplacement;
import evenement.Deversage;
import evenement.EteintIncendie;
import evenement.Remplissage;
import gui.GUISimulator;
import nouvellesException.CaseInaccessibleException;


public class TestScenario0 {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException, CaseInaccessibleException {
		// crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);
        // crée le simulateur, en l'associant à la fenêtre graphique précédente
        String nomCarte = new String("./cartes/carteSujet.map");
        Simulateur simulateur = new Simulateur(gui, nomCarte);

        Robot drone = simulateur.getDonnees().getRobots().get(0);
        Carte carte = simulateur.getDonnees().getCarte();
        Case pos = drone.getPosition();
        simulateur.ajouteEvenement(new Deplacement(1, drone, Direction.NORD));
        simulateur.ajouteEvenement(new Deplacement(2, drone, Direction.NORD));
        simulateur.ajouteEvenement(new Deplacement(3, drone, Direction.NORD));
        simulateur.ajouteEvenement(new Deplacement(4, drone, Direction.NORD));
    }
}
