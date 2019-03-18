package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import exception.IllegalMoveException;
import gameconfig.GameConfig;
import strategy.Strategy;

public class Player {

    private static final Logger logger = Logger.getLogger("Game");

    private Random random = new Random();

    private static final int STARTING_HAND_SIZE = 3;
    private static final int MAXIMUM_HAND_SIZE = 5;
    private static final int MAXIMUM_HEALTH = 20;
    private static final int MAXIMUM_MANA_SLOTS = 10;

    private int health = MAXIMUM_HEALTH;

    private int currentMana = 0;
    private int currentManaSlots = 0;
    private boolean isOponent = false;

    /**
     * cards in players hand
     */
    private List<Card> hand = new ArrayList<>();
    /**
     * all, not draw yet players cards
     */
    private List<Card> deck;
    /**
     * living played cards
     */
    private List<Card> activeMonsters = new ArrayList<>();

    private final Strategy strategy;
    private final String name;

    public Player(String name, Strategy strategy, boolean isOpponent) {
        this.name = name;
        this.strategy = strategy;
        this.isOponent = isOpponent;
        this.deck = DeckGenerator.getDeck(GameConfig.CARDS_IN_DECK);
        if (isOpponent) {
            for (int i = 0; i < STARTING_HAND_SIZE + 1; i++) {
                this.drawCard();
            }
        } else {
            for (int i = 0; i < STARTING_HAND_SIZE; i++) {
                this.drawCard();
            }
        }
    }

    public Player(String name, Strategy strategy, List<Card> deck, boolean isOpponent) {
        this.name = name;
        this.strategy = strategy;
        this.deck = deck;
        this.isOponent = isOpponent;
        if (isOpponent) {
            for (int i = 0; i < STARTING_HAND_SIZE + 1; i++) {
                this.drawCard();
            }
        } else {
            for (int i = 0; i < STARTING_HAND_SIZE; i++) {
                this.drawCard();
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return currentMana;
    }

    public String getDeck() {
        return deck.toString();
    }

    public int getNumberOfDeckCardsWithManaCost(int manaCost) {
        return (int) deck.stream().filter(card -> card.getMana() == manaCost).count();
    }

    private int getNumberOfDeckCards() {
        return deck.size();
    }

    private int getNumberOfHandCards() {
        return hand.size();
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getActiveMonsters() {
        return activeMonsters;
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
                destroyCard(card);
                logger.info(this + " drops card " + card + " from overload!");
            }
        }
    }

    public void addManaSlot() {
        if (currentManaSlots < MAXIMUM_MANA_SLOTS) {
            ++currentManaSlots;
        }
    }

    public void fillMana() {
        currentMana = currentManaSlots;
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
        return hand.stream().anyMatch(card -> card.getMana() <= currentMana);
    }

    public boolean canAnyMonsterAttack() {
        return activeMonsters.stream().anyMatch(card -> card.canAttack);
    }

    private void destroyCard(Card card) {
        deck.remove(card);
    }

    private void destroyMonster(Card card) {
        activeMonsters.remove(card);
    }

    public boolean canMakeMove() {
        return canPlayCards() || canAnyMonsterAttack();
    }

    public void makeMove(Player opponent) {
        // strategy will choose move and make call to makeAction
        strategy.nextMove(this, opponent);
    }

    public void allowAttackForLiveMonsters() {
        activeMonsters.forEach(card -> card.canAttack = true);
    }

    public void makeAction(Card playersCard, Player opponent, Card opponentMonster, Action action) {
        switch (action) {
            case PLAY_CARD:
                if (currentMana < playersCard.getMana()) {
                    throw new IllegalMoveException("Insufficient Mana (" + currentMana + ") to pay for card " + playersCard + ".");
                }
                logger.info(this + " plays card " + playersCard + " for " + action);
                currentMana -= playersCard.getMana();
                hand.remove(playersCard);
                activeMonsters.add(playersCard);
                break;
            case ATTACK_HERO:
                logger.info(this + " attacks hero with " + playersCard);
                if (playersCard == null || opponent == null) {
                    logger.info("Error, passing null for required values");
                    return;
                }
                opponent.receiveDamage(playersCard.getAttack());
                playersCard.canAttack = false;
                break;
            case ATTACK_MONSTER:
                logger.info(this + " attacks monster with " + playersCard);
                if (opponent == null || playersCard == null || opponentMonster == null) {
                    logger.info("Error, passing null for required values");
                    return;
                }
                attackOpponentCard(playersCard, opponent, opponentMonster);
                break;
            default:
                throw new IllegalMoveException("Unrecognized game action: " + action);
        }
    }

    private void attackOpponentCard(Card card, Player opponent, Card opponentCard) {
        card.damage(opponentCard.getAttack());
        card.canAttack = false;
        opponentCard.damage(card.getAttack());
        if (card.isDead()) {
            this.destroyMonster(card);
        }
        if (opponentCard.isDead()) {
            opponent.destroyMonster(opponentCard);
        }
    }

    @Override
    public String toString() {
        return "Player:" + name + "{" +
                "health=" + health +
                ", mana=" + currentMana +
                ", hand=" + hand +
                ", deck=" + deck +
                '}';
    }
}