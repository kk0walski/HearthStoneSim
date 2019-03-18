import game.Card;
import game.DeckGenerator;
import game.Game;
import game.Player;
import gameconfig.GameConfig;
import strategy.AttackMonsterStrategy;
import strategy.RandomStrategy;

import java.util.List;

public class Demo {

    public static void main(String... args) {
        // players can share same deck from generator, order is mixed by drawing random card
        List<Card> deck = DeckGenerator.getDeck(GameConfig.CARDS_IN_DECK);
        new Game(new Player("Human", new AttackMonsterStrategy(), deck, false),
                new Player("CPU", new RandomStrategy(), deck, true)).run();

    }
}
