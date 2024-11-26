package metier.piece;

import constants.AppConstants;
import constants.Couleur;
import metier.Echiquier;

/**
 * La classe {@code Piece} représente une pièce d'échec sur un échiquier. 
 * Elle contient des informations relatives à la pièce, telles que sa position sur l'échiquier, 
 * son numéro (qui détermine son type), et sa couleur. 
 * Elle fournit des méthodes pour déplacer la pièce, vérifier la validité de ses déplacements, 
 * et obtenir des informations sur son type et sa couleur.
 * 
 * Cette classe sert de base pour toutes les pièces du jeu d'échecs, et ses méthodes permettent 
 * de déterminer les déplacements valides, de modifier la position de la pièce, 
 * et de vérifier si une pièce a la même couleur qu'une autre.
 * 
 * @author Ted Herambert
 * @version 1.0
 * @since 2024-11-23
 */
public abstract class Piece
{
    // Référence à l'échiquier sur lequel la pièce se trouve
    protected Echiquier echiquier;

    // Propriétés de la pièce
    protected int num;
	
	// Coordonnées lig,col dans l'echiquier
    protected int lig;     
    protected int col;

	// Coordonnée x,y dans l'IHM
    protected int x;
    protected int y;

	// Couleur (BLANC ou NOIR)
    protected Couleur couleur;

    /**
     * Constructeur de la classe {@code Piece}.
     * Initialise les attributs de la pièce en fonction du numéro de la pièce, de sa position 
     * sur l'échiquier et de sa couleur.
     * @param echiquier L'échiquier sur lequel la pièce est placée.
     * @param num Le numéro de la pièce, utilisé pour déterminer son type, sa couleur et également pour l'affichage.
     * @param lig La ligne de la pièce sur l'échiquier.
     * @param col La colonne de la pièce sur l'échiquier.
     */
    public Piece(Echiquier echiquier, int num, int lig, int col)
    {
		this.echiquier = echiquier;

		this.num     = num;
        this.lig     = lig;
        this.col     = col;
        this.x       = this.col * AppConstants.LARGEUR_PIECE_DEST;
        this.y       = this.lig * AppConstants.HAUTEUR_PIECE_DEST;
        this.couleur = (this.num > 6 ? Couleur.NOIR : Couleur.BLANC);
    }

    /**
     * Vérifie si le déplacement d'une pièce vers une nouvelle position (ligne, colonne) est valide.
     * @param lig Ligne de destination
     * @param col Colonne de destination
     * @return {@code true} si le déplacement est valide, sinon {@code false}.
     */
    public boolean deplacementValide(int lig, int col) 
    {
        return !(this.lig == lig && this.col == col) && !this.estMemeCouleur(this.echiquier.getPiece(lig, col));
    }

    /**
     * Vérifie si cette pièce a la même couleur qu'une autre pièce.
     * 
     * @param autrePiece La pièce à comparer.
     * @return {@code true} si les deux pièces ont la même couleur, sinon {@code false}.
     */
    public boolean estMemeCouleur(Piece autrePiece)
    {
        if(autrePiece == null) return false;
        return this.couleur == autrePiece.couleur;
    }

	/**
	 * Méthode abstraite qui permet d'obtenir le type de la pièce.
	 * Cette méthode doit être implémentée par les classes filles pour 
	 * retourner le type spécifique de la pièce (par exemple, "Pion", "Tour", etc.).
	 * @return Le type de la pièce sous forme de chaîne de caractères.
	 */
	public abstract String getType();

    // Méthodes getter
    public int     getNum    () { return this.num;     }
    public int     getLig    () { return this.lig;     }
    public int     getCol    () { return this.col;     }
    public int     getX      () { return this.x;       }
    public int     getY      () { return this.y;       }
    public Couleur getCouleur() { return this.couleur; }

    // Méthodes setter
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    /**
     * Déplace la pièce vers une nouvelle position (ligne, colonne).
     * @param ligDest Ligne de destination
     * @param colDest Colonne de destination
     */
    public void deplacer(int ligDest, int colDest)
    {
        this.lig = ligDest;
        this.col = colDest;
    }

    /**
     * Ajoute une valeur de déplacement à la position x de la pièce.
     * @param vitesse La valeur à ajouter à la position x.
     */
    public void addX(int vitesse) { this.x += vitesse; }

    /**
     * Ajoute une valeur de déplacement à la position y de la pièce.
     * @param vitesse La valeur à ajouter à la position y.
     */
    public void addY(int vitesse) { this.y += vitesse; }

	/**
     * Retourne une représentation en chaîne de caractères de la pièce.
     * 
     * @return le type, la couleur, et ses coordonnées (lig,col) sur l'echiquier sous forme de String.
     */
	public String toString()
	{
		return this.getType() + "[" + this.couleur.toString() + "](" + this.lig + "," + this.col + ")";
	}
}
