package metier;

import java.util.ArrayList;
import java.util.HashMap;

import constants.AppConstants;
import constants.Couleur;
import controleur.Controleur;
import metier.piece.Piece;
import metier.piece.Pion;
import metier.piece.Tour;
import metier.reseau.Client;
import metier.reseau.Serveur;
import metier.utils.Utils;
import metier.piece.Cavalier;
import metier.piece.Fou;
import metier.piece.Reine;
import metier.piece.Roi;

/**
 * Classe représentant l'échiquier.
 * Elle contient la logique de gestion des pièces, des tours de jeu,
 * ainsi que des déplacements et des événements liés à la fin du jeu.
 */
public class Echiquier
{
	private Controleur ctrl;
	private Serveur    serveur;
	private Client     client;
	private Thread     connexionThread;

	private Piece[][]  tabPieces;
	private Piece      pieceSelectionnee;
	private Piece      pieceEnDeplacement;
	private Pion       pionAPromouvoir;
	private Couleur    joueurActif;
	private Couleur    joueur;
	private int        numTour;

	private HashMap<Couleur, ArrayList<Piece>> tabPiecesCapturees;

	private int[][] initEchec = new int[][]{ { 8,  9, 10, 11, 12, 10,  9,  8},
											 { 7,  7,  7,  7,  7,  7,  7,  7},
											 { 0,  0,  0,  0,  0,  0,  0,  0},
											 { 0,  0,  0,  0,  0,  0,  0,  0},
											 { 0,  0,  0,  0,  0,  0,  0,  0},
											 { 0,  0,  0,  0,  0,  0,  0,  0},
											 { 1,  1,  1,  1,  1,  1,  1,  1},
											 { 2,  3,  4,  5,  6,  4,  3,  2}  };

	/**
     * Constructeur de la classe Echiquier.
     * 
     * @param ctrl Le contrôleur
     */
	public Echiquier(Controleur ctrl)
	{
		this.ctrl      = ctrl;
		this.tabPieces = new Piece[8][8];

		this.tabPiecesCapturees = new HashMap<Couleur, ArrayList<Piece>>();
		this.tabPiecesCapturees.put(Couleur.BLANC, new ArrayList < Piece > ());
		this.tabPiecesCapturees.put(Couleur.NOIR,  new ArrayList < Piece > ());

		this.init();
	}

	/**
     * Méthode pour initialiser le jeu
	 * 
     */
	public void init()
	{
		// Initialise l'échiquier avec la disposition de départ des pièces.
		for(int i = 0 ; i < initEchec.length ; i++ )
		{
			for (int j = 0 ; j < initEchec[0].length ; j++)
			{
				if(initEchec[i][j] != 0)
				{
					int typePiece = (initEchec[i][j] > 6 ? initEchec[i][j] - 6 : initEchec[i][j]);
					switch(typePiece)
					{
						case 1  : this.tabPieces[i][j] = new Pion     (this, initEchec[i][j], i, j); break;
						case 2  : this.tabPieces[i][j] = new Tour     (this, initEchec[i][j], i, j); break;
						case 3  : this.tabPieces[i][j] = new Cavalier (this, initEchec[i][j], i, j); break;
						case 4  : this.tabPieces[i][j] = new Fou      (this, initEchec[i][j], i, j); break;
						case 5  : this.tabPieces[i][j] = new Reine    (this, initEchec[i][j], i, j); break;
						case 6  : this.tabPieces[i][j] = new Roi      (this, initEchec[i][j], i, j); break;
						default : this.tabPieces[i][j] = null;                                            break;
					}
				}
				else tabPieces[i][j] = null;
			}

			// Réinitialise les paramètres du jeu
			this.pieceSelectionnee  = null;
			this.pieceEnDeplacement = null;
			this.pionAPromouvoir    = null;
			this.joueurActif        = Couleur.BLANC;
			this.joueur             = Couleur.BLANC;
			this.numTour            = 1;

			//  Vide les listes de pièces capturées
			this.tabPiecesCapturees.get(Couleur.BLANC).clear();
			this.tabPiecesCapturees.get(Couleur.NOIR) .clear();
		}
	}

	// Getters
	public Piece   getPiece(int lig, int col) { return this.tabPieces[lig][col]; }

