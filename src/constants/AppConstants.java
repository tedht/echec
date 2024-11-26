package constants;

/**
 * Classe utilitaire contenant toutes les constantes utilisées dans l'application d'échecs.
 * Cette classe est finale pour éviter qu'elle ne soit étendue, et son constructeur est privé
 * pour empêcher son instanciation.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public final class AppConstants 
{
	/* =================================== */
    /* Constantes liées au modèle (métier) */
	/* =================================== */

    // Vitesse de déplacement des pièces en pixels par seconde.
    public static final int VITESSE_PIECE = 25;

	/* ================================================ */
    /* Constantes liées à l'interface utilisateur (IHM) */
	/* ================================================ */

    // Nombre d'images par seconde pour le rendu de l'interface graphique.
    public static final int FPS = 24;

    // Marges pour l'affichage des composants dans les fenêtres.
    public static final int MARGE_X = 80;
    public static final int MARGE_Y = 80;

    // Dimensions de la fenêtre principale pour les joueurs.
    public static final int LARGEUR_FRAME_JOUEUR = 800;
    public static final int HAUTEUR_FRAME_JOUEUR = 800;

    // Dimensions de la fenêtre de menu principal.
    public static final int LARGEUR_FRAME_MENU = 320;
    public static final int HAUTEUR_FRAME_MENU = 320;

    // Dimensions de l'échiquier en pixels (taille pour l'affichage final et pour les sources des textures).
    public static final int LARGEUR_ECHIQUIER_DEST = 640;  // Dimensions à l'écran.
    public static final int HAUTEUR_ECHIQUIER_DEST = 640;
    public static final int LARGEUR_ECHIQUIER_SRC  = 700;  // Dimensions dans la source graphique.
    public static final int HAUTEUR_ECHIQUIER_SRC  = 700;

    // Dimensions des pièces d'échecs en pixels (taille pour l'affichage final et pour les sources des textures).
    public static final int LARGEUR_PIECE_DEST = 80;  // Dimensions à l'écran.
    public static final int HAUTEUR_PIECE_DEST = 80;
    public static final int LARGEUR_PIECE_SRC  = 80;  // Dimensions dans la source graphique.
    public static final int HAUTEUR_PIECE_SRC  = 80;

    // Dimensions des cercles
    public static final int LARGEUR_CERCLE = 40;
    public static final int HAUTEUR_CERCLE = 40;

    /**
     * Constructeur privé pour empêcher l'instanciation de cette classe utilitaire.
     */
    private AppConstants() {}
}
