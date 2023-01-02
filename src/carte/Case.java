package carte;

import enums.NatureTerrain;

public class Case {

    private int ligne;
    private int colonne;
    private NatureTerrain nature;
    private Carte carte;
    
    /**
     * Constructeur de l'objet Case
     * @param ligne     abscisse de la position de la case sur la carte
     * @param colonne   ordonnee de la position de la case sur la carte
     * @param nature    nature de la case
     * @param carte     la carte à laquelle la case appartient
     */
    public Case(int ligne, int colonne, NatureTerrain nature, Carte carte) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = nature;
        this.carte = carte;
    }

    /**
     * 
     * @return l'abscisse de la position de la case
     */
    public int getLigne() {
        return this.ligne;
    }

    /**
     * 
     * @return l'ordonnée de la position de la case
     */
    public int getColonne() {
        return this.colonne;
    }

    /**
     * Retourne la nature du terrain de la case
     * @return
     */
    public NatureTerrain getNature(){
        return this.nature;
    }
    
    /**
     * 
     * @return la carte à laquelle la case appartient
     */
    public Carte getCarte() {
    	return this.carte;
    }
    

    //Redéfinition des méthodes equals et hashCode pour utiliser HashMap dans calculPlusCourtChemin

    @Override
    public boolean equals(Object o) {
    	if (! (o instanceof Case)) {
    		return false;
    	}
    	Case other = (Case) o;
    	return other.ligne == this.ligne && other.colonne == this.colonne && other.nature == this.nature;
    }
    
    @Override
    public int hashCode() {
    	return this.getLigne()*this.getCarte().getNbColonnes() + this.getColonne();
    }
    
}