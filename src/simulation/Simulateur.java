package simulation;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
import gui.ImageElement;
import gui.Simulable;
import gui.Text;
import incendies.Incendie;
import io.LecteurDonnees;
import carte.Case;

import java.util.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Carte;
import enums.NatureTerrain;
import evenement.Evenement;
import robots.Robot;


public class Simulateur implements Simulable {
    
	private GUISimulator gui;
	private DonneesSimulation donnees;
	private int factDiv = 2;
	private int tailleAffichageCase;
	private ImageObserver obs;
	private long dateSimulation;
	private Queue<Evenement> serieEvenements;
	private ChefPompier chef;
	
	
	public Simulateur(GUISimulator gui, String fichierDonnees) throws FileNotFoundException, DataFormatException {
		DonneesSimulation donnees = LecteurDonnees.creeDonnees(fichierDonnees, this);
		this.donnees = donnees;
		ImageObserver obs = new ImageObserver() {
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		};
		Carte carteSimulee = donnees.getCarte();
		final int tailleAffichage_case;
		if (carteSimulee.getNbLignes() < carteSimulee.getNbColonnes()) {
			tailleAffichage_case = 975/carteSimulee.getNbColonnes();
		} else {
			tailleAffichage_case = 975/carteSimulee.getNbLignes();
		}
		this.chef = new ChefPompier(donnees);
		this.tailleAffichageCase = tailleAffichage_case;
		this.gui = gui;
		gui.setSimulable(this);
		this.dateSimulation = 0;
		Queue<Evenement> serieEvenements = new PriorityQueue<Evenement>();
		this.serieEvenements = serieEvenements;
		drawCarte(carteSimulee);
		drawRobots(donnees.getRobots(), carteSimulee);
		drawIncendies(donnees.getIncendies(), carteSimulee);
	}
	
	/**
	 * Passage à la date suivante et execution de tous les événements liés à cette date
	 */
	public void next() {
		incrementeDate();
		if ( ! this.simulationTerminee()) {
			while ((! this.simulationTerminee()) && this.serieEvenements.peek().getDate() == this.dateSimulation) {
				Evenement EvenementTraite = this.serieEvenements.poll();
				EvenementTraite.execute();
			}
		}
		gui.reset();
		drawCarte(donnees.getCarte());
		drawRobots(donnees.getRobots(), donnees.getCarte());
		drawIncendies(donnees.getIncendies(), donnees.getCarte());
	}
	
	/**
	 * Remet l'ensemble des données dans l'état initial.
	 */
	public void restart() {
		for (Robot robot : donnees.getRobots()) {
			robot.setPosition(robot.getPositionInit());
			robot.setQuantiteReservoir(robot.getCapaciteReservoir());
			robot.setAffectation(false);
		}
		for (Incendie incendie : donnees.getIncendies()){
			incendie.setNbLitreEau(incendie.getNbLitreNecessaire());
			incendie.setAffectation(false);
		}
		this.serieEvenements = new PriorityQueue<Evenement>();
		gui.reset();
		drawCarte(donnees.getCarte());
		drawRobots(donnees.getRobots(), donnees.getCarte());
		drawIncendies(donnees.getIncendies(), donnees.getCarte());
		this.getChefPompier().affecteIncendies();
	}

	/**
	 * Dessine le robot passé en paramètre sur la carte.
	 * 
	 * @param robot Le robot à dessiner
	 */
	private void  drawRobot(Robot robot) {
		int x = (robot.getPosition().getColonne()) * tailleAffichageCase + tailleAffichageCase/2;
		int y = (robot.getPosition().getLigne()) * tailleAffichageCase + tailleAffichageCase/2;
		switch(robot.typeRobot()) {
			case "roue":
				gui.addGraphicalElement(new ImageElement(x, y - tailleAffichageCase/factDiv, new String("./roue.png"),tailleAffichageCase/factDiv , tailleAffichageCase/factDiv, obs));
				break;
			case "drone":
				gui.addGraphicalElement(new ImageElement(x, y - tailleAffichageCase/factDiv, new String("./drone.png"),tailleAffichageCase/factDiv, tailleAffichageCase/factDiv, obs));
				break;
			case "pattes":
				gui.addGraphicalElement(new ImageElement(x, y - tailleAffichageCase/factDiv, new String("./pattes.png"),tailleAffichageCase/factDiv, tailleAffichageCase/factDiv, obs));
				break;
			case "chenille":
				gui.addGraphicalElement(new ImageElement(x, y - tailleAffichageCase/factDiv, new String("./chenille.png"),tailleAffichageCase/factDiv, tailleAffichageCase/factDiv, obs));
		}
	}

