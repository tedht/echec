# Jeu d'Échecs - Application Java

## Table des matières

- [Description](#description)
- [Fonctionnalités](#fonctionnalités)
- [Prérequis](#prérequis)
- [Lancer l'application](#lancer-lapplication)
	- [Sous Linux](#sous-linux)
	- [Sous Windows](#sous-windows)
- [Exemple d’utilisation](#exemple-dutilisation)
- [Auteurs](#auteurs)


## Description

Cette application est un jeu d'échecs développé en Java avec une interface graphique construite à l'aide de Swing. Elle permet à deux joueurs de s'affronter en temps réel et en ligne via  une fonctionnalité multijoueur en réseau.
Le projet respecte globalement les règles traditionnelles des échecs à part quelques différences pour des raisons
de simplification. 

## Fonctionnalités

- **Jeu d'échecs complet :**
	- **Gestion des joueurs :**
		- Chaque joueur est identifié comme Blanc ou Noir.
		- Gestion des tours en alternant le joueur actif
	- **Sélection et déplacement des pièces :** 
		- Cliquez sur une pièce pour la sélectionner et cliquez sur une case valide pour la déplacer.
		- Désélectionnez une pièce en cliquant à nouveau dessus.
	- **Validation des mouvements :**
		- Les mouvements sont validés selon les règles du jeu d'échecs.
		- Capture des pièces adverses.
		- Détection de fin de jeu
		- *Simplification* : Fin de jeu lorsque un des rois est capturé.
	- **Promotion des pions :**
		- Les pions atteignant la dernière rangée peuvent être promus.
		- *Simplfication* : les pions sont promus directement en Reine.
- **Interface graphique :** 
	- Fenêtres dédiées pour le menu principal, la connexion et les parties des joueurs.
	- Les fenêtres sont positionnées de façon intellgiente (centrées sur l'écran 
	ou sur d'autres fenêtres associées).
- **Architecture MVC :** Le jeu utilise l'architecture Modèle-Vue-Contrôleur (MVC).
- **Multijoueur en ligne :** 
	- Implémentation d'une communication client-serveur en utilisant des sockets pour permettre un jeu multijoueur en ligne.
	- *Simplification* : Le joueur blanc est celui qui a créé la partie et le joueur noir est celui
	qui la rejoind.
- **Boucle de jeu :** Le jeu utilise une boucle de jeu (update/draw) principalement pour les 
animations de déplacements de pièces.
- **Multi-threading :** Gestion fluide du fil d'exécution des parties pour garantir une expérience utilisateur réactive.
- **Adaptabilité :** Utilisation de constantes pour faciliter la modification des dimensions et autres paramètres.

## Prérequis
- Java 8 ou version supérieure.

## Lancer l'application
### Sous Linux
```bash
chmod u + x run.sh
./run.sh
```

### Sous Windows
```bash
run.bat
```
Vous pouvez également lancer l'application avec un double clic sur le fichier run.sh ou run.bat.

## Exemple d’utilisation

Lancez deux instances de l'application sur le même ordinateur.
Sur une des instance lancées:
1. Cliquez sur le bouton "Multijoueur"
2. Saisissez le port : 1234
3. Validez

Sur l'autre :
1. Cliquez sur le bouton "Multijoueur"
2. Sélectionnez l'option "Rejoindre une partie"
3. Saisissez le port : 1234
4. l'adresse du serveur : localhost
5. Validez

La fenêtre normalement afficher "Connexion réussie", et vous pourrez donc jouer à 
partir là lorsque la fenêtre de jeu s'affiche.


## Auteurs

- **Auteur :** Ted Herambert
