package carte;

import enums.*;

public class Carte {
    private int tailleCases;
    private Case[][] matrice;

    /**
     * Constructeur de l'objet Carte : crée un objet de type de carte
     * @param nbLignes      nombre de lignes de la carte
     * @param nbColonnes    nombre de colonnes de la carte
     * @param tailleCases   taille des cases de la carte
     */
    public Carte(int nbLignes, int nbColonnes, int tailleCases) {
        this.matrice = new Case[nbLignes][nbColonnes];
        this.tailleCases = tailleCases;
    }

    /**
     * Permet d'ajouter les case dans la matrice lors de la création de la carte dans LecteursDonnees
     * @param caseAAjouter  la case à ajouter
     */
    public void initCarte(Case caseAAjouter) {
        this.matrice[caseAAjouter.getLigne()][caseAAjouter.getColonne()] = caseAAjouter;
    }

    /**
     * Retourne le nombre de lignes
     * @return  Le nombre de lignes
     */
    public int getNbLignes() {
        return matrice.length;
    }
    
    /**
     * Retourne le nombre de colonnes
     * @return  Le nombre de colonnes
     */
    public int getNbColonnes() {
        return matrice[0].length;
    }

    /**
     * Retourne la taille des cases
     * @return  int : La taille des cases
     */
    public int getTailleCases() {
        return this.tailleCases;
    }

    /**
     * Retourne la case demandée
     * @param lig   la ligne où la case se trouve
     * @param col   la colonne où la case se trouve
     * @return  la case voulue spécifiée par les coordonnées
     */
    public Case getCase(int lig, int col) {
        return matrice[lig][col];
    }

    /**
     * Cherche si le voisin dans la direction donnée existe
     * @param src   la case dont on cherche un voisin
     * @param dir   la direction vers laquelle on va chercher le voisin
     * @return      true si le voisin existe, false sinon
     */
    public boolean voisinExiste(Case src, Direction dir) {
        if (dir == Direction.NORD) {
            if (src.getLigne() - 1 >= 0) {
                return true;
            } else {
                return false;
            }
        } else if (dir == Direction.SUD) {
            if (src.getLigne() + 1 < matrice.length) {
                return true;
            } else {
                return false;
            }
        } else if (dir == Direction.EST) {
            if (src.getColonne() + 1 < matrice[0].length) {
                return true;
            } else {
                return false;
            }
        } else {
            if (src.getColonne() - 1 >= 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Retourne le voisin d'une case dans une direction donnée. 
     * En général cette fonction est utilisé après voisinExiste
     * @param src   la case dont on va récupérer le voisin
     * @param dir   la direction vers laquelle on réucpère le voisin
     * @return      le voisin de la case src vers la direction dir
     */
    public Case getVoisin(Case src, Direction dir) /* throws CaseInaccessibleException */{
        //assert (voisinExiste(src, dir));
        // if(voisinExiste(src, dir) == false){
        //     throw new CaseInaccessibleException("Eh oh on ne sort pas de la case");
        // }
        if (dir == Direction.NORD) {
            return this.getCase(src.getLigne() - 1, src.getColonne());
        } else if (dir == Direction.SUD) {
            return this.getCase(src.getLigne() + 1, src.getColonne());
        } else if (dir == Direction.OUEST) {
            return this.getCase(src.getLigne(), src.getColonne() - 1);
        } else {
            return this.getCase(src.getLigne(), src.getColonne() + 1);
        }
    }

    /**
     * Cherche si une des cases voisines contient de l'eau 
     * @param pos   la case autour de laquelle on regarde si il y a de l'eau
     * @return      true si une des cases voisines contient de l'eau, false sinon
     */
    public boolean natureEauCaseVoisine(Case pos) {

        Case voisin;
        if (voisinExiste(pos, Direction.NORD)) {
            voisin = getVoisin(pos, Direction.NORD);
            if (voisin.getNature() == NatureTerrain.EAU) {
                return true;
            }
        }
        if (voisinExiste(pos, Direction.SUD)) {
            voisin = getVoisin(pos, Direction.SUD);
            if (voisin.getNature() == NatureTerrain.EAU) {
                return true;
            }
        }
        if (voisinExiste(pos, Direction.EST)) {
            voisin = getVoisin(pos, Direction.EST);
            if (voisin.getNature() == NatureTerrain.EAU) {
                return true;
            }
        }
        if (voisinExiste(pos, Direction.OUEST)) {
            voisin = getVoisin(pos, Direction.OUEST);
            if (voisin.getNature() == NatureTerrain.EAU) {
                return true;
            }
        }
        return false;
    }

}