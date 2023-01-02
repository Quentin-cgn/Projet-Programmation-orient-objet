package incendies;

import carte.*;

public class Incendie {
    private Case posIncendie;
    private int nbLitreEau;
    private int nbLitreNecessaire;
    private boolean affecte;

    /**
     * Constructeur de l'objet Incendie
     * @param pos       la position de l'incendie sur la carte
     * @param litreEau  le nombre de litres d'eau qu'il faut pour l'éteindre
     */
    public Incendie(Case pos, int litreEau){
        this.posIncendie = pos;
        this.nbLitreEau = litreEau;
        this.nbLitreNecessaire = litreEau;
        this.affecte = false;
    }

    /**
     * Permet d'obtenir le nombre de litres d'eau pour 
     * @return le nombre de litres d'eau nécessaire pour éteindre l'incendie
     */
    public int getNbLitreEau() {
        return this.nbLitreEau;
    }

    public int getNbLitreNecessaire(){
        return nbLitreNecessaire;
    }
    
    /**
     * Permet de donner directement le nombre de litres d'eau dans le réservoir
     * (utilisé pour mettre la valeur à 0)
     * @param nouveauNbLitreEau
     */
    public void setNbLitreEau(int nouveauNbLitreEau) {
    	this.nbLitreEau = nouveauNbLitreEau;
    }

    /**
     * Retourne la position de l'incendie
     * @return la case où se trouve l'incendie
     */
    public Case getPosition(){
        return this.posIncendie;
    }

    /**
     * Permet de choisir l'affectation de l'incendie
     * @param b 
     */
    public void setAffectation(boolean b){
        this.affecte = b;
    }

    /**
     * On décrémente le nombre de litre d'eau necessaire pour éteindre l'incendie
     * @param quantiteEau   la quantité d'eau à enlever du nombre de litres d'eau pour 
     *                      éteindre l'incendie
     */
    public void DecrementeLitreEau(int quantiteEau) {
    	if (quantiteEau > this.getNbLitreEau()) {
    		this.setNbLitreEau(0);
    	} else {
    		this.nbLitreEau -= quantiteEau;
    	}
    }
    
    /**
     * Permet de savoir si un incendie a été affecté à un robot 
     * et donc si on est en train de l'éteindre
     * @return  true si il est affecté, false sinon
     */
    public boolean estAffecte() {
    	return this.affecte;
    }
    
    /**
     * Passe l'affectation de l'incendie à true lorsque l'on lui affecte un robot 
     */
    public void changeAffectation() {
    	this.affecte = true;
    }
}