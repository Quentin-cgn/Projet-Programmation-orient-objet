package robots;
import carte.Carte;
import carte.Case;
import enums.*;
import simulation.Simulateur;

public class RobotPattes extends RobotTerrestre {

	
	/* Constructeur 1 : le fichier lu précise la vitesse
	 * On modélise un réservoir de capacité infinie par un réservoir de taille 0 (on ne consultera pas cet attribut lors de la simulation) */
	
	public RobotPattes(Case position, int vitesse, Simulateur simulateur) {
		super(position, -1, vitesse, 10, -1, simulateur);
	}
	
	/* Constructeur 2 : vitesse par défaut du RobotPattes */
	
	public RobotPattes(Case position, Simulateur simulateur) {
		super(position, -1, 30,10, -1, simulateur);
	}
		
	@Override
    public long getVitesse(NatureTerrain nature){
        if(nature == NatureTerrain.ROCHE){
        	if (super.getVitesse() > 10) {
        		return 10;
        	} else {
        		return super.getVitesse();
        	}
        }else if(nature == NatureTerrain.EAU){
            return 0;
        }
        return super.getVitesse();
    }

	@Override
	public void remplirReservoir(Carte carte) {
		return;
	}

	@Override
	public String typeRobot(){
		return "pattes";
	}
	@Override
	public void setQuantiteReservoir(int quantite) {
		return;
	}

}