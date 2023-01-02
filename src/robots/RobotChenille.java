package robots;
import carte.Carte;
import carte.Case;
import enums.*;
import nouvellesException.PasEauVoisineException;
import simulation.Simulateur;

public class RobotChenille extends RobotTerrestre {
	
	
	/* Constructeur 1 : le fichier lu précise la vitesse */
	
	public RobotChenille(Case position, int vitesse, Simulateur simulateur) {
		super(position, 2000, vitesse, 5, 12, simulateur);
	}
	
	/* Constructeur 2 : vitesse par défaut du RobotChenille */
	
	public RobotChenille(Case position, Simulateur simulateur) {
		super(position, 2000, 60, 5, 12, simulateur);
	}
	
	
	/* Permet de préciser une vitesse maximale propre au RobotChenille */
	
	@Override
	public void setVitesse(int vitesse) {
		if (vitesse > 80) {
			super.setVitesse(80);
		} else {
			super.setVitesse(vitesse);
		}
	}
    
	@Override
    public long getVitesse(NatureTerrain nature){
        if(nature == NatureTerrain.EAU || nature == NatureTerrain.ROCHE){
            return 0;
        }else if(nature == NatureTerrain.FORET){
            return super.getVitesse() / 2;
        }else{
            return super.getVitesse();
        }
    }

    @Override
    public void remplirReservoir(Carte carte) throws PasEauVoisineException {
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
		return "chenille";
	}
}