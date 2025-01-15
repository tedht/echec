package ihm;

import controleur.Controleur;
import ihm.connexion.FrameConnexion;
import ihm.joueur.FrameJoueur;
import ihm.menu.FrameMenu;
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 * Classe représentant l'interface graphique du jeu d'échecs (IHM).
 * Cette classe gère l'affichage des différentes fenêtres (menu, connexion, jeu) 
 * et les interactions avec l'utilisateur.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class IhmEchiquier 
{
    // Contrôleur principal qui gère la logique du jeu
	private Controleur  ctrl;

    // Fenêtres d'interface graphique
	private FrameMenu        frameMenu;
	private FrameConnexion   frameConnexion;
	private FrameJoueur      frameJoueur;

    /**
     * Constructeur de la classe IhmEchiquier.
     * Initialise l'interface graphique en créant le menu principal.
     * 
     * @param ctrl Le contrôleur qui gère la logique du jeu.
     */
	public IhmEchiquier(Controleur ctrl)
	{
		this.ctrl = ctrl;
		
		// Initialisation du menu principal
		this.frameMenu = new FrameMenu(this);
	}

    /**
     * Affiche la fenêtre de connexion pour créer/rejoindre une partie.
     * Si la fenêtre de connexion est déjà ouverte, elle est mise en avant.
     */
	public void jouer() 
	{ 
		if(this.frameConnexion == null)
		{
			// Crée une nouvelle fenêtre de connexion
			this.frameConnexion = new FrameConnexion(this.ctrl, this, this.frameMenu);

            // Ajoute un écouteur pour fermer la fenêtre de connexion correctement
			this.frameConnexion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    IhmEchiquier.this.frameConnexion = null;
                }
            });
		} 
		else 
		{
            // Si la fenêtre de connexion existe déjà, elle est mise en avant
            this.frameConnexion.toFront();
        }
	}
	
    /**
     * Quitte l'application
     */
	public void quitter() { System.exit(0); }

    /**
     * Affiche "attente du client" dans la fenêtre de connexion.
     * Utilisé lorsque qu'on attend qu'un autre joueur se connecte sur notre serveur.
     */
	public void attendreClient () { this.frameConnexion.attendreClient (); }

    /**
     * Affiche "Connexion au serveur..." dans la fenêtre de connexion.
	 * Utilisé lorsque qu'on essaye de se connecter à un autre serveur.
     */
	public void attendreServeur() { this.frameConnexion.attendreServeur(); }

    /**
     * Lance le jeu après une connexion réussie.
     * Ferme les fenêtres de connexion et de menu, puis ouvre la fenêtre de jeu.
     */
	public void lancerJeu() 
	{ 
        // Ferme la fenêtre de connexion et de menu
		this.frameConnexion.dispose();
		this.frameMenu.dispose();

        // Ouvre la fenêtre du jeu
		this.frameJoueur = new FrameJoueur(ctrl, this);
	}

    /**
     * Affiche un message à la fin du jeu, indiquant si le joueur a gagné ou perdu.
     * Termine également le thread de la partie en cours.
     */
	public void afficherFinJeu() 
	{ 
        // Arrête le thread du jeu en cours
		this.frameJoueur.endGameThread();

        // Affiche un message selon si le joueur a gagné ou perdu
		String message = (this.ctrl.getJoueur() != this.ctrl.getJoueurActif() ? "Vous avez gagné !" : "Vous avez perdu.");
		JOptionPane.showMessageDialog(this.frameJoueur, message, "Fin Jeu", JOptionPane.INFORMATION_MESSAGE);
	}
}
