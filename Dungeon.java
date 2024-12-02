package cours3;

import java.util.Random;

class Dungeon {
    private char[][] map;               // Carte du donjon
    private Monster[][] monsters;       // Monstres présents sur la carte
    private Obstacle[][] obstacles;     // Obstacles présents sur la carte
    private Random random;
    private boolean isGameComplete = false; // Indique si le jeu est terminé

    // Constantes pour représenter les éléments du donjon
    public static final char PLAYER = 'P';
    public static final char MONSTER = 'M';
    public static final char OBSTACLE = 'O';
    public static final char EXIT = 'E';
    public static final char EMPTY = '.';

    /**
     * Constructeur du donjon.
     *
     * @param rows Nombre de lignes.
     * @param cols Nombre de colonnes.
     */
    public Dungeon(int rows, int cols) {
        map = new char[rows][cols];
        monsters = new Monster[rows][cols];
        obstacles = new Obstacle[rows][cols];
        random = new Random();
    }

    public char[][] getMap() {
        return map;
    }

    public Obstacle[][] getObstacles() {
        return obstacles;
    }

    /**
     * Initialise le donjon avec des monstres, obstacles et une sortie.
     */
    public void initializeDungeon() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int rand = random.nextInt(5);
                if (rand == 0) {
                    map[i][j] = MONSTER;
                    monsters[i][j] = new Monster();
                } else if (rand == 1) {
                    map[i][j] = OBSTACLE;
                    obstacles[i][j] = new Obstacle();
                } else {
                    map[i][j] = EMPTY;
                }
            }
        }
        map[0][0] = PLAYER; // Position initiale du joueur
        map[map.length - 1][map[0].length - 1] = EXIT; // Position de la sortie
    }

    /**
     * Vérifie si le joueur est à la sortie.
     *
     * @param player Instance du joueur.
     * @return `true` si le joueur est à la sortie, sinon `false`.
     */
    public boolean isAtExit(Player player) {
        int x = player.getX();
        int y = player.getY();
        return map[x][y] == EXIT;
    }

    /**
     * Gère le déplacement du joueur dans le donjon.
     *
     * @param direction Direction du déplacement (haut, bas, gauche, droite).
     * @param player    Instance du joueur.
     * @return Message décrivant l'état après le déplacement.
     */
    public String movePlayer(String direction, Player player) {
        int x = player.getX();
        int y = player.getY();
        map[x][y] = Dungeon.EMPTY; // Efface l'ancienne position du joueur

        // Détermine la nouvelle position
        switch (direction.toLowerCase()) {
            case "haut" -> x = Math.max(0, x - 1);
            case "bas" -> x = Math.min(map.length - 1, x + 1);
            case "gauche" -> y = Math.max(0, y - 1);
            case "droite" -> y = Math.min(map[0].length - 1, y + 1);
            default -> {
                return "Direction invalide.";
            }
        }

        StringBuilder message = new StringBuilder();

        // Gestion des interactions
        switch (map[x][y]) {
            case Dungeon.MONSTER -> handleMonsterInteraction(x, y, player, message);
            case Dungeon.OBSTACLE -> {
                message.append("Un obstacle bloque votre chemin.\n");
                x = player.getX();
                y = player.getY();
            }
            case Dungeon.EXIT -> {
                message.append("Félicitations ! Vous avez trouvé la sortie !");
                isGameComplete = true;
                player.setPosition(x, y);
                return message.toString();
            }
        }

        // Si le joueur est mort
        if (!player.isAlive()) {
            return "Game Over ! Votre santé est à 0.";
        }

        // Mettre à jour la position et retourner le message
        player.setPosition(x, y);
        map[x][y] = Dungeon.PLAYER;

        return message.toString();
    }


    private void handleMonsterInteraction(int x, int y, Player player, StringBuilder message) {
        Monster monster = monsters[x][y];
        if (monster != null && monster.isAlive()) {
            // Dégâts infligés par le monstre
            double damageFromMonster = monster.attackPlayer();
            player.takeDamage(damageFromMonster);
            message.append("Le monstre vous attaque et inflige ")
                    .append(String.format("%.2f", damageFromMonster))
                    .append(" dégâts.\n");

            // Dégâts infligés au monstre
            double damageToMonster = player.getAttackPower();
            if (player.getEquippedWeapon() != null) {
                damageToMonster += player.getEquippedWeapon().calculateDamageForMonster();
            }
            monster.hit(damageToMonster);
            message.append("Vous attaquez le monstre et infligez ")
                    .append(String.format("%.2f", damageToMonster))
                    .append(" dégâts.\n");

            // Si le monstre est vaincu
            if (!monster.isAlive()) {
                monsters[x][y] = null; // Retire le monstre de la carte
                double xpEarned = monster.grantXP();
                double goldEarned = monster.grantGold();
                player.gainXP(xpEarned);
                player.addGold(goldEarned);

                message.append("Vous avez vaincu le monstre !\n")
                        .append("Récompenses : ").append(xpEarned).append(" XP et ")
                        .append(goldEarned).append(" pièces d'or.\n");
            } else {
                message.append("Le monstre est toujours en vie. Préparez-vous pour une autre attaque.\n");
            }
        }
    }
}
