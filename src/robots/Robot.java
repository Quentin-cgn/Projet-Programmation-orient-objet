package robots;

import java.util.HashMap;
import java.util.LinkedList;

import incendies.Incendie;
import nouvellesException.CaseEnDehorsCarteException;
import nouvellesException.CaseInaccessibleException;
import nouvellesException.PasEauVoisineException;
import nouvellesException.QuantiteEauReservoirException;
import simulation.Simulateur;
import carte.*;
import enums.*;
import evenement.*;

public abstract class Robot {
    /** temps remplissage en secondes et débit en L/s */
    protected Case position;
    protected int quantiteReservoir;
    protected int vitesse;
    private boolean affecte;
    protected Simulateur simulateur;
    private int debitDeversage;
    private long tempsRemplissage;
    private Case position_init;
    private int capaciteReservoir;

    /**
     * Constructeur d'un Robot
     * 
     * @param position
     * @param quantiteReservoir
     * @param vitesse
     * @param debitDeversage
     * @param tempsRemplissage
     * @param simulateur
     */
    public Robot(Case position, int quantiteReservoir, int vitesse, int debitDeversage, long tempsRemplissage,
            Simulateur simulateur) {
        this.position = position;
        this.quantiteReservoir = quantiteReservoir;
        setVitesse(vitesse);
        this.affecte = false;
        this.simulateur = simulateur;
        this.debitDeversage = debitDeversage;
        this.tempsRemplissage = tempsRemplissage;
        this.position_init = position;
        this.capaciteReservoir = quantiteReservoir;
    }

    /**
     * Accesseur pour la vitesse du Robot sur le type de terrain passé en
     * paramètres.
     * 
     * @param nature
     * @return
     */
    public abstract long getVitesse(NatureTerrain nature);

    /**
     * Remplissage du réservoir du Robot si ce dernier est sur une case appropriée
     * (une case eau pour un drone ou une case voisine de l'eau pour les autres
     * Robots).
     * 
     * @param carte
     * @throws PasEauVoisineException
     */
    public abstract void remplirReservoir(Carte carte) throws PasEauVoisineException;

    /**
     * Accesseur pour la chaîne de caractère correspondant au type du Robot.
     * 
     * @return
     */
    public abstract String typeRobot();

    /**
     * Accesseur pour le simulateur de la simulation à laquelle appartient le Robot.
     * 
     * @return
     */
    public Simulateur getSimulateur() {
        return this.simulateur;
    }

    /**
     * Accesseur pour la vitesse (si le terrain le permet) du Robot.
     * 
     * @return
     */
    public int getVitesse() {
        return this.vitesse;
    }

    /**
     * Accesseur pour la position du Robot.
     * 
     * @return
     */
    public Case getPosition() {
        return this.position;
    }

    /**
     * Accesseur pour la quantité d'eau dans le réservoir du Robot.
     * 
     * @return
     */
    public int getQuantiteReservoir() {
        return this.quantiteReservoir;
    }

    /**
     * Accesseur pour le temps de remplissage du Robot.
     * 
     * @return
     */
    public long getTempsRemplissage() {
        return this.tempsRemplissage;
    }

    /**
     * Renvoie le témoin d'affectation du Robot.
     * Le booléen renvoyé sera true si le Robot est déjà occupé (pour aller se
     * remplir ou éteindre un incendie).
     * Le booléen renvoyé sera false si le Robot est disponible.
     * 
     * @return
     */
    public boolean estAffecte() {
        return this.affecte;
    }

    /**
     * Mutateur pour la vitesse.
     * 
     * @param vitesse
     */
    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * Mutateur pour la position du Robot.
     * 
     * @param nouvPos
     */
    public void setPosition(Case nouvPos) {
        this.position = nouvPos;
    }

    public Case getPositionInit() {
        return position_init;
    }
 
    public int getCapaciteReservoir() {
        return this.capaciteReservoir;
    }

