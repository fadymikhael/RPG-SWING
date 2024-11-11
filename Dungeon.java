package cours3;
import java.util.Random;

class Dungeon {
    private char[][] map; // Carte du donjon représentée par une matrice de caractères
    private int playerRow; // Position actuelle du joueur (ligne)
    private int playerCol; // Position actuelle du joueur (colonne)
    private Random random; // Générateur de nombres aléatoires pour peupler la carte

    // Constructeur pour initialiser la carte du donjon
    public Dungeon(int rows, int cols) {
        map = new char[rows][cols];
        playerRow = 0; // Position initiale du joueur en haut à gauche
        playerCol = 0;
        random = new Random();
    }

    // Méthode pour initialiser la carte avec des monstres, des obstacles et des cases vides
    public void initializeDungeon() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int rand = random.nextInt(5); // Génère un nombre aléatoire entre 0 et 4
                if (rand == 0) {
                    map[i][j] = 'M'; // 'M' représente un monstre
                } else if (rand == 1) {
                    map[i][j] = 'O'; // 'O' représente un obstacle
                } else {
                    map[i][j] = '.'; // '.' représente une case vide
                }
            }
        }
        map[0][0] = 'P'; // Place le joueur à la position initiale
        map[map.length - 1][map[0].length - 1] = 'E'; // Place la sortie à la position finale
        printMap(); // Affiche la carte initialisée
    }

    // Méthode pour afficher la carte du donjon
    public void printMap() {
        System.out.println("\n=== Carte du Donjon ===");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // Affichage coloré pour différents types de cases
                if (map[i][j] == 'P') {
                    System.out.print("\u001B[32mP\u001B[0m "); // Vert pour le joueur
                } else if (map[i][j] == 'M') {
                    System.out.print("\u001B[31mM\u001B[0m "); // Rouge pour les monstres
                } else if (map[i][j] == 'O') {
                    System.out.print("\u001B[33mO\u001B[0m "); // Jaune pour les obstacles
                } else if (map[i][j] == 'E') {
                    System.out.print("\u001B[34mE\u001B[0m "); // Bleu pour la sortie
                } else {
                    System.out.print(". "); // Case vide
                }
            }
            System.out.println();
        }
        System.out.println("======================");
    }

    // Méthode pour déplacer le joueur dans la carte
    public boolean movePlayer(String direction, Player player) {
        map[playerRow][playerCol] = '.'; // Efface la position actuelle du joueur

        // Déplacement en fonction de la direction donnée
        switch (direction.toLowerCase()) {
            case "haut":
                if (playerRow > 0) playerRow--;
                break;
            case "bas":
                if (playerRow < map.length - 1) playerRow++;
                break;
            case "gauche":
                if (playerCol > 0) playerCol--;
                break;
            case "droite":
                if (playerCol < map[0].length - 1) playerCol++;
                break;
            default:
                System.out.println("Direction invalide.");
                return false; // Retourne false si la direction est invalide
        }

        // Gestion des événements selon le type de case où le joueur se déplace
        if (map[playerRow][playerCol] == 'M') {
            System.out.println("Vous avez rencontré un monstre !");
            player.takeDamage(random.nextDouble() * 20 + 10); // Inflige des dégâts entre 10 et 30
            player.gainXP(10); // Gagne de l'XP
            player.addGold(20); // Gagne de l'or
        } else if (map[playerRow][playerCol] == 'O') {
            System.out.println("Vous avez rencontré un obstacle !");
            player.takeDamage(random.nextDouble() * 5 + 5); // Inflige des dégâts entre 5 et 10
        } else if (map[playerRow][playerCol] == 'E') {
            System.out.println("Félicitations ! Vous avez trouvé la sortie du donjon !");
            player.gainXP(50); // Gagne beaucoup d'XP
            player.addGold(100); // Gagne beaucoup d'or
            return true; // Indique que la sortie a été trouvée
        }

        // Met à jour la position du joueur sur la carte
        map[playerRow][playerCol] = 'P';
        printMap(); // Affiche la carte mise à jour
        return false;
    }
}
