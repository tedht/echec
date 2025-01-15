package metier.reseau;

import constants.Couleur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import metier.Echiquier;

/**
 * La classe {@code Client} gère la connexion à un serveur (un joueur qui à créer 
 * une partie) à partie d'un port et d'une adresse IP.
 * Pour des raisons de simplification, le joueur qui rejoind une partie (le client)
 * correspond au joueur "noir".
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class Client implements Runnable
{
    // Référence à l'échiquier du jeu
    private Echiquier echiquier;

    // Informations pour la connexion au serveur
    private String serveur;
    private int    port;
    private Socket toServer;
    private String action; // déplacement effectué au format : ligOrig,colOrig,ligDest,ColDest

    // Flux d'entrée et de sortie pour communiquer avec le serveur
    private BufferedReader in;
    private PrintWriter    out;

    /**
     * Constructeur du client
     * @param echiquier Référence à l'échiquier du jeu
     * @param serveur Adresse du serveur
     * @param port Port du serveur
     */
    public Client(Echiquier echiquier, String serveur, int port)
    {
        this.echiquier = echiquier;
        this.serveur   = serveur;
        this.port      = port;
        this.action    = null;
    }

    /**
     * Méthode exécutée dans un thread pour gérer la communication avec le serveur.
     * Elle gère la connexion, la réception des actions du serveur et met à jour l'échiquier.
     */
    @Override
    public void run() 
    {
        try 
        {
            // Connexion au serveur
            this.toServer = new Socket(this.serveur, this.port);
			
			this.echiquier.setConnexionEnCours(false);
			this.echiquier.setConnexionReussie(true);
            System.out.println("Connexion réussie !");

            // Initialisation du joueur avec la couleur NOIR et lancement du jeu
            this.echiquier.setJoueur(Couleur.NOIR);

            // Initialisation des flux d'entrée et de sortie pour la communication
            this.in  = new BufferedReader(new InputStreamReader(this.toServer.getInputStream()));
            this.out = new PrintWriter(this.toServer.getOutputStream(), true);

            Scanner sc;
            int ligOrig, colOrig, ligDest, colDest;
            // Boucle pour recevoir les actions du serveur en continu
            while((this.action = in.readLine()) != null)
            {
                // Récupération des données
                sc = new Scanner(this.action);
                sc.useDelimiter(",");
                ligOrig = sc.nextInt();
                colOrig = sc.nextInt();
                ligDest = sc.nextInt();
                colDest = sc.nextInt();
                sc.close();

                // Déplacement de la pièce sur l'échiquier
                this.echiquier.deplacer(ligOrig, colOrig, ligDest, colDest);
            }

        } 
        catch (IOException e)          
        { 
            e.printStackTrace(); 
        } 
		this.echiquier.setConnexionEnCours(false);
    }

    /**
     * Méthode pour fermer la connexion avec le serveur.
     */
    public void close() 
    { 
        try {
            this.toServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    /**
     * Méthode pour envoyer un déplacement de pièce au serveur.
     * @param ligOrig Ligne d'origine
     * @param colOrig Colonne d'origine
     * @param ligDest Ligne de destination
     * @param colDest Colonne de destination
     */
    public void envoyerDeplacement(int ligOrig, int colOrig, int ligDest, int colDest) 
    {
        // Envoi du déplacement
        this.out.println("" + ligOrig + "," + colOrig + "," + ligDest + "," + colDest);
    }
}
