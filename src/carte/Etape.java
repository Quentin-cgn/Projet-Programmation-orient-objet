package carte;

public class Etape {
	private Case caseEtape;
	private long date;
	
	/**
	 * Constructeur de l'objet Etape
	 * @param caseEtape	
	 * @param date		date à laquelle on se déplace sur la caseEtape
	 */
	public Etape(Case caseEtape, long date) {
		this.caseEtape = caseEtape;
		this.date = date;
	}
		
	/**
	 * Permet d'accéder à la date à laquelle cette étape est réalisée
	 * @return la date à laquelle l'étape est réalisée
	 */
	public long getDate() {
		return this.date;
	}
		
	/**
	 * Permet d'accéder à la case atteinte lors de cette étape
	 * @return la case où l'on va aller à cette étape
	 */
	public Case getCaseEtape () {
		return this.caseEtape;
	}
	
}
