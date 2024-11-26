package metier.piece;

import metier.Echiquier;

/**
 * Représente une pièce de type Fou dans le jeu d'échecs.
 * Cette classe hérite de la classe {@link Piece} et implémente la logique
 * spécifique au mouvement du Fou sur l'échiquier.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class Fou extends Piece 
{
	/**
     * Constructeur de la classe Fou.
	 * 
     * @param echiquier L'échiquier sur lequel la pièce est placée.
     * @param num Le numéro représentant le Fou (BLANC : 3, NOIR : 9)
     * @param lig La ligne de la pièce sur l'échiquier.
     * @param col La colonne de la pièce sur l'échiquier.
     */
	public Fou(Echiquier echiquier, int num, int lig, int col) { super(echiquier, num, lig, col); }

	/**
	 * Vérifie si le déplacement du Fou est valide.
	 * Le Fou se déplace en diagonale.
	 * 
	 * @param ligDest Ligne de destination
	 * @param colDest Colonne de destination
	 * @return {@code true} si le déplacement est valide, {@code false} sinon.
	 */
	public boolean deplacementValide(int ligDest,int colDest) 
	{
		
		/**
		 * 1. Vérifie d'abord que le déplacement est valide au niveau des règles de base des pièces
		 * 2. Vérifie qu'il s'agit bien d'un déplacement en diagonale
		 * 3. Vérifie qu'il n'y a pas d'autres pièces sur le chemin
		 */
		return    super.deplacementValide(ligDest, colDest) 
		       && Math.abs(ligDest-this.lig) == Math.abs(colDest-this.col)
			   && !this.autrePiece(ligDest, colDest);

	}

	/**
	 * Vérifie s'il y a une autre pièce entre la position actuelle et la destination.
	 * Cette méthode parcourt les cases entre la position actuelle et la destination
	 * pour détecter une pièce se trouvant sur le chemin.
	 * 
	 * @param ligDest Ligne de destination
	 * @param colDest Colonne de destination
	 * @return {@code true} s'il y a une autre pièce sur le chemin, {@code false} sinon.
	 */
	private boolean autrePiece(int ligDest, int colDest)
	{
		// Calcul des directions pour parcourir le chemin (ligne et colonne)
		int dirlig = 1;
		int dircol = 1;
		if(this.lig > ligDest) dirlig = -1;
		if(this.col > colDest) dircol = -1;


    	// Parcours des cases entre la position actuelle et la destination
		Piece piece = null;
		for(int k = 1; k < Math.abs(ligDest-this.lig); k++)
		{
			piece = this.echiquier.getPiece(this.lig + k * dirlig,this.col + k * dircol);
			if (piece != null) return true; // Une pièce est présente sur le chemin
		}
		return false; // Aucune pièce sur le chemin
	}

	/**
	 * Retourne le type de la pièce, ici "Fou".
	 * Cette méthode implémente la méthode abstraite {@link Piece#getType()} 
	 * et retourne spécifiquement "Fou" pour cette classe.
	 * 
	 * @return Le type de la pièce, en l'occurrence "Fou".
	 */
	@Override
	public String getType() { return "Fou"; }
}
