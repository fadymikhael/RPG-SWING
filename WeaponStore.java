package cours3;

import java.util.ArrayList;

public class WeaponStore {

    private ArrayList<Weapon> weapons;

    // Constructeur qui initialise la liste des armes
    public WeaponStore() {
        this.weapons = new ArrayList<>();
        this.weapons.add(new Axe());
        this.weapons.add(new Hammer());
        this.weapons.add(new Bow());
    }

    // Méthode pour récupérer la liste des armes
    public ArrayList<Weapon> getWeapons() {
        return this.weapons;
    }

    // Méthode pour afficher les armes et leurs ASCII Art
    public void printWeapons() {
        for (Weapon w : this.weapons) {
            System.out.println(w.toString() + "\n" + w.asciiArt());
        }
    }
    public Weapon buyWeapon(String name, Player player) {
        for (Weapon weapon : weapons) {
            if (weapon.getName().equalsIgnoreCase(name) && player.getMoney() >= weapon.getPrice()) {
                player.deductMoney(weapon.getPrice());
                System.out.println("Vous avez acheté " + weapon.getName() + " pour " + weapon.getPrice() + " pièces d'or.");
                return weapon;
            }
        }
        System.out.println("Achat échoué. L'arme n'existe pas ou vous n'avez pas assez d'argent.");
        return null; // Retourne null si l'achat échoue
    }

}