	public Piece   getPieceSelectionnee () { return this.pieceSelectionnee;  }
	public Piece   getPieceEnDeplacement() { return this.pieceEnDeplacement; }
	public Couleur getJoueurActif       () { return this.joueurActif;        }
	public Couleur getJoueur            () { return this.joueur;             }
	public int     getNumTour           () { return this.numTour;            }

	// Setters
	public void setPieceSelectionnee (Piece   piece)   { this.pieceSelectionnee  = piece;   }
	public void setPieceEnDeplacement(Piece   piece)   { this.pieceEnDeplacement = piece;   }
	public void setJoueurActif       (Couleur couleur) { this.joueurActif        = couleur; }
	public void setJoueur            (Couleur couleur) { this.joueur             = couleur; }

	/**
     * Met à jour l'état de l'échiquier à chaque frame (animation des déplacements, fin de jeu, etc.).
     * 
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
     */
	public void update(double deltaTime)
	{
		if(this.pieceEnDeplacement != null)
		{
			double angleRad = Utils.calculerAngleRadian(this.pieceEnDeplacement.getX  (), 
														this.pieceEnDeplacement.getY  (), 
														this.pieceEnDeplacement.getCol() * AppConstants.LARGEUR_PIECE_DEST, 
														this.pieceEnDeplacement.getLig() * AppConstants.HAUTEUR_PIECE_DEST);											
			double cos = Math.cos(angleRad);
			double sin = Math.sin(angleRad);			

			this.pieceEnDeplacement.addX((int)(AppConstants.VITESSE_PIECE * deltaTime * cos));
			this.pieceEnDeplacement.addY((int)(AppConstants.VITESSE_PIECE * deltaTime * sin));

			if(   Math.abs(this.pieceEnDeplacement.getX() - this.pieceEnDeplacement.getCol() * AppConstants.LARGEUR_PIECE_DEST) <= 20
				&& Math.abs(this.pieceEnDeplacement.getY() - this.pieceEnDeplacement.getLig() * AppConstants.HAUTEUR_PIECE_DEST) <= 20)
			{
				this.pieceEnDeplacement.setX(this.pieceEnDeplacement.getCol() * AppConstants.LARGEUR_PIECE_DEST);
				this.pieceEnDeplacement.setY(this.pieceEnDeplacement.getLig() * AppConstants.HAUTEUR_PIECE_DEST);

				this.pieceEnDeplacement = null;
				this.tourSuivant();
			}
		}
		else if(this.pionAPromouvoir != null)
		{
			// Promotion d'un pion en reine
			int lig = this.pionAPromouvoir.getLig();
			int col = this.pionAPromouvoir.getCol();
			int num = this.pionAPromouvoir.getCouleur() == Couleur.BLANC ? 5 : 11;
			this.tabPieces[lig][col] = new Reine(this, num, lig, col);
			this.pionAPromouvoir = null;
		}
		else if(this.estFinJeu())
		{
			// Fin de la partie
			this.ctrl.afficherFinJeu();
		}
		
	}

	/**
	 * Gére les événements liés à la souris.
	 */
	public void handleMousePressed(int x, int y)
	{
		// Inversion des coordonnées si le joueur est Noir
		if(this.joueur == Couleur.NOIR)
		{
			x = AppConstants.LARGEUR_FRAME_JOUEUR - x;
			y = AppConstants.HAUTEUR_FRAME_JOUEUR - y;
		}

		// Vérificatier que le jeu n'est pas terminé, aucune pièce n'est en déplacement,
    	// le joueur actif est bien le joueur courant, et le clic est dans les limites de l'échiquier
		if(   !this.estFinJeu() && this.pieceEnDeplacement == null && this.joueur == this.joueurActif
		   && AppConstants.MARGE_X <= x && x <= AppConstants.MARGE_X + AppConstants.LARGEUR_ECHIQUIER_DEST
		   && AppConstants.MARGE_Y <= y && y <= AppConstants.MARGE_Y + AppConstants.HAUTEUR_ECHIQUIER_DEST)
	 	{
			int lig = (int)(y / AppConstants.LARGEUR_PIECE_DEST)-1;
			int col = (int)(x / AppConstants.HAUTEUR_PIECE_DEST)-1;

			// Cas 1 : Désélectionner pièce
			if(this.pieceSelectionnee == tabPieces[lig][col])
			{
				this.pieceSelectionnee = null;
			}
			// Cas 2 : Sélectionner nouvelle pièce
			else if (tabPieces[lig][col] != null && this.joueurActif == tabPieces[lig][col].getCouleur())
			{
				this.pieceSelectionnee = tabPieces[lig][col];
			}
			// Cas 3 : Déplacement (pièce autre couleur ou nulle)
			else if (this.pieceSelectionnee != null && this.pieceSelectionnee.deplacementValide(lig, col))
			{
				this.deplacer(this.pieceSelectionnee.getLig(), this.pieceSelectionnee.getCol(), lig, col);
				this.pieceSelectionnee  = null;
			}

	 	}
	}

