package cours3;

public class Axe extends Weapon {
    // Déclaration des constantes
    private static final double DAMAGE = 20;
    private static final double PRICE = 25;
    private static final String NAME = "AXE";
    private static final double MONSTER_DAMAGE_RATIO = 0.8;

    // Constructeur appelant le constructeur de la classe parente Weapon
    public Axe() {
        super(DAMAGE, PRICE, NAME, MONSTER_DAMAGE_RATIO);
    }

    // Méthode pour afficher l'art ASCII de la hache
    @Override
    public String asciiArt() {
        return """
               ,:\\      /:.
             //  \\_()_/  \\\\
             ||   |    |   ||
             ||   |    |   ||
             ||   |____|   ||
              \\\\  / || \\  //
               `:/  ||  \\;'
                    ||
                    ||;
               """;
    }

}
