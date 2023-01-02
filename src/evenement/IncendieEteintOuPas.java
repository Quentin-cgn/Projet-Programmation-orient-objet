package evenement;
import robots.Robot;
import incendies.*;


public class IncendieEteintOuPas extends Evenement{
    private Robot robot;
	private Incendie incendie;

	/**
	 * Constructeur de l'evenement IncendieEteintOuPas
	 * @param date		la date à laquelle regarde si l'incendie est éteint ou pas
	 * @param robot		le robot qui s'occupe de l'incendie
	 * @param incendie	l'incendie qui est peut-être éteint
	 */
	public IncendieEteintOuPas(long date, Robot robot, Incendie incendie) {
		super(date);
		this.robot = robot;
		this.incendie = incendie;
	}
	
    public void execute() {
		robot.vaSeRemplirOuPas(incendie);
	}
}
