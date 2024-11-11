package cours3;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans le RPG Textuel !");

        // Variable pour contrôler la boucle du jeu
        boolean playing = true;

        // Création du personnage du joueur
        System.out.print("Entrez le nom de votre personnage : ");
        String playerName = scanner.nextLine();
        String characterClass;

        // Boucle pour choisir une classe valide
        while (true) {
            System.out.print("Choisissez une classe (Sorcier, Elfe, Guerrier) : ");
            characterClass = scanner.nextLine();
            if (characterClass.equalsIgnoreCase("Sorcier") ||
                    characterClass.equalsIgnoreCase("Elfe") ||
                    characterClass.equalsIgnoreCase("Guerrier")) {
                break; // Sort de la boucle si la classe est valide
            } else {
                System.out.println("Classe invalide. Veuillez choisir entre Sorcier, Elfe ou Guerrier.");
            }
        }

        // Création du joueur avec les caractéristiques choisies
        Player player = new Player(playerName, characterClass);
        System.out.println("Personnage créé ! Voici vos caractéristiques :");
        player.displayStats();

        // Initialisation et affichage des armes disponibles dans le magasin
        WeaponStore store = new WeaponStore();
        System.out.println("\nBienvenue au magasin d'armes ! Voici les armes disponibles :");
        store.printWeapons();

        // Création et initialisation du donjon
        Dungeon dungeon = new Dungeon(5, 5); // Création d'une carte 5x5
        dungeon.initializeDungeon();

        // Boucle principale du jeu
        while (playing) {
            try {
                // Affichage du menu des actions disponibles
                System.out.println("\nQue souhaitez-vous faire ?");
                System.out.println("1. Acheter une arme");
                System.out.println("2. Consulter l'inventaire");
                System.out.println("3. Vérifier le statut XP");
                System.out.println("4. Se déplacer");
                System.out.println("5. Utiliser votre sort spécial");
                System.out.println("6. Quitter le jeu");
                System.out.print("Votre choix : ");

                // Lecture du choix de l'utilisateur
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer le saut de ligne

                switch (choice) {
                    case 1:
                        // Achat d'une arme
                        System.out.print("Entrez le nom de l'arme à acheter : ");
                        String weaponName = scanner.nextLine();
                        Weapon weapon = store.buyWeapon(weaponName, player);
                        if (weapon != null) {
                            player.addWeaponToInventory(weapon);
                            System.out.println(weapon.getName() + " a été ajouté à votre inventaire.");
                        } else {
                            System.out.println("Achat échoué. Vérifiez votre argent ou le nom de l'arme.");
                        }
                        break;
                    case 2:
                        // Affichage de l'inventaire du joueur
                        player.showInventory();
                        break;
                    case 3:
                        // Vérification du statut XP du joueur
                        player.checkXPStatus();
                        break;
                    case 4:
                        // Déplacement dans le donjon
                        System.out.print("Entrez la direction (haut, bas, gauche, droite) : ");
                        String direction = scanner.nextLine();
                        if (dungeon.movePlayer(direction, player)) {
                            playing = false; // Fin du jeu si le joueur trouve la sortie
                            System.out.println("Merci d'avoir joué au RPG Textuel !");
                        }
                        break;
                    case 5:
                        // Utilisation du sort spécial avec un monstre
                        Monster monster = new Monster();  // Crée un monstre
                        player.useSpecialPower(monster);  // Passe le monstre à la méthode
                        break;
                    case 6:
                        // Quitter le jeu
                        playing = false;
                        System.out.println("Merci d'avoir joué au RPG Textuel !");
                        break;
                    default:
                        // Gestion des choix invalides
                        System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 6.");
                }
            } catch (InputMismatchException e) {
                // Gestion des erreurs de saisie incorrecte
                System.out.println("Erreur : Veuillez entrer un nombre valide pour votre choix.");
                scanner.nextLine(); // Nettoyer l'entrée incorrecte
            }
        }

        scanner.close(); // Fermer le scanner à la fin du jeu
    }

}
