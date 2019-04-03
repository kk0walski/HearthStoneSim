package Heroes;

import Cards.Card;
import Engine.Game;

import java.util.List;

public class DefaultHero extends AbstractHero {

    public DefaultHero(Game game, String name, List<Card> initialDeck, boolean isOponent, int initialHandSize) {
        super(game, name, initialDeck, isOponent, initialHandSize);
        // TODO Auto-generated constructor stub
    }

    public DefaultHero() {
        super(null, null, null, false, -1);
    }
}
