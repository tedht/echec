package metier.utils;

/**
 * Classe utilitaire contenant des méthodes statiques pour les calculs mathématiques
 * et géométriques utilisés dans l'application.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class Utils 
{
    /**
     * Calcule l'angle (en radians) entre deux points dans un plan cartésien.
     * 
     * @param x1 Coordonnée X du premier point.
     * @param y1 Coordonnée Y du premier point.
     * @param x2 Coordonnée X du second point.
     * @param y2 Coordonnée Y du second point.
     * @return L'angle en radians entre les deux points, mesuré dans le sens trigonométrique
     *         (positif dans le sens antihoraire, négatif dans le sens horaire).
     *         La valeur est comprise entre -π et π.
     */
    public static double calculerAngleRadian(int x1, int y1, int x2, int y2)
    {
        // Calcul de la différence entre les coordonnées.
        double dx = x2 - x1;
        double dy = y2 - y1;

        // Utilisation de Math.atan2 pour obtenir l'angle correspondant.
        return Math.atan2(dy, dx);
    }
}
