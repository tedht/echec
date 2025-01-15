package ihm.joueur;

import constants.AppConstants;
import controleur.Controleur;
import ihm.IhmEchiquier;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Classe représentant la fenêtre dédiée à un joueur dans le jeu.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class FrameJoueur extends JFrame 
{
    private PanelJoueur panelJoueur;

    /**
     * Constructeur de la fenêtre joueur.
     * 
     * @param ctrl Le contrôleur principal de l'application.
	 * @param ihm  Le gestionnaire de l'ihm
     */
    public FrameJoueur(Controleur ctrl, IhmEchiquier ihm)
    {
        this.setTitle("Echec " + (ctrl.getJoueur().toString()));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Initialisation et ajout du panneau dédié au joueur.
        this.panelJoueur = new PanelJoueur(ctrl, ihm);
        this.add(this.panelJoueur);

        this.panelJoueur.startGameThread(); // Démmarrage du Thread de jeu

        // Définition des dimensions
        this.setSize(new Dimension(AppConstants.LARGEUR_FRAME_JOUEUR, AppConstants.HAUTEUR_FRAME_JOUEUR));

        // Positionnement de la fenêtre au centre de l'écran.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

        // Rend la fenêtre visible une fois tous les composants configurés.
        this.setVisible(true);
    }

    /**
     * Termine le Thread de jeu.
     */
    public void endGameThread()
    {
        this.panelJoueur.endGameThread();
    }
}
