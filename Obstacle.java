package cours3;

public class Obstacle extends Destructible {

    private static final double LIFE = 50; // Vie initiale de l'obstacle

    // Constructeur qui initialise la santé de l'obstacle avec la valeur par défaut
    public Obstacle() {
        super(LIFE); // Appelle le constructeur de la classe parente pour initialiser la santé
    }

    // Redéfinition de la méthode hit() pour infliger des dégâts à l'obstacle
    @Override
    public void hit(double damage) {
        this.health -= damage; // Réduit la santé de l'obstacle en fonction des dégâts reçus
    }
}
