package cours3;

public class Monster {
    private  static  final double LIFE = 50;
    private double health;

    public  Monster (){
        this.health = LIFE;
    }

    public  void hit(double d){

        this.health-=d;
    }
}
