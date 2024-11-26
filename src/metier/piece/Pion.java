package metier.piece;

import constants.Couleur;
import metier.Echiquier;

/**
 * Représente une pièce de type Pion dans le jeu d'échecs.
 * Cette classe hérite de la classe {@link Piece} et implémente la logique
 * spécifique au mouvement du Pion sur l'échiquier.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class Pion extends Piece
{
	private boolean caseDepart;
	
	/**
     * Constructeur de la classe Pion.
	 * 
     * @param echiquier L'échiquier sur lequel la pièce est placée.
     * @param num Le numéro représentant le Pion (BLANC : 1, NOIR : 7)
     * @param lig La ligne de la pièce sur l'échiquier.
     * @param col La colonne de la pièce sur l'échiquier.
     */
	public Pion (Echiquier echiquier, int num, int lig, int col) 
	{
		super(echiquier, num, lig, col); 
		this.caseDepart = true;
	}

	/**
	 * Vérifie si le déplacement du Pion est valide.
	 * Le Pion peut se déplacer verticalement d'une ou deux cases (au premier mouvement), 
	 * si aucune pièce ne bloque.
     * Le Pion peut capturer en diagonale une pièce adverse.
	 * 
	 * @param ligDest Ligne de destination
	 * @param colDest Colonne de destination
	 * @return {@code true} si le déplacement est valide, {@code false} sinon.
	 */
	public boolean deplacementValide(int ligDest, int colDest)
	{	
		// Vérifie que le déplacement est valide au niveau des règles de base des pièces
		if (!super.deplacementValide(ligDest, colDest))
			return false;

		int dir      = this.couleur == Couleur.BLANC ? -1 : 1;

		// Déplacement vertical de deux cases
		if(this.echiquier.getPiece(ligDest, colDest) == null && this.caseDepart) 
			return colDest == this.col && (ligDest == this.lig + 2 * dir || ligDest == this.lig + dir);

		// Déplacement vertical d'une case'
		if(this.echiquier.getPiece(ligDest, colDest) == null)
			return colDest == this.col && ligDest == this.lig + dir;

		// Capture diagonale (donc déplacement seulement s'il y a une pièce adverse sur la case de destination)
		return Math.abs(colDest - this.col) == 1 && ligDest == this.lig + dir;

	}


    /**
     * Déplace le Pion vers une nouvelle position (ligne, colonne).
	 * Lorsque le pion effectue un premier déplacement, il ne se retrouve
	 * à sa case de départ
	 * 
     * @param ligDest Ligne de destination
     * @param colDest Colonne de destination
     */
	public void deplacer(int ligDest, int colDest)
    {
        super.deplacer(ligDest, colDest);
		if(this.caseDepart) this.caseDepart = false;
		if(   this.couleur == Couleur.BLANC && this.lig == 0
		   || this.couleur == Couleur.NOIR  && this.lig == 7)
		   this.echiquier.promotion(this);
    }

	/**
	 * Retourne le type de la pièce, ici "Pion".
	 * Cette méthode implémente la méthode abstraite {@link Piece#getType()} 
	 * et retourne spécifiquement "Pion" pour cette classe.
	 * 
	 * @return Le type de la pièce, en l'occurrence "Pion". 
	 */
	@Override
	public String getType() { return "Pion"; }
}
