package io;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import carte.*;
import enums.*;
import incendies.Incendie;
import robots.*;
import simulation.*;


/**
 * Lecteur de cartes au format specifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {

    private static Scanner scanner;
    //private Simulateur simulateur;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees, Simulateur simulateur)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
        //this.simulateur = simulateur;
    }

    /**
     * Crée une instance de DonneesSimulation et la retourne
     * @param fichierDonnees            le nom du fichier où l'on va lire les données
     * @param simulateur                le simualateur qui est utilisé
     * @return                          l'objet DonneesSimulation qui a été créé
     * @throws FileNotFoundException    si le fichier n'a pas été trouvé
     * @throws DataFormatException      si les données ne sont pas dans le bon format
     */
    public static DonneesSimulation creeDonnees(String fichierDonnees, Simulateur simulateur) throws FileNotFoundException, DataFormatException{
        
        // On crée l'instance LecteurDonnees
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees, simulateur);
        ArrayList<Case> listeCaseEau = new ArrayList<Case>();
        
        // On crée la carte
        Carte carteComplete = lecteur.creeCarte(listeCaseEau);
        
        // On crée l'instance DonneesSimulation
        DonneesSimulation donnees = new DonneesSimulation(carteComplete, listeCaseEau);
        
        // On ajoute les robots et les incendies dans l'instance de DonneesSimulation
        lecteur.creeIncendie(donnees);
        lecteur.creeRobots(donnees, simulateur);
        return donnees;
    }

    /**
     * On crée la carte en lisant le fichier et en remplissant toutes les case dans la matrice de la carte
     * @param listeCaseEau  la liste des case contenant de l'eau
     * @return              la carte créée et complète
     * @throws DataFormatException  si les données ne sont pas dans le bon format
     */
    private Carte creeCarte(ArrayList<Case> listeCaseEau) throws DataFormatException{
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m

            // On crée la carte
            Carte carteReturn = new Carte(nbLignes, nbColonnes, tailleCases);
            Case caseAAjouter;

            // On crée toutes les case et on les met dans la carte
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    caseAAjouter = creeCase(lig, col, carteReturn);
                    if (caseAAjouter.getNature() == NatureTerrain.EAU) {
                        listeCaseEau.add(caseAAjouter);
                    }
                    carteReturn.initCarte(caseAAjouter);
                }
            }
            return carteReturn;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
    } 

    /**
     * On crée la case avec ses différents attributs
     * @param lig       son abscisse sur la carte
     * @param col       son ordonnee sur la carte
     * @param carte     la carte
     * @return          la case
     * @throws DataFormatException  si les données ne sont pas dans le bon format
     */
    private Case creeCase(int lig, int col, Carte carte) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();

        try {
            chaineNature = scanner.next();
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            verifieLigneTerminee();
            Case caseReturn = new Case(lig, col, nature, carte);
            return caseReturn;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
    }

    /**
     * Crée un incendie et l'ajoute dans la liste des incendies de l'instance DonneesSimulation
     * @param donnees   l'instance de DonneesSimulation 
     * @throws DataFormatException  si les données ne sont pas dans le bon format
     */
    private void ajouteIncendie(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + "nb litres pour éteindre doit etre > 0");
            }
            verifieLigneTerminee();
            Incendie incendieAAjouter = new Incendie(donnees.getCarte().getCase(lig, col), intensite);
            donnees.ajouterIncendie(incendieAAjouter);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }

    /**
     * Permet d'appeler la fonction ajouteIncendie autant de fois que le nombre d'incendie présents
     * @param donnees
     * @throws DataFormatException  si les données ne sont pas dans le bon format
     */
    private void creeIncendie(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            for (int i = 0; i < nbIncendies; i++) {
                ajouteIncendie(donnees);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
            + "Attendu: nbIncendies");
        }
        
        ignorerCommentaires();
        
    }

    /**
     * Crée un robot et l'ajoute dans la liste des robots de l'instance DonneesSimulation
     * @param donnees       l'instance de DonneesSimulation 
     * @param simulateur    
     * @throws DataFormatException  si les données ne sont pas dans le bon format
     */
    private void ajouteRobots(DonneesSimulation donnees, Simulateur simulateur) throws DataFormatException{
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            String type = scanner.next();

            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");
            Robot robotAAjouter;

            // Si le robot n'a pas une vitesse spécifique
            if (s == null) {
                // En fonction du type de robot, on utilise le bon constructeur
                switch(type) {
                    case "DRONE":
                        robotAAjouter = new Drone(donnees.getCarte().getCase(lig, col), simulateur);
                        break;
                    case "ROUES":
                        robotAAjouter = new RobotRoue(donnees.getCarte().getCase(lig, col), simulateur);
                        break;
                    case "CHENILLES":
                        robotAAjouter = new RobotChenille(donnees.getCarte().getCase(lig, col), simulateur);
                        break;
                    default:
                        robotAAjouter = new RobotPattes(donnees.getCarte().getCase(lig, col), simulateur);
                        break;
                }

            // Si le robot a une vitesse spécifique
            } else {
                int vitesse = Integer.parseInt(s);
                switch(type) {
                    // En fonction du type de robot, on utilise le bon constructeur
                    case "DRONE":
                        robotAAjouter = new Drone(donnees.getCarte().getCase(lig, col), vitesse, simulateur);
                        break;
                    case "ROUES":
                        robotAAjouter = new RobotRoue(donnees.getCarte().getCase(lig, col), vitesse, simulateur);
                        break;
                    case "CHENILLES":
                        robotAAjouter = new RobotChenille(donnees.getCarte().getCase(lig, col), vitesse, simulateur);
                        break;
                    default:
                        robotAAjouter = new RobotPattes(donnees.getCarte().getCase(lig, col), vitesse, simulateur);
                        break;
                }
            }
            verifieLigneTerminee();

            donnees.ajouterRobot(robotAAjouter);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }

    /**
     * Permet d'appeler la fonction ajouteRobot autant de fois que le nombre de robots présents
     * @param donnees
     * @param simulateur
     * @throws DataFormatException  si les données ne sont pas dans le bon format
     */
    private void creeRobots(DonneesSimulation donnees, Simulateur simulateur) throws DataFormatException{
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            for (int i = 0; i < nbRobots; i++) {
                ajouteRobots(donnees, simulateur);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }
    
    /**
     * Permet d'ignorer toute (fin de) ligne commencant par '#'
     */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, données en trop.");
        }
    }
}
