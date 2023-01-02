package evenement;
import robots.Robot;
import nouvellesException.PasEauVoisineException;
import carte.Carte;

public class Remplissage extends Evenement{
        
	private Robot robot;

	/**
	 * Constructeur de l'évenement Remplissage
	 * @param date	date à laquelle le robot va se remplir
	 * @param robot	le robot qui va se remplir
	 */
	public Remplissage(long date, Robot robot) {
		super(date);
		this.robot = robot;
	}
	
    public void execute() {
        try {
			Carte carte = robot.getPosition().getCarte();
			robot.remplirReservoir(carte);
		} catch (PasEauVoisineException e) {
			System.out.println(e);
		}
	}
}
