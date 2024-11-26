package metier.piece;

import metier.Echiquier;

/**
 * Représente une pièce de type Roi dans le jeu d'échecs.
 * Cette classe hérite de la classe {@link Piece} et implémente la logique
 * spécifique au mouvement du Roi sur l'échiquier.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class Roi extends Piece
{
	/**
     * Constructeur de la classe Roi.
	 * 
     * @param echiquier L'échiquier sur lequel la pièce est placée.
     * @param num Le numéro représentant la Reine (BLANC : 6, NOIR : 12)
     * @param lig La ligne de la pièce sur l'échiquier.
     * @param col La colonne de la pièce sur l'échiquier.
     */
	public Roi(Echiquier echiquier, int num,int lig,int col) { super(echiquier, num, lig, col); }

	/**
	 * Vérifie si le déplacement de la Reine est valide.
	 * Le Roi peut se déplacer d'une case dans n'importe quelle direction.
	 * 
	 * @param ligDest Ligne de destination
	 * @param colDest Colonne de destination
	 * @return {@code true} si le déplacement est valide, {@code false} sinon.
	 */
    public boolean deplacementValide(int ligDest,int colDest)
    {	
		/**
		 * 1. Vérifie d'abord que le déplacement est valide au niveau des règles de base des pièces
		 * 2. Vérifie qu'il s'agit bien d'un déplacement d'une case dans n'importe quelle direction
		 */
		return    super.deplacementValide(ligDest, colDest) 
		       && Math.abs(ligDest - this.lig) <= 1 && Math.abs(colDest - this.col) <= 1;
    }

	/**
	 * Retourne le type de la pièce, ici "Roi".
	 * Cette méthode implémente la méthode abstraite {@link Piece#getType()} 
	 * et retourne spécifiquement "Roi" pour cette classe.
	 * 
	 * @return Le type de la pièce, en l'occurrence "Roi".
	 */
	@Override
	public String getType() { return "Roi"; }
}
