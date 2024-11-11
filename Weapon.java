package cours3;

public abstract class Weapon {

    protected double damage;
    protected double price;
    protected String name;

    protected double monster_damage_ratio;
    protected double obstacle_damage_ratio;

    // Constructeur
    public Weapon(double d, double p, String name, double monsterRatio, double obstacleRatio) {
        this.damage = d;
        this.price = p;
        this.name = name;
        this.monster_damage_ratio = monsterRatio;
        this.obstacle_damage_ratio = obstacleRatio;
    }

    // Getter pour damage
    public double getDamage() {
        return this.damage;
    }

    // Getter pour name
    public String getName() {
        return this.name;
    }

    // Getter pour price
    public double getPrice() {
        return this.price;
    }

    // Getters pour les ratios
    public double getMonsterDamageRatio() {
        return this.monster_damage_ratio;
    }

    public double getObstacleDamageRatio() {
        return this.obstacle_damage_ratio;
    }

    // Méthode abstraite pour l'art ASCII
    public abstract String asciiArt();

    // Méthode pour attaquer un monstre
    public void attack(Monster m) {
        m.hit(this.damage * this.getMonsterDamageRatio());
    }

    // Méthode pour attaquer un obstacle
    public void attack(Obstacle o) {
        o.hit(this.damage * this.getObstacleDamageRatio());
    }

    // Correction de la méthode toString()
    @Override
    public String toString() {
        return this.name + " - damage = " + this.damage + " - price = " + this.price;
    }
}