	/**
	 * Dessine l'incendie passé en paramètre.
	 * 
	 * @param incendie L'incendie à dessiner
	 */
	private void  drawIncendie(Incendie incendie) {
		if (incendie.getNbLitreEau()!=0) {
			int x = (incendie.getPosition().getColonne()) * tailleAffichageCase + tailleAffichageCase/2;
			int y = (incendie.getPosition().getLigne()) * tailleAffichageCase + tailleAffichageCase/2;
			
			gui.addGraphicalElement(new ImageElement(x - tailleAffichageCase/factDiv, y - tailleAffichageCase/factDiv, new String("./feu.png"), tailleAffichageCase/factDiv, tailleAffichageCase/factDiv, obs));	
		}
	}

	
	/**
	 * Dessine la case passée en paramètre.
	 * 
	 * @param caseDessinee La case dessinée
	 */
	private void drawCase(Case caseDessinee) {
		int x = (caseDessinee.getColonne()) * tailleAffichageCase + tailleAffichageCase/2;
		int y = (caseDessinee.getLigne()) * tailleAffichageCase + tailleAffichageCase/2;
		
		switch (caseDessinee.getNature()) {
			case EAU:
				gui.addGraphicalElement(new ImageElement(x - tailleAffichageCase/2, y - tailleAffichageCase/2, new String("./eau.jpg"), tailleAffichageCase, tailleAffichageCase, obs));
				break;
			case FORET:
				gui.addGraphicalElement(new ImageElement(x - tailleAffichageCase/2, y - tailleAffichageCase/2, new String("./foret.jpg"), tailleAffichageCase, tailleAffichageCase, obs));
				break;
			case ROCHE:
				gui.addGraphicalElement(new ImageElement(x - tailleAffichageCase/2, y - tailleAffichageCase/2, new String("./rocher.png"), tailleAffichageCase, tailleAffichageCase, obs));
				break;
			case TERRAIN_LIBRE:
				gui.addGraphicalElement(new ImageElement(x - tailleAffichageCase/2, y - tailleAffichageCase/2, new String("./herbe.png"), tailleAffichageCase, tailleAffichageCase, obs));
				break;
			case HABITAT:
				gui.addGraphicalElement(new ImageElement(x - tailleAffichageCase/2, y - tailleAffichageCase/2, new String("./herbe.png"), tailleAffichageCase, tailleAffichageCase, obs));
				gui.addGraphicalElement(new ImageElement(x - tailleAffichageCase/2, y - tailleAffichageCase/2, new String("./maison.png"), tailleAffichageCase, tailleAffichageCase, obs));
				break;
		}
	}

	
	
	/**
	 * Dessine la carte. Pour cela on dessine l'ensemble des cases qui la constituent.
	 * 
	 * @param carteDessinee La carte à dessiner
	 */
	private void drawCarte(Carte carteDessinee) {
		int nbLignes = carteDessinee.getNbLignes();
		int nbColonnes = carteDessinee.getNbColonnes();
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {
				Case caseDessinee = carteDessinee.getCase(i, j);
				drawCase(caseDessinee);
			}
		}
	}

	/**
	 * Dessine l'ensemble des Robots sur la carte.
	 * 
	 * @param listeRobot La liste de Robots à dessiner
	 * @param carteDessinee La carte sur laquelle dessiner
	 */
	private void drawRobots(ArrayList<Robot> listeRobot, Carte carteDessinee) {
		Iterator<Robot> it = listeRobot.iterator();
		while (it.hasNext()) {
			Robot robot = it.next();
			drawRobot(robot);
		}   
	}

	/**
	 * Dessine l'ensemble des incendies sur la carte.
	 * 
	 * @param listeIncendie La liste des Incendies à dessiner
	 * @param carteDessinee La carte sur laquelle dessiner
	 */
	private void drawIncendies(ArrayList<Incendie> listeIncendie, Carte carteDessinee) {
		Iterator<Incendie> it = listeIncendie.iterator();
		while (it.hasNext()) {
			Incendie incendie = it.next();
			drawIncendie(incendie);
		}
	}
	
	
	/**
	 * Ajoute un événement au gestionnaire d'événements.
	 * 
	 * @param e L'événement à ajouter au gestionnaire.
	 */
	public void ajouteEvenement(Evenement e) {
		this.serieEvenements.add(e);
	}
	
	/**
	 * Augmente la date actuelle d'une unité.
	 */
	private void incrementeDate() {
		this.dateSimulation += 1;
	}
	
	/**
	 * Indique si la simulation est terminée (c'est à dire si la pile d'événements est vide).
	 * 
	 * @return
	 */
	private boolean simulationTerminee() {
		if (this.serieEvenements.peek() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Accesseur pour le chef pompier.
	 * 
	 * @return
	 */
	public ChefPompier getChefPompier(){
		return this.chef;
	}
 	
	/**
	 * Accesseur pour la date actuelle.
	 * 
	 * @return
	 */
	public long getDate() {
		return this.dateSimulation;
	}

	/**
	 * Accesseur pour les données de simulation.
	 * 
	 * @return
	 */
	public DonneesSimulation getDonnees() {
		return this.donnees;
	}

	/**
	 * Donne le nombre d'événements programmés dans le gestionnaire d'événements.
	 * 
	 * @return
	 */
	public int getTailleSerieEvenement() {
		return this.serieEvenements.size();
	}
	
}
