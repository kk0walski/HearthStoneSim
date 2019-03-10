package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import exception.IllegalMoveException;
import strategy.Strategy;

public class Player {

    private static final Logger logger = Logger.getLogger("Game");

    Random random = new Random();

    private static final int STARTING_HAND_SIZE = 3;
    private static final int MAXIMUM_HAND_SIZE = 5;
    private static final int MAXIMUM_HEALTH = 30;
    private static final int MAXIMUM_MANA_SLOTS = 10;

    private int health = MAXIMUM_HEALTH;

    private int manaSlots = 0;
    private int mana = 0;

    private List<Card> deck = Card.list(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8);
    private List<Card> hand = new ArrayList<>();

    private final Strategy strategy;
    private final String name;

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    Player(String name, Strategy strategy, int health, int manaSlots, int mana, List<Card> deck, List<Card> hand) {
        this.name = name;
        this.strategy = strategy;
        this.health = health;
        this.manaSlots = manaSlots;
        this.mana = mana;
        this.deck = deck;
        this.hand = hand;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getNumberOfDeckCardsWithManaCost(int manaCost) {
        return (int) deck.stream().filter(card -> card.getMana() == manaCost).count();
    }

    public int getNumberOfDeckCards() {
        return deck.size();
    }

    public Integer getNumberOfHandCardsWithManaCost(int manaCost) {
        return (int) hand.stream().filter(card -> card.getMana() == manaCost).count();
    }

    public int getNumberOfHandCards() {
        return hand.size();
    }

    public void drawCard() {
        if (getNumberOfDeckCards() == 0) {
            logger.info(this + " bleeds out!");
            health--;
        } else {
            Card card = deck.get(random.nextInt(deck.size()));
            deck.remove(card);
            logger.info(this + " draws card: " + card);
            if (getNumberOfHandCards() < MAXIMUM_HAND_SIZE) {
                hand.add(card);
            } else {
                logger.info(this + " drops card " + card + " from overload!");
            }
        }
    }

    public int getManaSlots() {
        return manaSlots;
    }

    public void giveManaSlot() {
        if (manaSlots < MAXIMUM_MANA_SLOTS) {
            manaSlots++;
        }
    }

    public void refillMana() {
        mana = manaSlots;
    }

    public void drawStartingHand() {
        for (int i = 0; i < STARTING_HAND_SIZE; i++) {
            drawCard();
        }
    }

    private void heal(int amount) {
        health = Math.min(health + amount, MAXIMUM_HEALTH);
    }

    private void receiveDamage(int damage) {
        health -= damage;
    }

    public boolean canPlayCards() {
        return hand.stream().filter(card -> card.getMana() <= mana).count() > 0;
    }

    public void playCard(Player opponent) {
        Move move = strategy.nextMove(mana, health, hand);
        Optional<Card> card = move.getCard();
        if (card.isPresent()) {
            playCard(card.get(), opponent, move.getAction());
        } else {
            throw new IllegalMoveException("No card can be played from hand " + hand + " with (" + mana + ") mana.");
        }
    }

    void playCard(Card card, Player opponent, Action action) {
        if (mana < card.getMana()) {
            throw new IllegalMoveException("Insufficient Mana (" + mana + ") to pay for card " + card + ".");
        }
        logger.info(this + " plays card " + card + " for " + action);
        mana -= card.getMana();
        hand.remove(card);
        switch (action) {
            case DAMAGE:
                opponent.receiveDamage(card.getMana());
                break;
            case HEALING:
                this.heal(card.getMana());
                break;
            default:
                throw new IllegalMoveException("Unrecognized game action: " + action);
        }
    }

    @Override
    public String toString() {
        return "Player:" + name + "{" +
                "health=" + health +
                ", mana=" + mana + "/" + manaSlots +
                ", hand=" + hand +
                ", deck=" + deck +
                '}';
    }

}