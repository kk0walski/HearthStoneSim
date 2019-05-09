package Engine;

import Cards.Minion;
import Cards.Card;
import Heroes.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckGenerator {

    private static final Random RANDOM = new Random();

    public static List<Card> getDeck(int numberOfCards, Hero owner) {
        List<Card> deck = new ArrayList<>();
        while (deck.size() < numberOfCards / 2) {
            Minion temp = new Minion(
                    RANDOM.nextInt(GameConfig.CARD_MAX_HEALTH) + 1,
                    RANDOM.nextInt(GameConfig.CARD_MAX_MANA) + 1,
                    RANDOM.nextInt(GameConfig.CARD_MAX_ATTACK) + 1,
                    owner);
            deck.add(temp.deepCopy());
            deck.add(temp.deepCopy());
        }
        return deck;
    }
}