    /**
     * Mutateur pour la quantité d'eau dans le réservoir.
     * 
     * @param quantite
     * @throws QuantiteEauReservoirException
     */
    public void setQuantiteReservoir(int quantite) {
        try {
            if (quantite >= 0) {
                this.quantiteReservoir = quantite;
            } else {
                throw new QuantiteEauReservoirException("La quantité d'eau dans le réservoir d'un Robot doit être positive !");
            }
        } catch (QuantiteEauReservoirException e) {
            System.out.println(e);
        }
    }

    /**
     * Diminue la quantité d'eau dans le réservoir de "quantite" Litres d'eau.
     * Si cela est impossible alors la quantité d'eau est remise à 0.
     * 
     * @param quantite La quantité d'eau à retirer du réservoir
     */
    public void decrementeQuantiteReservoir(int quantite) {
        if (this.getQuantiteReservoir() > quantite) {
            this.quantiteReservoir -= quantite;
        } else {
            this.setQuantiteReservoir(0);
        }

    }

    /**
     * Change le témoin d'occupation booléen du Robot.
     */
    public void changeAffectation() {
        if (this.estAffecte()) {
            this.affecte = false;
        } else {
            this.affecte = true;
        }
    }

    /**
     * Permet de choisir l'affectation du robot
     * @param b
     */
    public void setAffectation(boolean b){
        this.affecte = b;
    }

    /**
     * Méthode vérifiant si la case dans laquelle on souhaite déplacer le robot est
     * valide, puis changeant la position du robot si tout est bon
     * 
     * @throws CaseInaccessibleException
     */
    public void deplacerRobot(Carte carte, Direction dir) throws CaseInaccessibleException, CaseEnDehorsCarteException {
        if (!carte.voisinExiste(position, dir)) {
            throw new CaseEnDehorsCarteException("Le robot essaye de sortir de la carte");
        } else if(this.getVitesse(carte.getVoisin(position, dir).getNature()) == 0 ) {
            throw new CaseInaccessibleException("Ce robot ne peut pas ce rendre sur cette case...");
        }
        this.setPosition(carte.getVoisin(position, dir));
    }

    /**
     * Méthode ajoutant l'ensemble des étapes d'un chemin passé en paramètre au
     * gestionnaire d'événements.
     * 
     * @param chemin Le chemin que doit parcourir le Robot
     */
    private void ajouteEvPourDeplacerRobot(Chemin chemin) {
        LinkedList<Etape> listeEtapes = chemin.getChemin();
        int caseCouranteAbs;
        int caseCouranteOrd;
        int caseSuivanteAbs;
        int caseSuivanteOrd;
        for (int i=-1; i<listeEtapes.size()-1; i++) {
            if (i==-1) {
                caseCouranteAbs = position.getLigne();
                caseCouranteOrd = position.getColonne();
                caseSuivanteAbs = listeEtapes.get(i+1).getCaseEtape().getLigne();
                caseSuivanteOrd = listeEtapes.get(i+1).getCaseEtape().getColonne();
            } else {
                caseCouranteAbs = listeEtapes.get(i).getCaseEtape().getLigne();
                caseCouranteOrd = listeEtapes.get(i).getCaseEtape().getColonne();
                caseSuivanteAbs = listeEtapes.get(i+1).getCaseEtape().getLigne();
                caseSuivanteOrd = listeEtapes.get(i+1).getCaseEtape().getColonne();
            }
            Direction dir = caseCouranteAbs==caseSuivanteAbs?
                                    (caseCouranteOrd<caseSuivanteOrd?Direction.EST:Direction.OUEST):
                                    (caseCouranteAbs<caseSuivanteAbs?Direction.SUD:Direction.NORD);
            this.simulateur.ajouteEvenement(new Deplacement(listeEtapes.get(i+1).getDate(), this, dir));
        }
    }

