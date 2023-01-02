package robots;

import nouvellesException.PasEauVoisineException;
import simulation.Simulateur;
import carte.*;
import enums.*;

public abstract class RobotTerrestre extends Robot {
    
	
	public RobotTerrestre(Case position, int capaciteReservoir, int vitesse, int debitDeversage, long tempsRemplissage, Simulateur simulateur) {
		super(position, capaciteReservoir, vitesse, debitDeversage, tempsRemplissage, simulateur);
	}
	
	@Override
	 public void remplirReservoir(Carte carte) throws PasEauVoisineException {
		 Case positionRobot = this.getPosition();
		 if (!(carte.natureEauCaseVoisine(positionRobot))) {
			throw new PasEauVoisineException("Il n'y a pas d'eau à proximité, vous ne pouvez pas remplir le réservoir !");
		 }
	 }

	@Override
	public Chemin caseEauPlusProche() {
		Carte carte = simulateur.getDonnees().getCarte();
		Chemin eauPlusProche = null;
		if (this.getPosition().getNature() == NatureTerrain.EAU) {
			return new Chemin();
		}
		long tps = 0;
		long min = -2;
		Chemin[][] matrice = this.calculPlusCourtChemin();
		for (Case eau : simulateur.getDonnees().getCaseEau()) {
			for (Direction dir : Direction.values()) {
				if (carte.voisinExiste(eau, dir) && carte.getVoisin(eau, dir).getNature() != NatureTerrain.EAU
												&& matrice[carte.getVoisin(eau, dir).getLigne()][carte.getVoisin(eau, dir).getColonne()].getCoutChemin(simulateur.getDate())!= -1) {
					Chemin temp = matrice[carte.getVoisin(eau, dir).getLigne()][carte.getVoisin(eau, dir).getColonne()];
					tps = temp.getCoutChemin(simulateur.getDate());
					if (min == -2 || tps < min) {
						min = tps;
						eauPlusProche = temp;
					}
				}
			}
		}
		return eauPlusProche;
	}
}

