package metier.piece;

import metier.Echiquier;

/**
 * Représente une pièce de type Cavalier dans le jeu d'échecs.
 * Cette classe hérite de la classe {@link Piece} et implémente la logique
 * spécifique au mouvement du Cavalier sur l'échiquier.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class Cavalier extends Piece 
{
    /**
     * Constructeur de la classe Cavalier.
	 * 
     * @param echiquier L'échiquier sur lequel la pièce est placée.
     * @param num Le numéro représentant le Cavalier (BLANC : 3, NOIR : 9)
     * @param lig La ligne de la pièce sur l'échiquier.
     * @param col La colonne de la pièce sur l'échiquier.
     */
    public Cavalier(Echiquier echiquier, int num, int lig, int col) 
    {
        super(echiquier, num, lig, col);
    }

    /**
     * Vérifie si le déplacement du Cavalier est valide.
     * Le Cavalier se déplace en formant un "L", c'est-à-dire deux cases dans une direction
     * et une case dans la direction perpendiculaire.
	 * 
     * @param ligDest Ligne de destination
     * @param colDest Colonne de destination
     * @return true si le déplacement est valide, sinon false.
     */
    @Override
    public boolean deplacementValide(int ligDest, int colDest) 
    {
        // Vérifie d'abord que le déplacement est valide au niveau des règles de base des pièces
        return super.deplacementValide(ligDest, colDest)
            // Vérifie que le déplacement du Cavalier respecte la forme "L"
            && ((Math.abs(ligDest - this.lig) == 2 && Math.abs(colDest - this.col) == 1) 
            ||  (Math.abs(ligDest - this.lig) == 1 && Math.abs(colDest - this.col) == 2));
    }

	/**
	 * Retourne le type de la pièce, ici "Cavalier".
	 * Cette méthode implémente la méthode abstraite {@link Piece#getType()} 
	 * et retourne spécifiquement "Cavalier" pour cette classe.
	 * 
	 * @return Le type de la pièce, en l'occurrence "Cavalier".
	 */
	@Override
	public String getType() { return "Cavalier"; }
}
