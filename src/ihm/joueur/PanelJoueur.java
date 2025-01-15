package ihm.joueur;

import constants.AppConstants;
import constants.Couleur;
import controleur.Controleur;
import ihm.IhmEchiquier;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import metier.piece.Piece;

/**
 * Classe JPanel afficher et gérer l'interface utilisateur du jeu d'échecs.
 * 
 * Cette classe implémente Runnable pour permettre une mise à jour
 * continue de l'affichage via un thread de jeu.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class PanelJoueur extends JPanel implements Runnable
{
	private Controleur   ctrl;
	private IhmEchiquier ihm;

	private Thread gameThread;

	private Image imgFond;
	private Image imgEchiquier;
	private Image imgPieces;


	/**
     * Constructeur de PanelJoueur.
     * 
     * @param ctrl le contrôleur
	 * @param ihm  le gestionnaire de l'ihm
     */
	public PanelJoueur(Controleur ctrl, IhmEchiquier ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.imgFond = this.imgEchiquier = this.imgPieces = null;

		this.imgFond      = getToolkit().getImage("../res/fond.png");
		this.imgEchiquier = getToolkit().getImage("../res/echiquier.png");
		this.imgPieces    = getToolkit().getImage("../res/pieces.png"); 

		this.addMouseListener(new GereSouris());
	}

	/**
     * Démarre le thread du jeu pour gérer les mises à jour périodiques.
     */
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
     * Arrête le thread du jeu.
     */
	public void endGameThread()
	{
		gameThread = null;
	}

	/**
     * Méthode run.
     * Elle gère la boucle principale du jeu.
     */
	@Override
	public void run() 
	{
		final double drawInterval = 1_000_000_000/AppConstants.FPS;
		double deltaTime = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while(!this.ctrl.estFinJeu())
		{

			currentTime = System.nanoTime();

			deltaTime += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (deltaTime >= 1) 
			{
				this.ctrl.update(deltaTime); // mise à jour du metier
				repaint(); // Redessine le JPanel
				deltaTime--;
			}
		}

		this.ihm.afficherFinJeu();
	}

	/**
     * Méthode paintComponent.
	 * Dessine le fond, l'échiquier, les pièces et les indications.
     * 
     * @param g L'objet Graphics pour dessiner.
     */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Dessiner le fond
		g2.drawImage(this.imgFond, 0, 0, 
		             AppConstants.LARGEUR_FRAME_JOUEUR, AppConstants.HAUTEUR_FRAME_JOUEUR,
					 0,0, 
					 AppConstants.LARGEUR_ECHIQUIER_SRC, AppConstants.HAUTEUR_ECHIQUIER_SRC,
		    null);

		// Dessiner l'échiquier
		g2.drawImage(this.imgEchiquier, 
		             AppConstants.MARGE_X, 
					 AppConstants.MARGE_Y, 
					 AppConstants.MARGE_X + AppConstants.LARGEUR_ECHIQUIER_DEST, 
					 AppConstants.MARGE_Y + AppConstants.HAUTEUR_ECHIQUIER_DEST,
					 0,
					 0,
					 AppConstants.LARGEUR_ECHIQUIER_SRC,
					 AppConstants.HAUTEUR_ECHIQUIER_SRC,
					 null);

		for(int lig = 0; lig < 8; lig++)
		{
			for(int col = 0; col < 8; col++)
			{
				// Dessiner les pièces
				Piece pieceSelectionnee = this.ctrl.getPieceSelectionnee(); 
	
				if(pieceSelectionnee != null && pieceSelectionnee.deplacementValide(lig, col))
					this.drawCercle(g2, lig, col, Color.RED);
				if(this.ctrl.getPiece(lig, col) != null && this.ctrl.getPiece(lig, col) == pieceSelectionnee)
					this.drawCercle(g2, lig, col, Color.GREEN);
				
				if(this.ctrl.getPiece(lig, col) != null)
					this.drawPiece(g2, this.ctrl.getPiece(lig, col));
			}
			
			// Dessiner les numéros et lettres autour de l'échiquier
			g2.setFont(new Font("Arial", Font.BOLD, 20));
			g2.setColor(Color.WHITE);
			if(this.ctrl.getJoueur() == Couleur.BLANC)
			{
				// Colonnes
				g2.drawString(""+(char)('A'+lig), 
								AppConstants.MARGE_X + lig * AppConstants.LARGEUR_PIECE_DEST, 
								AppConstants.MARGE_Y - 5);
				// Lignes
				g2.drawString(""+(8-lig),
								AppConstants.MARGE_X - 15, 
								AppConstants.MARGE_Y + 17 + lig * AppConstants.HAUTEUR_PIECE_DEST);
			}
			else
			{
				// Colonnes
				g2.drawString(""+(char)('A'+(7-lig)), 
								AppConstants.MARGE_X + lig * AppConstants.LARGEUR_PIECE_DEST, 
								AppConstants.MARGE_Y - 5);
				// Lignes
				g2.drawString(""+(lig+1),
								AppConstants.MARGE_X - 15, 
								AppConstants.MARGE_Y + 17 + lig * AppConstants.HAUTEUR_PIECE_DEST);
			}

			// Afficher le statut du jeu
			String tour = "Tour " + this.ctrl.getNumTour() + " : ";
			if(this.ctrl.estFinJeu())
				tour += "fin du jeu";
			else 
				tour += "C'est au tour des " + this.ctrl.getJoueurActif().toString() + "s de jouer.";
			FontMetrics metrics = g2.getFontMetrics(g2.getFont());
			int largeurTexte = metrics.stringWidth(tour);


			g2.drawString(tour,
			              AppConstants.LARGEUR_FRAME_JOUEUR/2 - largeurTexte/2, 
						  AppConstants.MARGE_Y - 40);
			g2.setColor(Color.BLACK);
		}

		g2.dispose();
	}

	/**
     * Dessine une pièce à une position donnée sur l'échiquier.
     * 
     * @param g2    L'objet Graphics2D pour dessiner.
     * @param piece La pièce à dessiner.
     */
	private void drawPiece(Graphics2D g2, Piece piece)
	{
		int num, x, y;
		num = piece.getNum();

		if(this.ctrl.getJoueur() == Couleur.BLANC)
		{
			x = piece.getX();
			y = piece.getY();
		}
		else
		{
			x = AppConstants.LARGEUR_ECHIQUIER_DEST - piece.getX() - AppConstants.LARGEUR_PIECE_DEST;
			y = AppConstants.HAUTEUR_ECHIQUIER_DEST - piece.getY() - AppConstants.HAUTEUR_PIECE_DEST;
		}

		g2.drawImage(this.imgPieces, 
		             AppConstants.MARGE_X + x, 
		             AppConstants.MARGE_Y + y, 
		             AppConstants.MARGE_X + x + AppConstants.LARGEUR_PIECE_DEST, 
		             AppConstants.MARGE_Y + y + AppConstants.HAUTEUR_PIECE_DEST, 
		             (num-1)*AppConstants.LARGEUR_PIECE_SRC, 
		             0, 
		             (num-1)*AppConstants.LARGEUR_PIECE_SRC+AppConstants.LARGEUR_PIECE_SRC, 
		             AppConstants.HAUTEUR_PIECE_SRC, 
		             null);
	}

	/**
     * Dessine une cercle.
	 * Uilisée pour indiquer quelle est la pièce qu'on a sélectionnée (vert) ou 
	 * pour afficher ses mouvements possibles (rouge).
     * 
     * @param g2    L'objet Graphics2D pour dessiner.
     * @param lig   La ligne sur l'échiquier.
     * @param col   La colonne sur l'échiquier.
     * @param color La couleur du cercle.
     */
	private void drawCercle(Graphics2D g2, int lig, int col, Color color)
	{
		if(this.ctrl.getJoueur() == Couleur.NOIR)
		{
			lig = 7 - lig;
			col = 7 - col;
		}

        g2.setColor(color); 
        g2.fillOval(AppConstants.MARGE_X + col * AppConstants.LARGEUR_PIECE_DEST + AppConstants.LARGEUR_CERCLE/2,
		            AppConstants.MARGE_Y + lig * AppConstants.HAUTEUR_PIECE_DEST + AppConstants.HAUTEUR_CERCLE/2,
					AppConstants.LARGEUR_CERCLE, 
					AppConstants.HAUTEUR_CERCLE);
	}

	/**
	 * Classe interne permettant de gérer les événements liés à la souris.
	 * 
	 */
	private class GereSouris extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			PanelJoueur.this.ctrl.handleMousePressed(e.getX(), e.getY());
		}
	}
}