    /**
     * Méthode permettant de déverser le volume d'eau passé en paramètre.
     * Dans le cas où le volume d'eau serait plus important que la quantité d'eau
     * présente dans le réservoir, le réservoir est vidé en intégralité sur la case.
     * 
     * @param incendie
     */
    public void deverserEau(Incendie incendie) {
        if (this.getQuantiteReservoir() == -1) {
            incendie.setNbLitreEau(0);
        } else {
            int eauNecessaire = incendie.getNbLitreEau();
            if (this.quantiteReservoir >= eauNecessaire) {
                incendie.setNbLitreEau(0);
                this.decrementeQuantiteReservoir(eauNecessaire);
            } else {
                incendie.DecrementeLitreEau(this.quantiteReservoir);
                this.setQuantiteReservoir(0);
            }
        }
    }

    /**
     * Retourne le chemin vers la case d'eau la plus proche.
     * Ce chemin va jusque sur une case d'eau si jamais le Robot est un drone ou au
     * voisinage d'une case d'eau sinon.
     * Ce chemin est null si aucune case d'eau n'est accessible.
     * 
     * @return
     */
    public abstract Chemin caseEauPlusProche() ;

    /**
    * Méthode permettant d'ajouter au gestionnaire d'événements le déplacement vers
    * l'eau la plus proche et le remplissage du réservoir du Robot.
    *
    * @param init Booléen permettant d'indiquer à la fonction si le remplissage à programmer est un remplissage initial ou non
    * @return
     */
    public long ajouteEvPourRemplirRobot() {
        Carte carte = simulateur.getDonnees().getCarte();
        long dateRemplissage = simulateur.getDate() + 1;
        
        for (Direction dir : Direction.values()) {
            if (carte.voisinExiste(position, dir) && carte.getVoisin(position, dir).getNature() == NatureTerrain.EAU) {
                simulateur.ajouteEvenement(new Remplissage(dateRemplissage, this));
                return dateRemplissage;
            }
        }
        Chemin chemin = caseEauPlusProche();
        ajouteEvPourDeplacerRobot(chemin);
        dateRemplissage = dateRemplissage + chemin.getCoutChemin(simulateur.getDate());
        simulateur.ajouteEvenement(new Remplissage(dateRemplissage, this));
        return dateRemplissage;
    }

    /**
     * Méthode déterminant la nécessité ou non d'aller se remplir pour le Robot.
     * Si c'est nécessaire, on programme le remplissage.
     * Si l'incendie auquel est affecté le Robot n'est toujours pas éteint, on programme 
     * également un déplacement et un déversage sur cet incendie.
     * @param incendie Incendie auquel est affecté le Robot.
     */
    public void vaSeRemplirOuPas(Incendie incendie) {
        boolean remplir = false;
        long dateRemplissage = 0;
        if (this.quantiteReservoir == 0) {
            remplir = true;
            dateRemplissage = ajouteEvPourRemplirRobot();
        }
        long dateProchainEv;
        if (remplir) {
            dateProchainEv = dateRemplissage + this.getTempsRemplissage() * 60 + 1;
        } else {
            dateProchainEv = simulateur.getDate() + 1;
        }
        if (incendie.getNbLitreEau() > 0) {
            simulateur.ajouteEvenement(new EteintIncendie(dateProchainEv, this, incendie));
        } else {
            this.changeAffectation();
            simulateur.ajouteEvenement(new ReveilleChefPompier(dateProchainEv, simulateur.getChefPompier()));
        }
    }

