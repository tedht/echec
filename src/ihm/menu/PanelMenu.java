package ihm.menu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;

/**
 * Classe JPanel qui contient les composents de la fenêtre du menu de l'application.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class PanelMenu extends JPanel implements ActionListener
{
	private Controleur ctrl;

	private JButton btnMultijoueur, btnQuitter;

	/**
	 * Constructeur de la classe PanelMenu.
	 * Initialise le panneau avec deux boutons : "Multijoueur" et "Quitter".
	 *
	 * @param ctrl le contrôleur
	 */
	public PanelMenu(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setLayout(new GridLayout(2, 1, 20,20));
		this.setBorder(new EmptyBorder(30,30,100,30));

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.btnMultijoueur = new JButton("Multijoueur");
		this.btnQuitter     = new JButton("Quitter");

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.add(this.btnMultijoueur);
		this.add(this.btnQuitter);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnMultijoueur  .addActionListener(this);
		this.btnQuitter.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource() == this.btnMultijoueur)
		{
			this.ctrl.jouer();
		}

		if(e.getSource() == this.btnQuitter)
		{
			this.ctrl.quitter();
		}
	}
}
