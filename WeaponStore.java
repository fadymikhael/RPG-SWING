package cours3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeaponStore {
    private final List<Weapon> weapons;

    /**
     * Constructeur qui initialise la liste des armes du magasin.
     */
    public WeaponStore() {
        this.weapons = new ArrayList<>();
        initializeWeapons();
    }

    /**
     * Initialise les armes disponibles dans le magasin.
     */
    private void initializeWeapons() {
        this.weapons.add(new Axe());
        this.weapons.add(new Hammer());
        this.weapons.add(new Bow());
    }

    /**
     * Récupère la liste des armes disponibles.
     *
     * @return Une copie de la liste des armes pour éviter les modifications externes.
     */
    public List<Weapon> getWeapons() {
        return new ArrayList<>(this.weapons);
    }

    /**
     * Affiche la liste des armes avec leurs caractéristiques et leur ASCII Art.
     */
    public void printWeapons() {
        if (weapons.isEmpty()) {
            System.out.println("Le magasin ne contient aucune arme.");
            return;
        }

        System.out.println("Armes disponibles dans le magasin :");
        for (Weapon weapon : this.weapons) {
            System.out.println(weapon + "\n" + weapon.asciiArt());
        }
    }

    /**
     * Vérifie si une arme est disponible dans le magasin.
     *
     * @param name Nom de l'arme.
     * @return `true` si l'arme est disponible, sinon `false`.
     */
    public boolean isWeaponAvailable(String name) {
        return weapons.stream().anyMatch(weapon -> weapon.getName().equalsIgnoreCase(name));
    }

    /**
     * Permet à un joueur d'acheter une arme, si elle est disponible et abordable.
     *
     * @param name   Nom de l'arme à acheter.
     * @param player Le joueur qui achète l'arme.
     * @return L'arme achetée si l'achat est réussi, sinon `null`.
     */
    public Weapon buyWeapon(String name, Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Le joueur ne peut pas être null.");
        }

        Optional<Weapon> optionalWeapon = weapons.stream()
                .filter(weapon -> weapon.getName().equalsIgnoreCase(name))
                .findFirst();

        if (optionalWeapon.isEmpty()) {
            System.out.println("Achat échoué. L'arme '" + name + "' n'existe pas dans le magasin.");
            return null;
        }

        Weapon weapon = optionalWeapon.get();

        if (player.getMoney() < weapon.getPrice()) {
            System.out.println("Achat échoué. Vous n'avez pas assez d'argent pour acheter '" + weapon.getName() + "'.");
            return null;
        }

        // Le joueur peut acheter l'arme
        player.deductMoney(weapon.getPrice());
        weapons.remove(weapon); // Retire l'arme du magasin
        return weapon;
    }
}