    /**
    * Méthode permettant de programmer (dans le gestionnaire d'événements) le déplacement du Robot vers un incendie, ainsi
    * que le déversage de la quantité d'eau nécessaire sur la case.   
    *
    * @param incendie L'incendie auquel a été affecté le Robot (qu'il doit donc éteindre)
    * @return
     */
    public void eteindreIncendie(Incendie incendie) {
        Chemin chemin = this.calculPlusCourtChemin()[incendie.getPosition().getLigne()][incendie.getPosition().getColonne()];
        ajouteEvPourDeplacerRobot(chemin);
        long dateDeversage = simulateur.getDate() + chemin.getCoutChemin(simulateur.getDate()) + 2;
        int nbLitreEauAdeverser = (this.getQuantiteReservoir() == -1 ? incendie.getNbLitreEau()
                : (this.getQuantiteReservoir() >= incendie.getNbLitreEau() ? incendie.getNbLitreEau()
                        : this.getQuantiteReservoir()));
        long dateFinDeversage = dateDeversage + (nbLitreEauAdeverser / this.debitDeversage) + 1;
        this.simulateur.ajouteEvenement(new Deversage(dateFinDeversage, this, incendie));
        this.simulateur.ajouteEvenement(new IncendieEteintOuPas(dateFinDeversage, this, incendie));
    }

    /**
     * Méthode utilisée pour trouver la case la plus proche (en terme de temps) que
     * l'on a pas encore visitée/validée lors d'itération de l'algorithme de
     * Dijkstra.
     * 
     * @param carte Carte sur laquelle évolue le Robot
     * @param matrice[][] Matrice contenant le chemin pour se rendre à chaque case de la carte
     * @param casesDejaVisitees Table de hachage répertoriant les cases déjà "marquées" par l'algorithme de Dijkstra
     * @return
     */

