package controleur;

import constants.Couleur;
import ihm.IhmEchiquier;
import metier.Echiquier;
import metier.piece.Piece;

/**
 * La classe Controleur est le lien entre le modèle (Echiquier) et la vue (IhmEchiquier).
 * Elle gère la logique du jeu ainsi que les interactions avec l'interface utilisateur.
 * Elle suit le modèle MVC (Modèle-Vue-Contrôleur).
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class Controleur 
{
    private Echiquier metier; 

    /**
     * Constructeur qui initialise le modèle (Echiquier) et la vue (IhmEchiquier).
     * Le contrôleur joue le rôle central pour coordonner les actions.
     */
    public Controleur()
    {
		// Métier
        this.metier = new Echiquier();

		// IHM
        new IhmEchiquier(this);
    }

    /**
     * Récupère la couleur du joueur actif.
     * @return La couleur du joueur actif (BLANC ou NOIR).
     */
    public Couleur getJoueurActif() 
    {
        return this.metier.getJoueurActif(); 
    }

    /**
     * Récupère la couleur du joueur actuel.
     * @return La couleur du joueur actuel.
     */
    public Couleur getJoueur() 
    { 
        return this.metier.getJoueur(); 
    }

    /**
     * Récupère le numéro actuel du tour dans le jeu.
     * @return Le numéro du tour.
     */
    public int getNumTour() 
    { 
        return this.metier.getNumTour(); 
    }

    /**
     * Récupère la pièce actuellement sélectionnée par le joueur.
     * @return La pièce sélectionnée.
     */
    public Piece getPieceSelectionnee() 
    { 
        return this.metier.getPieceSelectionnee(); 
    }

    /**
     * Récupère la pièce située à une position spécifique sur l'échiquier.
     * @param lig Ligne de l'échiquier.
     * @param col Colonne de l'échiquier.
     * @return La pièce à la position donnée, ou null si aucune pièce n'est présente.
     */
    public Piece getPiece(int lig, int col) 
    { 
        return this.metier.getPiece(lig, col); 
    }

    /**
     * Vérifie si la partie est terminée.
     * @return true si la partie est finie, sinon false.
     */
    public boolean estFinJeu() 
    { 
        return this.metier.estFinJeu(); 
    }

    /**
     * Met à jour l'état du jeu.
	 * Prinipalement utilisé pour gérer les animations et le fin de jeu.
     * @param deltaTime Temps écoulé depuis la dernière mise à jour.
     */
    public void update(double deltaTime) 
    { 
        this.metier.update(deltaTime); 
    }

    /**
     * Gère les événements de clic de souris sur l'échiquier.
     * @param x Coordonnée x du clic.
     * @param y Coordonnée y du clic.
     */
    public void handleMousePressed(int x, int y) 
    { 
        this.metier.handleMousePressed(x, y); 
    }

    /**
     * Annule une tentative de connexion en cours.
     */
    public void annulerConnexion() 
    { 
        this.metier.annulerConnexion(); 
    }

    /**
     * Valide la connexion en tant que serveur.
     * @param port Le port utilisé pour la connexion.
     */
    public void validerConnexionServeur(int port) 
    { 
         this.metier.validerConnexionServeur(port); 
    }

    /**
     * Valide la connexion en tant que client.
     * @param serveur L'adresse du serveur.
     * @param port Le port utilisé pour la connexion.
     */
    public void validerConnexionClient(String serveur, int port) 
    { 
        this.metier.validerConnexionClient(serveur, port); 
    }

	/**
	 * Vérifie si la connexion est en cours.
	 * 
	 * @return true si la connexion est en cours, false sinon.
	 */
	public boolean getConnexionEnCours()
	{
		return this.metier.getConnexionEnCours();
	}
	
	/**
	 * Vérifie si la connexion a réussi.
	 * 
	 * @return true si la connexion a réussi, false sinon.
	 */
	public boolean getConnexionReussie()
	{
		return this.metier.getConnexionReussie();
	}

    /* ======================== */
    /* Point d'entrée principal */
    /* ======================== */

    /**
     * Méthode principale pour démarrer l'application.
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) 
    { 
        new Controleur(); 
    }
}
