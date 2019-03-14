package game;

import gameconfig.GameConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckGenerator {

    private static final Random RANDOM = new Random();

    public static List<Card> getDeck(int numberOfCards) {
        List<Card> deck = new ArrayList<>();
        while (deck.size() < numberOfCards) {
            deck.add(new Card(
                    RANDOM.nextInt(GameConfig.CARD_MAX_HEALTH) + 1,
                    RANDOM.nextInt(GameConfig.CARD_MAX_MANA) + 1,
                    RANDOM.nextInt(GameConfig.CARD_MAX_ATTACK) + 1
            ));
        }
        return deck;
    }
}