    private int[] recupereCasePlusCourte(Carte carte, Chemin matrice[][], HashMap<Case, Boolean> casesDejaVisitees) {
        double coutMin = -1;
        int[] coordonnees = { -1, -1 };
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                Case caseEtudiee = carte.getCase(i, j);
                if (!casesDejaVisitees.containsKey(caseEtudiee)) {
                	long coutChemin = matrice[i][j].getCoutChemin(simulateur.getDate());
                	if (coutChemin >= 0) {
	                    if (coutChemin < coutMin || coutMin < 0) {
	                        coutMin = coutChemin;   
	                        coordonnees[0] = i;
	                        coordonnees[1] = j;
	                    }
                	}
                }
            }
        }
        return coordonnees;
    }

    /**
     * Méthode permmettant de trouver le temps mi par le robot pour arriver sur chaque case de la matrice
     * On utilise pour cela l'algorithme de Dijkstra.
     *  
     * Le choix de l'unité de temps est la seconde en supposant que la taille de la
     * carte soit donnée en mètres
     * 
     * @return  la matrice contenant les chemins et les couts des chemins vers les différents 
     *          pour aller sur chaque case de la carte
     */

    public Chemin[][] calculPlusCourtChemin() {
        // Initialisation du problème (matrice et table de hachage pour savoir les cases
        // visitées par l'algo)
        Carte carte = simulateur.getDonnees().getCarte();
        int nbLig = carte.getNbLignes();
        int nbCol = carte.getNbColonnes();
        Chemin matrice[][] = new Chemin[nbLig][nbCol];
        HashMap<Case, Boolean> casesDejaVisitees = new HashMap<Case, Boolean>();
        for (int i = 0; i < nbLig; i++) {
            for (int j = 0; j < nbCol; j++) {
                matrice[i][j] = new Chemin();
            }
        }
        // La case de départ est initialisée dans la matrice
        int ligDep = this.getPosition().getLigne();
        int colDep = this.getPosition().getColonne();
        Case depart = this.getPosition();
        casesDejaVisitees.put(depart, true);
        MetAJourDistances(depart, matrice, casesDejaVisitees, matrice[ligDep][colDep]);
        // Itérations de l'algorithme
        while (casesDejaVisitees.size() < matrice.length*matrice[0].length) {
            // Récupération de la case la plus proche et marquage
            int tab[] = recupereCasePlusCourte(carte, matrice, casesDejaVisitees);
            int ligne = tab[0];
            int colonne = tab[1];
            if (ligne==-1) return matrice;
            Case aTraiter = carte.getCase(ligne, colonne);
            Chemin cheminInitial = matrice[ligne][colonne];
            casesDejaVisitees.put(aTraiter, true);
            // Mise à jour des distances (temporelles) avec les voisins accessibles de la
            // case aTraiter
            MetAJourDistances(aTraiter, matrice, casesDejaVisitees, cheminInitial);
        }
        return matrice;
    }

    
    /**
     * Met à jour les distances des cases voisines des cases marquées dans l'algorithme de Dijkstra.
     * 
     * @param aTraiter Case que l'on est en train de marquer
     * @param matrice Matrice des coûts des chemins à partir de la case de départ qu'on considère dans l'algorithme de Dijkstra
     * @param casesDejaVisitees Cases déjà marquées stockées dans une table de hachage
     * @param cheminInitial Chemin menant à la case que l'on est en train de marquer (la case aTraiter)
     */
    private void MetAJourDistances(Case aTraiter, Chemin matrice[][], HashMap<Case, Boolean> casesDejaVisitees, Chemin cheminInitial) {
    	Carte carte = this.getSimulateur().getDonnees().getCarte();
    	for (Direction dir : Direction.values()) {
    		
            // On détermine si le voisin est accessible
            if (carte.voisinExiste(aTraiter, dir)) {
            	double vitesseVoisin = this.getVitesse(carte.getVoisin(aTraiter, dir).getNature());
            	if (vitesseVoisin != 0) {
	                Case voisin = carte.getVoisin(aTraiter, dir);
	                int ligVoi = voisin.getLigne();
	                int colVoi = voisin.getColonne();
	                Chemin voisinChem = matrice[ligVoi][colVoi];
	                // Si le voisin n'est pas déjà marqué, on met à jour le temps nécessaire pour
	                // l'atteindre depuis la case aTraiter
	                if (!casesDejaVisitees.containsKey(carte.getCase(ligVoi, colVoi))) {
	                    double vitesse = this.getVitesse(aTraiter.getNature());
	                    double tps = ((carte.getTailleCases() / (2 * vitesse)
	                            + (carte.getTailleCases()) / (2 * vitesseVoisin))) * 3600 / 1000;
	                    // Si le temps mis en passant par cette case est meilleur que le temps précédent
	                    // d'accès à voisin, on remplace l'ancien chemin par celui ci
	                    if (voisinChem.getCoutChemin(simulateur.getDate()) == -1
	                            || voisinChem.getCoutChemin(simulateur.getDate()) > cheminInitial.getCoutChemin(simulateur.getDate()) + tps) {
	                        LinkedList<Etape> nvChem = new LinkedList<>();
	                        for (Etape etape : cheminInitial.getChemin()) {
	                            nvChem.add(etape);
	                        }
	                        voisinChem.setChemin(nvChem);
	                        if (cheminInitial.getLastDate() == -1) {
	                        	voisinChem.ajouteEtapeFin(voisin, (long) (this.getSimulateur().getDate() + tps));
	                        } else {
	                        	voisinChem.ajouteEtapeFin(voisin, (long) (cheminInitial.getLastDate() + tps));
	                        }
	                        
	                    }
	                }
	            }
          	}
        }
    }

    /**
    * Méthode servant à calculer le plus court chemin vers un incendie (retourne null si aucun chemin n'existe)
    *
    * @param incendie L'incendie vers lequel on souhaite se rendre
    * @param carte La carte de la simulation (la carte sur laquelle évolue le Robot)
    *
    * @return
     */
    public Chemin incendiePossible(Incendie incendie, Carte carte) {
        Chemin matrice[][] = this.calculPlusCourtChemin();
    
            if (matrice[incendie.getPosition().getLigne()][incendie.getPosition().getColonne()].getLastDate() == -1) {
                
                return null;
            } else {
                return matrice[incendie.getPosition().getLigne()][incendie.getPosition().getColonne()];
            }
    }
}