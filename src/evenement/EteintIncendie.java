package evenement;
import robots.Robot;
import incendies.Incendie;

public class EteintIncendie extends Evenement{
        
	private Robot robot;
	private Incendie incendie;

	/**
	 * Constructeur de l'evenement EteintIncendie
	 * @param date		la date à laquelle on commence à éteindre l'incendie
	 * @param robot		le robot qui s'en occupe
	 * @param incendie	l'incendie que l'on va éteindre
	 */
	public EteintIncendie(long date, Robot robot, Incendie incendie) {
		super(date);
		this.robot = robot;
		this.incendie = incendie;
	}
	
    public void execute() {
		robot.eteindreIncendie(incendie);
	}
}
