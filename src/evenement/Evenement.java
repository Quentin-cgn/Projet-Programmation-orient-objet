package evenement;

public abstract class Evenement implements Comparable<Evenement> {
	
	private long date;
	
	/**
	 * Constructeur de la classe abstraite Evenement
	 * @param date	date à laquelle l'evenement s'exécute
	 */
	public Evenement(long date) {
		this.date = date;
	}
	
	/**
	 * Accesseur de l'attribut date
	 * @return la date de l'evenement
	 */
	public long getDate() {
		return this.date;
	}
	
	/**
	 * Execution de l'événement concerné
	 */
	public abstract void execute();
	

	// Redéfinition de compareTo et equals pour l'utilisation de Queue dans Simulateur

	@Override
	public int compareTo(Evenement e) {
		if (e==null) {
			throw new NullPointerException();
		}
		long diffDate = e.getDate() - this.getDate();
		if (diffDate < 0) {
			return 1;
		} else if (diffDate > 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Evenement) {
			Evenement ev = (Evenement) other;
			return ev.getDate() == this.getDate();
		}
		return false;
	}
	
	
}
