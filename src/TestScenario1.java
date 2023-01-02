import java.awt.Color;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import evenement.Deplacement;
import evenement.Deversage;
import evenement.EteintIncendie;
import evenement.Remplissage;
import carte.Carte;
import carte.Case;
import robots.Robot;
import simulation.ChefPompier;
import simulation.DonneesSimulation;
import simulation.Simulateur;
import enums.Direction;
import gui.GUISimulator;
import incendies.Incendie;
import nouvellesException.CaseInaccessibleException;

public class TestScenario1 {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException, CaseInaccessibleException {
		// crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);
        // crée le simulateur, en l'associant à la fenêtre graphique précédente
        String nomCarte = new String("./cartes/carteSujet.map");
        Simulateur simulateur = new Simulateur(gui, nomCarte);
        
        Case cas = simulateur.getDonnees().getCarte().getCase(5, 5);
        Robot robot = simulateur.getDonnees().getRobots().get(1);
        simulateur.ajouteEvenement(new Deplacement(1, robot, Direction.NORD));
        Incendie ind = null;
        for(Incendie i : simulateur.getDonnees().getIncendies()){
            if(i.getPosition() == cas){
                ind = i;
                break;
            }
        }
        simulateur.ajouteEvenement(new Deversage(5, robot, ind));
        simulateur.ajouteEvenement(new Deplacement(7, robot, Direction.OUEST));
        simulateur.ajouteEvenement(new Deplacement(8, robot, Direction.OUEST));
        simulateur.ajouteEvenement(new Remplissage(9, robot));
        simulateur.ajouteEvenement(new Deplacement(10, robot, Direction.EST));
        simulateur.ajouteEvenement(new Deplacement(11, robot, Direction.EST));
        simulateur.ajouteEvenement(new Deversage(14, robot, ind));
    }
}
