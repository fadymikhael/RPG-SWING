package cours3;

public class Hammer extends Weapon {

    private static final double DAMAGE = 10;
    private static final double PRICE = 10;
    private static final String NAME = "Hammer";
    private static final double MONSTER_DAMAGE_RATIO = 0.7;

    // Constructeur appelant le constructeur de la classe parente Weapon
    public Hammer() {
        super(DAMAGE, PRICE, NAME, MONSTER_DAMAGE_RATIO);
    }

    @Override
    public String asciiArt() {
        return
                "      ____  \n" +
                        "     |    | \n" +
                        "  ___|____|___ \n" +
                        " |            | \n" +
                        " |____________| \n" +
                        "      |||| \n" +
                        "      |||| \n" +
                        "      |||| \n" +
                        "      |||| \n" +
                        "      |||| \n" +
                        "      |||| \n" +
                        "      |||| \n";
    }
}
