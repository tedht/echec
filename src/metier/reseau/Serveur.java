package metier.reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import metier.Echiquier;

/**
 * La classe {@code Serveur} représente un serveur qui gère la connexion d'un 
 * client pour un jeu d'échecs en réseau.
 * Pour des raisons de simplification, le joueur créer uen partie (le Serveur) 
 * correspond au joueur "blanc".
 * 
 * @author Ted Herambert
 * @date 23/11/2024
 * @version 1.0
 */
public class Serveur implements Runnable
{
    // Référence à l'échiquier du jeu
    private Echiquier echiquier;
    
    // Informations pour la gestion du serveur
    private int          port;
    private ServerSocket serverSocket;
    private Socket       toClient;
    private String       action; // déplacement effectué au format : ligOrig,colOrig,ligDest,ColDest

    // Flux d'entrée et de sortie pour communiquer avec le client
    private BufferedReader in;
    private PrintWriter    out;

    /**
     * Constructeur du serveur.
     * @param echiquier Référence à l'échiquier du jeu
     * @param port Le port sur lequel le serveur écoute les connexions
     */
    public Serveur(Echiquier echiquier, int port)
    {
        this.echiquier = echiquier;
        this.port      = port;
        this.action    = null;
    }

    /**
     * Méthode exécutée dans un thread pour gérer les connexions et la communication avec le client.
     * Elle gère la réception des actions du client et met à jour l'échiquier.
     */
    @Override
    public void run() 
    {
        try 
        {
            // Création du socket serveur et attente de la connexion du client
            this.serverSocket = new ServerSocket(port);
            this.toClient = this.serverSocket.accept();

			this.echiquier.setConnexionEnCours(false);
			this.echiquier.setConnexionReussie(true);
            System.out.println("Client connecté : " + this.toClient.getInetAddress());

			// Par défaut, le joueur est initialisé avec la couleur BLANC

            // Initialisation des flux d'entrée et de sortie pour la communication
            this.in  = new BufferedReader(new InputStreamReader(this.toClient.getInputStream()));
            this.out = new PrintWriter(this.toClient.getOutputStream(), true);
            
			Scanner sc;
            int ligOrig, colOrig, ligDest, colDest;
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
        catch (UnknownHostException e) { 
            e.printStackTrace(); // Erreur d'adresse hôte inconnue
        } 
        catch (IOException e)          { 
            e.printStackTrace(); // Erreur d'entrée/sortie
        }
		this.echiquier.setConnexionEnCours(false);
    }

    /**
     * Méthode pour fermer la connexion avec le client et le serveur.
     */
    public void close() 
    { 
        try {
            this.serverSocket.close();
            this.toClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    /**
     * Méthode pour envoyer un mouvement de pièce au client.
     * @param ligOrig Ligne d'origine
     * @param colOrig Colonne d'origine
     * @param ligDest Ligne de destination
     * @param colDest Colonne de destination
     */
    public void envoyerDeplacement(int ligOrig, int colOrig, int ligDest, int colDest) 
    {
        // Envoi du mouvement
        this.out.println("" + ligOrig + "," + colOrig + "," + ligDest + "," + colDest);
    }
}