	/**
	 * Démarrer le mode serveur et attendre une connexion
	 */
	public void validerConnexionServeur(int port) 
	{
		this.ctrl.attendreClient();
		this.serveur         = new Serveur(this, port);
		this.connexionThread = new Thread (this.serveur);
		this.connexionThread.start();
	}

	/**
	 * Connexion au serveur en mode client
	 */
	public void validerConnexionClient (String serveur, int port) 
	{
		this.ctrl.attendreServeur();
		this.client          = new Client(this, serveur, port);
		this.connexionThread = new Thread(this.client);
		this.connexionThread.start();
	}

	/**
	 * Annuler la connexion en cours
	 */
	public void annulerConnexion() 
	{
		if(this.serveur != null)
		{
			this.serveur.close();
		}

		if(this.client != null)
		{
			this.client.close();
		}
	}

	/**
	 * Vérifie si le jeu est terminé 	
	 * @return {@code true} si le roi capturé, {@code false} sinon.
	 */
	public boolean estFinJeu() 
	{ 
		for(Piece piece : this.tabPiecesCapturees.get(this.joueurActif))
		{
			if(piece.getNum() / 6 - 1 == this.joueurActif.getValeur()) 
				return true;
		}
		return false;
	}

	/**
	 * Passe au tour suivant en alternant le joueur actif
	 */
	public void tourSuivant() 
	{
		this.joueurActif = Couleur.fromInt(1-this.joueurActif.getValeur());
		this.numTour++;
	}

	/**
	 * Gérer le déplacement d'une pièce sur l'échiquier
	 * @param ligOrig Ligne d'origine
	 * @param colOrig Colonne d'origine
	 * @param ligDest Ligne de destination
	 * @param colDest Colonne de destination
	 */
	public void deplacer(int ligOrig, int colOrig, int ligDest, int colDest) 
	{
		Piece pieceCapturee     = this.tabPieces[ligDest][colDest];
		this.pieceEnDeplacement = this.tabPieces[ligOrig][colOrig];

		// Met à jour les variables lig et col de la pièce
		this.pieceEnDeplacement.deplacer(ligDest, colDest);
		// Déplacement de la pièce sur l'echiquier
		this.tabPieces[ligDest][colDest] = this.pieceEnDeplacement;
		// Case de départ devient nulle
		this.tabPieces[ligOrig][colOrig] = null;

		// Si pièce capturée
		if(pieceCapturee != null)
		{
			this.tabPiecesCapturees.get(pieceCapturee.getCouleur()).add(pieceCapturee);
		}

		if(this.joueur == this.joueurActif)
		{
			if(this.serveur != null)
			{
				this.serveur.envoyerDeplacement(ligOrig, colOrig, ligDest, colDest);
			}

			if(this.client != null)
			{
				this.client.envoyerDeplacement(ligOrig, colOrig, ligDest, colDest);
			}
		}
	}

	/**
	 * Démarrer le jeu
	 */
	public void lancerJeu() { this.ctrl.lancerJeu(); }

	/**
	 * Notifier le contrôleur que la connexion a été refusée
	 */
	public void connexionRefusee() { this.ctrl.connexionRefusee(); }

	/**
	 * Gérer la promotion d'un pion
	 * @param pion
	 */
	public void promotion(Pion pion) 
	{
		// Stocker le pion à promouvoir (Promotion effectuée dans la méthode 
		// update après avoir teminé l'animation de déplacement)
		this.pionAPromouvoir = pion;
	}

}
