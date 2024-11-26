package ihm.connexion;

import javax.swing.*;

import controleur.Controleur;

/**
 * Classe représentant la fenêtre de connexion de l'interface graphique.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class FrameConnexion extends JFrame
{
    private PanelConnexion panelConnexion;

    /**
     * Constructeur de la fenêtre de connexion.
     * 
     * @param ctrl       Le contrôleur principal de l'application.
     * @param frameMenu  La fenêtre du menu utilisée pour positionner la 
	 *                   fenêtre de connexion au centre par rapport à celle-ci.
     */
    public FrameConnexion(Controleur ctrl, JFrame frameMenu)
    {
		this.setTitle("Connexion");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        // Création et ajout du panneau de connexion.
        this.panelConnexion = new PanelConnexion(ctrl);
        this.add(this.panelConnexion);

        this.pack();

        // Positionne la fenêtre au centre de la fenêtre du menu.
        this.setLocation(
            frameMenu.getX() + frameMenu.getWidth() / 2 - this.getWidth() / 2,
            frameMenu.getY() + frameMenu.getHeight() / 2 - this.getHeight() / 2
        );

        this.setVisible(true);
    }

    /**
     * Affiche "Attente d'un joueur...".
     * Utilisé lorsque qu'on attend qu'un autre joueur se connecte sur notre serveur.
     */
    public void attendreClient() 
    { 
        this.panelConnexion.attendreClient(); 
    }

    /**
     * Affiche "Connexion au serveur...".
	 * Utilisé lorsque qu'on essaye de se connecter à un autre serveur.
     */
    public void attendreServeur() 
    { 
        this.panelConnexion.attendreServeur(); 
    }

	/**
     * Affiche "Connexion réussie.".
     */
    public void connexionReussie() 
    { 
        this.panelConnexion.connexionReussie(); 
    }

    /**
     * Affiche "Connexion refusée.".
     */
    public void connexionRefusee() 
    { 
        this.panelConnexion.connexionRefusee(); 
    }
}
