package evenement;

import simulation.ChefPompier;

public class ReveilleChefPompier extends Evenement{
	
    private ChefPompier superChef;

    /**
     * Constructeur de l'évenement ReveilleChefPompier
     * @param date  date à laquelle on réveille le chef pompier
     * @param chef  le chef que l'on réveille
     */
    public ReveilleChefPompier(long date, ChefPompier chef){
        super(date);
        this.superChef = chef;
    }

    public void execute() {
		this.superChef.affecteIncendies();
	}
}
