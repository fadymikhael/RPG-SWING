package cours3;

public abstract class Weapon {
    protected double damage;
    protected double price;
    protected String name;
    protected double monsterDamageRatio;

    public Weapon(double damage, double price, String name, double monsterRatio) {
        if (damage < 0 || price < 0 || monsterRatio < 0) {
            throw new IllegalArgumentException("Les valeurs ne peuvent pas être négatives.");
        }
        this.damage = damage;
        this.price = price;
        this.name = name;
        this.monsterDamageRatio = monsterRatio;
    }

    public double calculateDamageForMonster() {
        return this.damage * this.monsterDamageRatio;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    public double getPrice() {
        return price;
    }

    public abstract String asciiArt();

    @Override
    public String toString() {
        return String.format("%s - Dégâts : %.2f - Prix : %.2f", name, damage, price);
    }
}
