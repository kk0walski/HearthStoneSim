package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import exception.IllegalMoveException;
import gameconfig.GameConfig;
import strategy.Strategy;

public class Player {

    private static final Logger logger = Logger.getLogger("Game");

    Random random = new Random();

    private static final int STARTING_HAND_SIZE = 3;
    private static final int MAXIMUM_HAND_SIZE = 5;
    private static final int MAXIMUM_HEALTH = 20;
    private static final int MAXIMUM_MANA_SLOTS = 10;
    private static final int MAX_MANA_COST = 9;

    private int health = MAXIMUM_HEALTH;

    private int manaSlots = 0;
    private int mana = 0;

    private List<Card> deck = new ArrayList<>();
    private List<Card> hand = new ArrayList<>();

    private final Strategy strategy;
    private final String name;

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
        this.deck = DeckGenerator.getDeck(GameConfig.CARDS_IN_DECK);
    }

    public Player(String name, Strategy strategy, int health,
                  int manaSlots, int mana, List<Card> hand) {
        this.name = name;
        this.strategy = strategy;
        this.health = health;
        this.manaSlots = manaSlots;
        this.mana = mana;
        this.hand = hand;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public String getDeck() { return deck.toString(); }

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
        Action action = move.getAction();
        Optional<Card> card = move.getCard();
        switch (action){
            case ATTACK_CARD:
                Optional<Card> enemyCard = move.getEnemyCard();
                if(card.isPresent() && enemyCard.isPresent()){
                    playCard(card.get(), opponent, enemyCard.get(), move.getAction());
                }else{
                    throw new IllegalMoveException("No card can be played from hand " + hand
                            + " with (" + mana + ") mana or no card in player" + deck + " or in enemy deck " + opponent.getDeck() + " .");
                }
                break;
            case PLAY_CARD:
                if(card.isPresent()){
                    playCard(card.get(), opponent, move.getAction());
                }
            case ATTACK_HERO:
                if(card.isPresent()){
                    playCard(card.get(), opponent, move.getAction());
                }
        }

        logger.info("PLAYING CARD: " + card + "!");
        if (card.isPresent()) {
            playCard(card.get(), opponent, move.getAction());
        } else {
            throw new IllegalMoveException("No card can be played from hand " + hand + " with (" + mana + ") mana.");
        }
    }

    void destroyCard(Card card){
        deck.remove(card);
    }

    void playCard(Card card, Player opponent, Action action) {
        switch (action) {
            case PLAY_CARD:
                if (mana < card.getMana()) {
                    throw new IllegalMoveException("Insufficient Mana (" + mana + ") to pay for card " + card + ".");
                }
                logger.info(this + " plays card " + card + " for " + action);
                mana -= card.getMana();
                deck.add(card);
                hand.remove(card);
                break;
            case ATTACK_HERO:
                logger.info(this + " plays card " + card + " for " + action);
                opponent.receiveDamage(card.getAttack());
                break;
            default:
                throw new IllegalMoveException("Unrecognized game action: " + action);
        }
    }

    void playCard(Card card, Player opponent, Card opponentCard, Action action){
        card.damage(opponentCard.getAttack());
        opponentCard.damage(card.getAttack());
        if(card.getHealth() <= 0){
            this.destroyCard(card);
        }
        if(opponentCard.getHealth() <= 0){
            opponent.destroyCard(opponentCard);
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