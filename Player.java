package cours3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTextArea;

public class Player {
    private String name;
    private String characterClass;
    private double health;
    private double mana;
    private double money;
    private double xp;
    private int x; // Position x du joueur
    private int y; // Position y du joueur
    private final List<Weapon> inventory; // Inventaire des armes

    // Paramètres des classes
    private static final List<String> VALID_CLASSES = List.of("Sorcier", "Elfe", "Guerrier");
    private static final double MAX_HEALTH = 100.0;
    private static final double MAX_MANA = 50.0;

    private static final double SORCIER_ATTACK = 20.0;
    private static final double ELFE_ATTACK = 15.0;
    private static final double GUERRIER_ATTACK = 25.0;

    private static final double SORCIER_MANA_COST = 20.0;
    private static final double ELFE_MANA_COST = 15.0;
    private static final double GUERRIER_MANA_COST = 10.0;

    private static final double SORCIER_START_MONEY = 200.0;
    private static final double ELFE_START_MONEY = 150.0;
    private static final double GUERRIER_START_MONEY = 100.0;

    private static final double SORCIER_START_XP = 10.0;
    private static final double ELFE_START_XP = 5.0;
    private static final double GUERRIER_START_XP = 0.0;

    /**
     * Constructeur principal.
     *
     * @param name          Nom du joueur.
     * @param characterClass Classe du joueur.
     */
    public Player(String name, String characterClass) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Le nom du joueur ne peut pas être vide.");
        }
        if (characterClass == null || !VALID_CLASSES.contains(characterClass)) {
            throw new IllegalArgumentException("Classe invalide. Veuillez choisir entre Sorcier, Elfe ou Guerrier.");
        }
        this.name = name;
        this.characterClass = characterClass;
        this.inventory = new ArrayList<>();
        initializeStats();
    }

    /**
     * Initialise les statistiques en fonction de la classe.
     */
    private void initializeStats() {
        switch (characterClass.toLowerCase()) {
            case "sorcier" -> {
                this.health = MAX_HEALTH ;
                this.mana = MAX_MANA;
                this.money = SORCIER_START_MONEY;
                this.xp = SORCIER_START_XP;
            }
            case "elfe" -> {
                this.health = MAX_HEALTH;
                this.mana = MAX_MANA * 0.7;
                this.money = ELFE_START_MONEY;
                this.xp = ELFE_START_XP;
            }
            case "guerrier" -> {
                this.health = MAX_HEALTH;
                this.mana = MAX_MANA * 0.5;
                this.money = GUERRIER_START_MONEY;
                this.xp = GUERRIER_START_XP;
            }
            default -> throw new IllegalArgumentException("Classe invalide.");
        }
    }
    public boolean useManaToDestroyObstacle(Obstacle obstacle) {
        final double MANA_COST = 10.0; // Coût en mana pour détruire un obstacle
        if (mana >= MANA_COST) {
            mana -= MANA_COST;
            if (obstacle != null) {
                obstacle.hit(obstacle.health); // Détruit complètement l'obstacle
            }
            return true; // Succès
        }
        return false; // Échec (pas assez de mana)
    }

    // Getters pour les attributs principaux
    public String getName() {
        return name;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public double getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    public double getMoney() {
        return money;
    }

    public double getXP() {
        return xp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Weapon> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    // Setters et mises à jour
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        this.name = name;
    }

    public void takeDamage(double damage) {
        if (damage < 0) throw new IllegalArgumentException("Les dégâts ne peuvent pas être négatifs.");
        this.health = clamp(health - damage, 0, MAX_HEALTH);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void gainXP(double xp) {
        if (xp < 0) throw new IllegalArgumentException("L'XP ne peut pas être négative.");
        this.xp += xp;
    }

    public void addGold(double amount) {
        if (amount < 0) throw new IllegalArgumentException("L'argent ajouté ne peut pas être négatif.");
        this.money += amount;
    }

    public void deductMoney(double amount) {
        if (amount < 0) throw new IllegalArgumentException("L'argent déduit ne peut pas être négatif.");
        if (this.money < amount) {
            throw new IllegalArgumentException("Pas assez d'argent.");
        }
        this.money -= amount;
    }

    public void addWeaponToInventory(Weapon weapon) {
        if (weapon == null) throw new IllegalArgumentException("L'arme ajoutée ne peut pas être null.");
        inventory.add(weapon);
    }

    public double getAttackPower() {
        double baseDamage = switch (characterClass.toLowerCase()) {
            case "sorcier" -> SORCIER_ATTACK;
            case "elfe" -> ELFE_ATTACK;
            case "guerrier" -> GUERRIER_ATTACK;
            default -> 19.0;
        };
        return baseDamage + Math.random() * 5; // Ajoute un facteur aléatoire
    }

    private Weapon equippedWeapon;

    public void equipWeapon(Weapon weapon) {
        if (!inventory.contains(weapon)) {
            throw new IllegalArgumentException("Vous devez acheter l'arme avant de l'équiper.");
        }
        this.equippedWeapon = weapon;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
    public String getEquippedWeaponName() {
        return equippedWeapon != null ? equippedWeapon.getName() : "None";
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setCharacterClass(String playerClass) {
        if (playerClass == null || !VALID_CLASSES.contains(playerClass)) {
            throw new IllegalArgumentException("Classe invalide. Veuillez choisir entre Sorcier, Elfe ou Guerrier.");
        }
        this.characterClass = playerClass.toLowerCase();
        initializeStats();
    }

}
