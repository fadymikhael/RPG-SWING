package cours3;

// Classe représentant un objet destructible avec une certaine quantité de santé
public class Destructible {

    // Attribut représentant la santé de l'objet
    protected double health;

    // Constructeur qui initialise la santé de l'objet
    public Destructible(double h) {
        this.health = h;
    }

    // Méthode pour infliger des dégâts à l'objet
    public void hit(double d) {
        this.health -= d; // Réduit la santé de l'objet en fonction des dégâts reçus
    }

}
