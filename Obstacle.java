package cours3;

public class Obstacle extends Destructible {

    private static final double BASE_LIFE = 50; // Vie initiale de l'obstacle
    private static final double BASE_DAMAGE = 5; // Dégâts de base de l'obstacle

    public Obstacle() {
        super(BASE_LIFE); // Initialise la santé avec la valeur de base
    }

    // Vérifie si l'obstacle est détruit
    public boolean isDestroyed() {
        return this.health <= 0;
    }
}
