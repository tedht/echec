package ihm.menu;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import constants.AppConstants;
import ihm.IhmEchiquier;

/**
 * Classe représentant la fenêtre du menu de l'application.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class FrameMenu extends JFrame
{
    private PanelMenu panelMenu;

    /**
     * Constructeur de la fenêtre du menu.
     * 
     * @param ihm le gestionnaire des fenêtres
     */
    public FrameMenu(IhmEchiquier ihm)
    {
        this.setTitle("Echec");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Création et ajout du panneau de menu.
        this.panelMenu = new PanelMenu(ihm);
        this.add(this.panelMenu);

        // Définition des dimensions.
        this.setSize(new Dimension(AppConstants.LARGEUR_FRAME_MENU, AppConstants.HAUTEUR_FRAME_MENU));

		// Positionnement de la fenêtre au centre de l'écran.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

		this.setVisible(true);
    }
}
