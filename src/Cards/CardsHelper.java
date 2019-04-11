package Cards;

import Engine.GameConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardsHelper {

    /**
     * Represents all cards types that can be used in the game.
     */
    private static List<Card> registeredStandardDeck;
    private static final Random RANDOM = new Random();

    /**
     * Works on copy of registeredStandardDeck. In standard deck, every card appears twice.
     *
     * @return shuffled copy of registeredStandardDeck
     */
    public static List<Card> generateStandardDeck() {
        if (registeredStandardDeck == null) {
            registerStandardDeck(GameConfig.CARDS_IN_DECK / 2);
        }

        List<Card> standardDeckCopyWithDoubledCards = deepDeckCopy(registeredStandardDeck);
        standardDeckCopyWithDoubledCards.addAll(deepDeckCopy(registeredStandardDeck));
        return standardDeckCopyWithDoubledCards;
    }

    /**
     * Shuffles deck. Works on original object.
     *
     * @param cards cards
     * @return original shuffled cards
     */
    public static List<Card> shuffle(List<Card> cards) {
        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
        return cards;
    }

    public static List<Card> deepDeckCopy(List<Card> deckToCopy) {
        List<Card> deckCopy = new ArrayList<>();

        deckToCopy.forEach(cardToCopy -> deckCopy.add(cardToCopy.deepCopy()));

        return deckCopy;
    }

    public static void registerStandardDeck(int size) {
        registeredStandardDeck = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            int health = RANDOM.nextInt(GameConfig.CARD_MAX_HEALTH) + 1;
            int attack = RANDOM.nextInt(GameConfig.CARD_MAX_ATTACK) + 1;
            int mana = (health / 2) + attack < GameConfig.CARD_MAX_MANA ? (health / 2) + attack : GameConfig.CARD_MAX_MANA;

            registerCard(registeredStandardDeck, new Minion(health, mana, attack));
        }
    }

    private static void registerCard(List<Card> deck, Card card) {
        deck.add(card);
    }
}
