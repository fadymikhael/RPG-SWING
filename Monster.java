package cours3;

public class Monster {
    private double health;
    private double xpValue;
    private double goldValue;

    public Monster() {
        this.health = 50; // Exemple
        this.xpValue = 20; // Exemple
        this.goldValue = 10; // Exemple
    }

    public double attackPlayer() {
        return 10 + Math.random() * 10;
    }


    public void hit(double damage) {
        health -= damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public double grantXP() {
        return xpValue;
    }

    public double grantGold() {
        return goldValue;
    }
}

