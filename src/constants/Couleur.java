package constants;

/**
 * Enumération représentant les couleurs utilisées dans le jeu d'échecs.
 * Cette énumération inclut des méthodes utilitaires pour manipuler et convertir les couleurs.
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public enum Couleur 
{ 
    // Représente la couleur blanche.
    BLANC(0), 
    
    // Représente la couleur noire.
    NOIR(1); 

    // Valeur entière associée à la couleur (0 pour blanc, 1 pour noir).
    private final int valeur;

    /**
     * Constructeur privé de l'énumération.
     * 
     * @param valeur La valeur entière associée à la couleur.
     */
    Couleur(int valeur) 
    { 
        this.valeur = valeur; 
    }

    /**
     * Obtient la valeur entière associée à cette couleur.
     * 
     * @return La valeur entière de la couleur (0 pour blanc, 1 pour noir).
     */
    public int getValeur() 
    { 
        return this.valeur; 
    }
    
    /**
     * Convertit une valeur entière en une couleur.
     * 
     * @param valeur La valeur entière (0 pour blanc, 1 pour noir).
     * @return La couleur correspondante (BLANC ou NOIR).
     * @throws IllegalArgumentException Si la valeur fournie ne correspond à aucune couleur.
     */
    public static Couleur fromInt(int valeur) 
    {
        // Parcours de toutes les valeurs de l'énumération pour trouver une correspondance.
        for (Couleur couleur : Couleur.values()) 
        {
            if (couleur.getValeur() == valeur) 
            { 
                return couleur; 
            }
        }

        // Lancer une exception si la valeur n'est pas valide.
        throw new IllegalArgumentException("Valeur couleur non valide : " + valeur);
    }

    /**
     * Renvoie la couleur opposée.
     * 
     * @param couleur La couleur actuelle.
     * @return La couleur opposée (BLANC -> NOIR ou NOIR -> BLANC).
     */
    public static Couleur autreCouleur(Couleur couleur)
    {
        return couleur == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC;
    }

	/**
     * Retourne une représentation en chaîne de caractères de la couleur.
     * 
     * @return "Blanc" si la couleur est BLANC, "Noir" si la couleur est NOIR.
     */
    @Override
    public String toString() 
    {
        return this == Couleur.BLANC ? "Blanc" : "Noir";
    }
}
