package carte;

import java.util.LinkedList;

public class Chemin {
    private LinkedList<Etape> chemCases;
    
    /**
     * Constructeur de l'objet Chemin. 
     * Un chemin est une suite d'étapes (voir la classe Etape) qui concernent des cases obligatoirement adjacentes et dont les dates sont croissantes. 
     */
    public Chemin() {
        this.chemCases = new LinkedList<Etape>();
    }
    
    /**
     * Permet d'accéder au coût total en minutes nécessaire pour parcourir ce chemin
     * @return le coût total
     */
    public long getCoutChemin(long dateActuelle)  {
        LinkedList<Etape> chemin = this.getChemin();
        if (chemin.size() == 0) {
        	return -1;
        } else {
        	long derniereDate = chemin.getLast().getDate();
        	return derniereDate - dateActuelle;
        }
    }

    /**
     * Permet d'accéder à la liste d'étapes constituant le chemin
     * @return la liste d'étape
     */
    public LinkedList<Etape> getChemin() {
        return chemCases;
    }
        
    /**
     * Permet de changer la liste d'étape d'un objet chemin à partir d'une liste d'étapes
     * @param nvChemin  la nouvelle liste d'étapes
     */
    public void setChemin(LinkedList<Etape> nvChemin) {
        this.chemCases = nvChemin;
    }

    /**
     * Permet d'ajouter une étape à la fin du chemin
     * @param caseAAjouter  la case où l'on va se déplacer
     * @param date          la date à laquelle on va se déplacer sur la case
     */
    public void ajouteEtapeFin(Case caseAAjouter, long date) {
    	Etape nouvelleEtape = new Etape(caseAAjouter, date);
        this.chemCases.add(nouvelleEtape);
    }
   
    /** Permet d'accéder à la date à laquelle la dernière étape est effectuée, c'est à dire la date à laquelle on a terminé de parcourir le chemin*/
    
    /**
     * Permet d'accéder à la date à laquelle la dernière étape est effectuée, 
     * c'est à dire la date à laquelle on a terminé de parcourir le chemin
     * @return  la date à laquelle la dernière étape est effectuée
     */
    public long getLastDate() {
    	if (this.getChemin().size() != 0) {
    		LinkedList<Etape> chemin = this.getChemin();
    		return chemin.getLast().getDate();
    	} else {
    		return -1;
    	}
    }

}