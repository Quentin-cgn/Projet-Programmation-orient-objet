package robots;

import carte.*;
import enums.*;
import simulation.Simulateur;

public class Drone extends Robot {

	/* Constructeur 1 : la vitesse est passée en argument */
	
    public Drone(Case position, int vitesse, Simulateur simulateur){
        super(position, 10000, vitesse, 333, 30, simulateur);
    }
    
    
    /* Constructeur 2 : on utilise la vitesse par défaut */
    
    public Drone(Case position, Simulateur simulateur) {
    	super(position, 10000, 100, 333, 30, simulateur);
    }
    
    
    @Override
    public void setVitesse(int vitesse) {
    	if (vitesse > 150) {
    		super.setVitesse(150);
    	} else {
    		super.setVitesse(vitesse);
    	}
    }
    

    @Override
    public long getVitesse(NatureTerrain nature){
        return super.getVitesse();
    }

    @Override
    public void remplirReservoir(Carte carte){
        if(position.getNature() == NatureTerrain.EAU){
            this.setQuantiteReservoir(this.getCapaciteReservoir());
        }
    }

    @Override
    public String typeRobot(){
        return "drone";
    }

    @Override
    public Chemin caseEauPlusProche() {
        Chemin eauPlusProche = null;
        if (this.getPosition().getNature() == NatureTerrain.EAU) {
        	return new Chemin();
        }
        long tps = 0;
        long min = -2;
        Chemin[][] matrice = this.calculPlusCourtChemin();
        for (Case eau : simulateur.getDonnees().getCaseEau()) {
        
            Chemin chemin = matrice[eau.getLigne()][eau.getColonne()];
            if (chemin.getLastDate() != -1) {
                tps = chemin.getCoutChemin(simulateur.getDate());
                if (min == -2 || tps < min) {
                    min = tps;
                    eauPlusProche = chemin;
                }
            }
        }
        return eauPlusProche;
    }
}