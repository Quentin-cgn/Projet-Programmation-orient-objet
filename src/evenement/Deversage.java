package evenement;
import robots.Robot;
import incendies.Incendie;
public class Deversage extends Evenement{
        
	private Robot robot;
    private Incendie incendie;

	/**
	 * Constructeur de l'evenement Deversage
	 * @param date		la date à laquelle on déverse notre eau
	 * @param robot		le robot qui déverse son eau
	 * @param incendie	l'incendie que l'on commence à éteindre
	 */
	public Deversage(long date, Robot robot, Incendie incendie) {
		super(date);
		this.robot = robot;
        this.incendie = incendie;
	}
	
    public void execute() {
		robot.deverserEau(incendie);
	}
}
