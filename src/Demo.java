import game.Game;
import game.Player;
import strategy.AttackHeroStrategy;

public class Demo {

    public static void main(String... args) {
        new Game(new Player("Human", new AttackHeroStrategy(), false),
                new Player("CPU", new AttackHeroStrategy(), true)).run();
    }
}
