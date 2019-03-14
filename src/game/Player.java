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

    private int mana = 0;
    private boolean isOponent = false;
    private boolean isPlayerTurn = false;

    private List<Card> deck = new ArrayList<>();
    private List<Card> hand = new ArrayList<>();
    private List<Card> pack = new ArrayList<>();

    private final Strategy strategy;
    private final String name;

    public Player(String name, Strategy strategy, boolean isOponent) {
        this.name = name;
        this.strategy = strategy;
        this.health = MAXIMUM_HEALTH;
        this.mana = MAXIMUM_MANA_SLOTS;
        this.isOponent = isOponent;
        this.pack = DeckGenerator.getDeck(GameConfig.CARDS_IN_PACK);
        if(isOponent){
            for(int i = 0; i < STARTING_HAND_SIZE+1; i++){
                this.drawCard();
            }
        }else{
            for(int i = 0; i < STARTING_HAND_SIZE; i++){
                this.drawCard();
            }
        }
    }

    public Player(String name, Strategy strategy, int health,
                  int mana, List<Card> pack, boolean isOponent) {
        this.name = name;
        this.strategy = strategy;
        this.health = health;
        this.mana = mana;
        this.pack = pack;
        this.isOponent = isOponent;
        if(isOponent){
            for(int i = 0; i < STARTING_HAND_SIZE+1; i++){
                this.drawCard();
            }
        }else{
            for(int i = 0; i < STARTING_HAND_SIZE; i++){
                this.drawCard();
            }
        }
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

    public int getNumberOfPackCards() {
        return pack.size();
    }

    public Integer getNumberOfHandCardsWithManaCost(int manaCost) {
        return (int) hand.stream().filter(card -> card.getMana() == manaCost).count();
    }

    public int getNumberOfHandCards() {
        return hand.size();
    }

    public void drawCard() {
        if (getNumberOfPackCards() == 0) {
            logger.info(this + " bleeds out!");
            health--;
        } else {
            Card card = pack.get(random.nextInt(deck.size()));
            pack.remove(card);
            logger.info(this + " draws card: " + card);
            if (getNumberOfHandCards() < MAXIMUM_HAND_SIZE) {
                hand.add(card);
            } else {
                logger.info(this + " drops card " + card + " from overload!");
            }
        }
    }

    public void refillMana() {
        this.mana = MAXIMUM_MANA_SLOTS;
    }

    public void drawStartingHand() {
        for (int i = 0; i < STARTING_HAND_SIZE; i++) {
            drawCard();
        }
    }

    private void receiveDamage(int damage) {
        this.health -= damage;
    }

    public boolean canPlayCards() {
        return hand.stream().filter(card -> card.getMana() <= mana).count() > 0;
    }

    public void destroyCard(Card card){
        deck.remove(card);
    }

    public void playCard(Card card, Player opponent, Action action){
        switch(action){
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

    public void playCard(Card card, Player opponent, Card opponentCard, Action action){
        card.damage(opponentCard.getAttack());
        opponentCard.damage(card.getAttack());
        if(card.getHealth() <= 0){
            this.destroyCard(card);
        }
        if(opponentCard.getHealth() <= 0){
            opponent.destroyCard(opponentCard);
        }
    }

    public void endTour(){
        this.isPlayerTurn = false;
    }

    public void beginTour(){
        this.isPlayerTurn = true;
    }

    @Override
    public String toString() {
        return "Player:" + name + "{" +
                "health=" + health +
                ", mana=" + mana +
                ", hand=" + hand +
                ", deck=" + deck +
                '}';
    }
}