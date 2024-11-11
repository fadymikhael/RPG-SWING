package cours3;

public class Bow extends Weapon {

    private static final double DAMAGE = 15;
    private static final double PRICE = 20;
    private static final String NAME = "Bow";

    private static final double MONSTER_DAMAGE_RATIO = 1.0;
    private static final double OBSTACLE_DAMAGE_RATIO = 0.5;

    // Constructeur appelant le constructeur de la classe parente Weapon
    public Bow() {
        super(DAMAGE, PRICE, NAME, MONSTER_DAMAGE_RATIO, OBSTACLE_DAMAGE_RATIO);
    }

    @Override
    public String asciiArt() {
        return
                "        4$$-.                                     \n" +
                        "        4   \".                                       \n" +
                        "        4    ^.                                      \n" +
                        "        4     $                                      \n" +
                        "        4     'b                                     \n" +
                        "        4      \"b.                                   \n" +
                        "        4        $                                   \n" +
                        "        4        $r                                  \n" +
                        "        4        $F                                  \n" +
                        "-$b========4========$b====*P=-                        \n" +
                        "        4       *$$F                                 \n" +
                        "        4        $$\"                                 \n" +
                        "        4       .$F                                  \n" +
                        "        4       dP                                   \n" +
                        "        4      F                                     \n" +
                        "        4     @                                      \n" +
                        "        4    .                                       \n" +
                        "        J.                                           \n" +
                        "       '$$                                           \n";
    }
}
