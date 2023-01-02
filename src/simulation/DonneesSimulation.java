package simulation;
import java.util.ArrayList;

import carte.*;
import robots.*;
import incendies.*;


public class DonneesSimulation {
    protected Carte meilleur_carte;
    protected ArrayList<Incendie> listeIncendie;
    protected ArrayList<Robot> listeRobot;
    protected ArrayList<Case> listeCaseEau;

    /**
     * Constructeur de DonneesSimulation, cette instance contient toutes les données nécessaires, 
     * la position des robots, des incendies, la composition de la carte.
     * @param carte
     * @param listeCaseEau
     */
    public DonneesSimulation(Carte carte, ArrayList<Case> listeCaseEau) {
        this.meilleur_carte = carte;
        this.listeIncendie = new ArrayList<Incendie>();
        this.listeRobot = new ArrayList<Robot>();
        this.listeCaseEau = listeCaseEau;
    }

    /**
     * Méthode permettant d'ajouter un incendie à la liste d'incendie lors de la création 
     * d'une instance DonneesSimulation
     * @param incendie
     */
    public void ajouterIncendie(Incendie incendie) {
        this.listeIncendie.add(incendie);
    }

    /**
     * Méthode permettant d'ajouter un robot à la liste de robots lors de la création 
     * d'une instance DonneesSimulation
     * @param robot
     */
    public void ajouterRobot(Robot robot) {
        this.listeRobot.add(robot);
    }

    /**
     * Retourne la liste de cases contenant de l'eau
     * @return
     */
    public ArrayList<Case> getCaseEau() {
        return this.listeCaseEau;
    }

    /**
     * Retourne la liste des robots présents sur la carte
     * @return
     */
    public ArrayList<Robot> getRobots() {
        return this.listeRobot;
    }

    /**
     * Retourne la liste des incendies présents sur la carte
     * @return
     */
    public ArrayList<Incendie> getIncendies() {
        return this.listeIncendie;
    }

    /**
     * Retourne la carte
     * @return
     */
    public Carte getCarte() {
        return this.meilleur_carte;
    }
}