package robots;

import carte.*;
import enums.*;
import nouvellesException.PasEauVoisineException;
import simulation.Simulateur;

public class RobotRoue extends RobotTerrestre {
	
	
	/* Constructeur 1 : le fichier lu précise la vitesse */
	
	public RobotRoue(Case position, int vitesse, Simulateur simulateur) {
		super(position, 5000, vitesse, 20, 10, simulateur);
	}
	
	/* Constructeur 2 : vitesse par défaut du RobotRoue */
	
	public RobotRoue(Case position, Simulateur simulateur) {
		super(position, 5000, 80, 20, 10, simulateur);
	}
	

	@Override
    public long getVitesse(NatureTerrain nature){
        if(nature == NatureTerrain.TERRAIN_LIBRE || nature == NatureTerrain.HABITAT){
            return super.getVitesse();
        }
        return 0;
    }

    @Override
    public void remplirReservoir(Carte carte){
    	try {
    		super.remplirReservoir(carte);
    	} catch (PasEauVoisineException e) {
    		System.out.println(e);
    		return;
    	}

    	this.setQuantiteReservoir(this.getCapaciteReservoir());    	
    }

	@Override
    public String typeRobot(){
        return "roue";
    }
}