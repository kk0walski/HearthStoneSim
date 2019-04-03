import Engine.Game;
import Heroes.Hero;

import java.util.List;

public class Demo {

    public static void main(String... args) {
        Game game = new Game();
        game.initializeAndStartStandardGame();
        Hero hero = game.getActiveHero();
        hero.startRound();
    }
}
