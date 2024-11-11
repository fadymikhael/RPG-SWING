package cours3;
import java.util.List;
import java.util.ArrayList;

class Player {
    // Attributs de base du joueur
    private String name;
    private String characterClass;
    private double health;
    private double mana;
    private double money;
    private double xp;
    private List<Weapon> inventory;

    // Coûts de mana pour chaque classe
    private static final double SORCIER_MANA_COST = 20.0;
    private static final double ELFE_MANA_COST = 15.0;
    private static final double GUERRIER_MANA_COST = 10.0;

    // Constructeur initialisant le joueur
    public Player(String name, String characterClass) {
        if (!characterClass.equalsIgnoreCase("Sorcier") &&
                !characterClass.equalsIgnoreCase("Elfe") &&
                !characterClass.equalsIgnoreCase("Guerrier")) {
            throw new IllegalArgumentException("Classe invalide. Veuillez choisir entre Sorcier, Elfe ou Guerrier.");
        }
        this.name = name;
        this.characterClass = characterClass;
        this.health = 100;
        this.mana = 50;
        this.money = 100;
        this.xp = 0;
        this.inventory = new ArrayList<>();
    }

    public void displayStats() {
        System.out.println("Nom: " + name);
        System.out.println("Classe: " + characterClass);
        System.out.printf("Santé: %.2f\n", health);
        System.out.printf("Mana: %.2f\n", mana);
        System.out.printf("Argent: %.2f\n", money);
        System.out.printf("XP: %.2f\n", xp);
    }

    public double getMoney() {
        return money;
    }

    public double getXP() {
        return xp;
    }

    public String getName() {
        return name;
    }

    public void deductMoney(double amount) {
        this.money -= amount;
    }

    public void addGold(double amount) {
        this.money += amount;
        System.out.println("Vous avez gagné " + amount + " pièces d'or.");
    }

    public void useSpecialPower(Monster monster) {
        switch (characterClass.toLowerCase()) {
            case "sorcier":
                if (mana >= SORCIER_MANA_COST) {
                    System.out.println("Le Sorcier lance une boule de feu ! Inflige 30 points de dégâts.");
                    mana -= SORCIER_MANA_COST;
                    monster.hit(30); // Applique les 30 points de dégâts au monstre
                } else {
                    System.out.println("Pas assez de mana pour lancer la boule de feu.");
                }
                break;
            // Autres classes comme l'Elfe et le Guerrier
            case "elfe":
                if (mana >= ELFE_MANA_COST) {
                    System.out.println("L'Elfe utilise sa capacité de régénération et récupère 15 points de santé.");
                    health = Math.min(100, health + 15); // Santé limitée à 100
                    mana -= ELFE_MANA_COST;
                } else {
                    System.out.println("Pas assez de mana pour utiliser la capacité de régénération.");
                }
                break;
            case "guerrier":
                if (mana >= GUERRIER_MANA_COST) {
                    System.out.println("Le Guerrier entre en rage et double ses dégâts pendant le prochain tour !");
                    mana -= GUERRIER_MANA_COST;
                } else {
                    System.out.println("Pas assez de mana pour entrer en rage.");
                }
                break;
            default:
                System.out.println("Classe inconnue, aucune capacité spéciale disponible.");
        }
    }


    public void takeDamage(double damage) {
        this.health -= damage;
        System.out.printf("Vous avez subi %.2f points de dégâts. Santé restante: %.2f\n", damage, health);
        if (health <= 0) {
            System.out.println("Vous êtes mort. Fin du jeu.");
            System.exit(0); // Termine le programme si la santé est à 0 ou moins
        }
    }

    public void addWeaponToInventory(Weapon weapon) {
        inventory.add(weapon);
    }

    public void showInventory() {
        System.out.printf("Santé actuelle : %.2f points\n", health);
        if (inventory.isEmpty()) {
            System.out.println("Votre inventaire est vide.");
        } else {
            System.out.println("Votre inventaire :");
            for (Weapon weapon : inventory) {
                System.out.println(weapon.toString());
            }
        }
        System.out.println("Argent actuel : " + money + " pièces d'or.");
    }

    public void gainXP(double xp) {
        this.xp += xp;
        System.out.println("Vous avez gagné " + xp + " points d'XP !");
    }

    public void checkXPStatus() {
        if (xp >= 100) {
            System.out.println("Félicitations ! Vous êtes maintenant un aventurier expérimenté ! (XP : " + xp + ")");
        } else if (xp >= 50) {
            System.out.println("Vous avez atteint un bon niveau d'expérience. (XP : " + xp + ")");
        } else {
            System.out.println("Continuez à explorer pour gagner plus d'XP ! (XP : " + xp + ")");
        }
    }
}
