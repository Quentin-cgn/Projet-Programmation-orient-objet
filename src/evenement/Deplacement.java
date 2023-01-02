package evenement;
import robots.Robot;
import nouvellesException.CaseEnDehorsCarteException;
import nouvellesException.CaseInaccessibleException;
import carte.Carte;
import enums.*;

public class Deplacement extends Evenement{
	
	private Robot robot;
	private Direction direction;
	// private Case caseDestination;

	/**
	 * Constructeur de l'evenement Deplacement
	 * @param date				date à laquelle le robot va se déplacer
	 * @param robot				le robot qui va se déplacer
	 * @param direction			la direction vers laquelle le robot va se déplacer
	 */
	public Deplacement(long date, Robot robot, Direction direction) {
		super(date);
		this.robot = robot;
		this.direction = direction;
	}
	
	public void execute() {
		try {
			Carte carte = robot.getPosition().getCarte();
			robot.deplacerRobot(carte, this.direction);
		} catch (CaseInaccessibleException e) {
			System.out.println(e);
		} catch (CaseEnDehorsCarteException e) {
			System.out.println(e);
		}
	}
}
