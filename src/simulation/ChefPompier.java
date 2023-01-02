package simulation;

import java.util.ArrayList;

import carte.Chemin;
import evenement.EteintIncendie;
import incendies.Incendie;
import robots.Robot;

public class ChefPompier {
    private DonneesSimulation donnees;

    /**
     * Constructeur de l'objet ChefPompier, un chef pompier s'occupe de donner les ordres et de décider quel 
     * incendie attribué à quel robot.
     * @param donnees   Une instance de DonneesSimulation qui contient toutes Les donnees de la simulation
     */
    public ChefPompier(DonneesSimulation donnees) {
        this.donnees = donnees;
    }

    /**
     * Cette méthode permet d'affecter les incendies qui ne sont pas encore éteints à ddes robots
     */
    public void affecteIncendies() {
    	ArrayList<Incendie> listeIncendie = donnees.getIncendies();
        ArrayList<Robot> listeRobot = donnees.getRobots();
        for (Robot robot: listeRobot) {
            if (!robot.estAffecte()) {
                for (Incendie incendie: listeIncendie) {
                    if (incendie.estAffecte() == false) {
                        Chemin chemin = robot.incendiePossible(incendie, donnees.getCarte());
                        if (chemin != null) {
                            robot.changeAffectation();
                            incendie.changeAffectation();
                            robot.getSimulateur().ajouteEvenement(new EteintIncendie(robot.getSimulateur().getDate() + 1, robot, incendie));
                            break;
                        }
                    }
                }
            }
        }
    }
}
