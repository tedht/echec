package ihm.connexion;

import controleur.Controleur;
import ihm.IhmEchiquier;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Classe JPanel qui contient les composents de la fenêtre de connexion de l'application.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class PanelConnexion extends JPanel implements ActionListener, ItemListener
{
	// Énumération pour définir le type de connexion : Serveur ou Client
	private enum TypeConn
	{
        SERVEUR(0), 
		CLIENT(1);
		
		private final int ind;

		TypeConn(int ind) { this.ind = ind; }
	
		public int getInd() { return this.ind; }
    }
	
	private Controleur   ctrl;
	private IhmEchiquier ihm;

	private boolean connexionEnCours;

	private JPanel panelInfo, panelAction;
	private JPanel panelInfoType, panelInfoPort, panelInfoServeur, panelActionBtn;

	private JRadioButton[] tabRbType;
	private ButtonGroup    btgAction;
	private JButton        btnAnnuler, btnValider;

	private JLabel      lblType, lblPort, lblServeur, lblChargement;
	private JTextField  txtPort, txtServeur;


	/**
     * Constructeur de PanelConnexion.
     * 
     * @param ctrl le contrôleur
	 * @param ihm  le gestionnaire de l'ihm
     */
	public PanelConnexion(Controleur ctrl, IhmEchiquier ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.connexionEnCours = false;

		this.setLayout(new BorderLayout (10,10));

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.panelInfo        = new JPanel(new GridLayout(0, 2, 5, 5)); this.panelInfo       .setOpaque(false);
		this.panelInfoType    = new JPanel(new FlowLayout(FlowLayout.LEFT));                this.panelInfoType   .setOpaque(false);
		this.panelInfoPort    = new JPanel(new FlowLayout(FlowLayout.LEFT));                this.panelInfoPort   .setOpaque(false);
		this.panelInfoServeur = new JPanel(new FlowLayout(FlowLayout.LEFT));                this.panelInfoServeur.setOpaque(false);
		
		this.panelAction      = new JPanel(new GridLayout(0, 1, 5, 5)); this.panelAction.setOpaque(false); 
		this.panelActionBtn   = new JPanel(); 
		this.panelActionBtn.setBackground(new Color(200, 200, 250));

		/* Eléments de panelInfo */
		this.btgAction = new ButtonGroup();

		this.lblType   = new JLabel ("Que voudriez-vous faire : ", SwingConstants.RIGHT);
		this.tabRbType = new JRadioButton[2];
		this.tabRbType[TypeConn.SERVEUR.getInd()] = new JRadioButton("Créer une partie");
		this.tabRbType[TypeConn.CLIENT .getInd()] = new JRadioButton("Rejoindre une partie");
		this.tabRbType[TypeConn.SERVEUR.getInd()].setSelected(true);

		this.btgAction.add(this.tabRbType[TypeConn.SERVEUR.getInd()]);
		this.btgAction.add(this.tabRbType[TypeConn.CLIENT .getInd()]);

		this.lblPort    = new JLabel ("Port : ", SwingConstants.RIGHT); 
		this.txtPort    = new JTextField(22);

		this.lblServeur = new JLabel ("Adresse du serveur : ", SwingConstants.RIGHT);
		this.txtServeur = new JTextField(22);
		this.lblServeur      .setVisible(false);
		this.panelInfoServeur.setVisible(false);

		/* Elements de panelAction */
		this.lblChargement = new JLabel (" ", SwingConstants.CENTER);

		this.btnAnnuler = new JButton("Annuler");
		this.btnValider = new JButton("Valider");

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.add(this.panelInfo,    BorderLayout.CENTER);
		this.add(this.panelAction,  BorderLayout.SOUTH );

		/* Elements de panelInfo */
		this.panelInfo       .add(this.lblType);
		this.panelInfo       .add(this.panelInfoType);
		this.panelInfoType   .add(this.tabRbType[0]);
		this.panelInfoType   .add(this.tabRbType[1]);

		this.panelInfo       .add(this.lblPort);
		this.panelInfo       .add(this.panelInfoPort);
		this.panelInfoPort   .add(this.txtPort);

		this.panelInfo       .add(this.lblServeur);
		this.panelInfo       .add(this.panelInfoServeur);
		this.panelInfoServeur.add(this.txtServeur);
		
		/* Elements de panelAction */
		this.panelAction   .add(this.lblChargement);
		this.panelAction   .add(this.panelActionBtn);
		this.panelActionBtn.add(this.btnAnnuler);
		this.panelActionBtn.add(this.btnValider);
		
		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		for (JRadioButton rb : this.tabRbType)
			rb.addItemListener (this);

		this.btnAnnuler.addActionListener(this);
		this.btnValider.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAnnuler)
		{
			if(!connexionEnCours)
			{
				SwingUtilities.getWindowAncestor(this).dispose();
			}
			else
			{
				this.setConnexionEnCours(false, " ");
				this.ctrl.annulerConnexion();
			}
		}

		if(e.getSource() == this.btnValider)
		{
			if(this.tabRbType[TypeConn.SERVEUR.getInd()].isSelected())
			{
				this.attendreClient();

				new Thread(() -> {
					this.ctrl.validerConnexionServeur(Integer.parseInt(this.txtPort.getText()));
					while (this.ctrl.getConnexionEnCours()) 
					{
						try 
						{
							Thread.sleep(100);
						}
						catch (InterruptedException ex) {}
					}
					
					SwingUtilities.invokeLater(() -> {
						if (this.ctrl.getConnexionReussie()) 
						{
							connexionReussie();
							this.ihm.lancerJeu();
						} 
						else 
						{
							connexionRefusee();
						}
					});
				}).start();
			}

			if(this.tabRbType[TypeConn.CLIENT .getInd()].isSelected())
			{
				this.attendreServeur();
				
				new Thread(() -> {
					this.ctrl.validerConnexionClient(this.txtServeur.getText(), Integer.parseInt(this.txtPort.getText()));
					while (this.ctrl.getConnexionEnCours()) 
					{
						try 
						{
							Thread.sleep(100); 
						} 
						catch (InterruptedException ex) {}
					}
					
					SwingUtilities.invokeLater(() -> {
						if (this.ctrl.getConnexionReussie()) 
						{
							this.connexionReussie();
							this.ihm.lancerJeu();
						} 
						else 
						{
							this.connexionRefusee();
						}
					});
				}).start();
			}
		}

		this.revalidate();
		this.repaint();
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{	
		if(   e.getSource() == this.tabRbType[TypeConn.SERVEUR.getInd()]
		   && this.tabRbType[TypeConn.SERVEUR.getInd()].isSelected())
		{
			this.lblServeur      .setVisible(false);
			this.panelInfoServeur.setVisible(false);
		}

		if(   e.getSource() == this.tabRbType[TypeConn.CLIENT.getInd()]
		   && this.tabRbType[TypeConn.CLIENT.getInd()].isSelected())
		{
			this.lblServeur      .setVisible(true);
			this.panelInfoServeur.setVisible(true);
		}

		this.revalidate();
		this.repaint();
	}

	 /**
     * Affiche "Attente d'un joueur...".
     * Utilisé lorsque qu'on attend qu'un autre joueur se connecte sur notre serveur.
     */
	public void attendreClient() 
	{ 
		this.setConnexionEnCours(true, "Attente d'un joueur...");
	}

	/**
     * Affiche "Connexion au serveur...".
	 * Utilisé lorsque qu'on essaye de se connecter à un autre serveur.
     */
	public void attendreServeur() 
	{ 
		this.setConnexionEnCours(true, "Connexion au serveur...");
	} 

	 /**
     * Change l'état de connexion.
     * 
     * @param b                état de la connexion
     * @param phraseChargement message affiché pendant le chargement
     */
	private void setConnexionEnCours(boolean b, String phraseChargement) {
		this.connexionEnCours = b;
		this.lblChargement.setText(phraseChargement);

		if(b)
			this.panelActionBtn.remove(this.btnValider);
		else 
			this.panelActionBtn.add(this.btnValider);

		this.lblType.setEnabled(!b);
		for(JRadioButton rb : this.tabRbType)
			rb.setEnabled(!b);

		this.lblPort.setEnabled (!b);
		this.txtPort.setEditable(!b);

		this.lblServeur.setEnabled (!b);
		this.txtServeur.setEditable(!b);
		this.revalidate();
		this.repaint();
	}

	/**
     * Affiche "Connexion réussie."
	 * Attend 2 secondes avant de poursuivre
     */
	public void connexionReussie() 
	{ 
		this.lblChargement.setText("Connexion réussie.");
		this.btnAnnuler.setEnabled(false);

		try 
		{ 
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) { e.printStackTrace(); }
		
	}

	/**
     * Affiche "Connexion refusée."
	 * Attend 2 secondes avant de poursuivre
     */
	public void connexionRefusee() 
	{ 
		this.lblChargement.setText("Connexion refusée.");
		this.btnAnnuler.setEnabled(false);
		
		try 
		{ 
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) { e.printStackTrace(); }

		this.setConnexionEnCours(false, " ");
		this.btnAnnuler.setEnabled(true);
	}

}
