package Heroes;

import Cards.Card;
import Cards.Minion;
import Engine.Game;
import Engine.DeckGenerator;
import Engine.GameConfig;
import MCTS.Node;
import Moves.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHero implements Hero {

    public static final int STARTING_HAND_SIZE = 4;
    public static final int MAXIMUM_HAND_SIZE = 7;
    public static final int MAXIMUM_HEALTH_POINTS = 20;
    public static final int MAXIMUM_MANA_POINTS = 10; // todo defaultowo powinno byc 10
    public static final int INITIAL_HEALTH_POINTS = 20;
    public static final int INITIAL_MANA_POINTS = 20;
    public static final int INITIAL_PUNISH_FOR_EMPTY_DECK = 1;
    public static final int INITIAL_ROUND_NUMBER = 0;

    protected String name;
    protected int health;
    protected int mana;
    protected int round;
    protected int punishForEmptyDeck;
    protected List<Card> deck;
    protected List<Card> hand;
    protected List<Card> board;
    protected Game game;
    protected List<Move> availableMoves;
    protected List<Card> activatedMinions;
    private int increasedMana;
    private Card lastPickedCardBackup;
    private List<Move> movesInRoundBackup;
    private List<Move> availableMovesBackup;
    private boolean isOponent = false;

    public AbstractHero(Game game, String name, List<Card> initialDeck, boolean isOponent, int initialHandSize) {
        this.game = game;
        this.name = name;
        round = INITIAL_ROUND_NUMBER;
        punishForEmptyDeck = INITIAL_PUNISH_FOR_EMPTY_DECK;
        deck = initialDeck;
        this.isOponent = isOponent;
        if (isOponent) {
            initHand(initialHandSize);
        } else {
            initHand(initialHandSize - 1);
        }
        board = new ArrayList<>();
        availableMoves = new ArrayList<>();
        health = INITIAL_HEALTH_POINTS;
        mana = INITIAL_MANA_POINTS;
    }

    public AbstractHero(Game game, String name, List<Card> initialDeck, boolean isOponent) {
        this.game = game;
        this.name = name;
        round = INITIAL_ROUND_NUMBER;
        punishForEmptyDeck = INITIAL_PUNISH_FOR_EMPTY_DECK;
        deck = initialDeck;
        this.isOponent = isOponent;
        if (isOponent) {
            initHand(STARTING_HAND_SIZE);
        } else {
            initHand(STARTING_HAND_SIZE - 1);
        }
        board = new ArrayList<>();
        availableMoves = new ArrayList<>();
        health = INITIAL_HEALTH_POINTS;
        mana = INITIAL_MANA_POINTS;
    }

    public AbstractHero(Game game, String name, boolean isOponent) {
        this.game = game;
        this.name = name;
        round = INITIAL_ROUND_NUMBER;
        punishForEmptyDeck = INITIAL_PUNISH_FOR_EMPTY_DECK;
        deck = DeckGenerator.getDeck(GameConfig.CARDS_IN_DECK, this);
        this.isOponent = isOponent;
        if (isOponent) {
            initHand(STARTING_HAND_SIZE);
        } else {
            initHand(STARTING_HAND_SIZE - 1);
        }
        board = new ArrayList<>();
        availableMoves = new ArrayList<>();
        health = INITIAL_HEALTH_POINTS;
        mana = INITIAL_MANA_POINTS;
    }

    public void startRound() {
        activateMinionsOnBoard();
        increaseMana();
        pickCardFromDeck();
        notifyIfDeadHero();
        generateAvailableMoves();
    }

    @Override
    public void revertStartRound() {
        deactivateMinionsOnBoard();
        decreaseMana(increasedMana);
        revertPreviousPickCardFromDeck();
        revertDeadHeroNotification();
        revertAvailableMovesGeneration();
    }

    @Override
    public boolean performMove(Move moveToDo) {
        if (availableMoves.contains(moveToDo)) {
            if (moveToDo instanceof EndRound) {
                endRound();
                return true;
            }
            moveToDo.performMove();
            generateAvailableMoves();
            notifyIfDeadHero();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void rollback(Move moveToRevert) {
        moveToRevert.rollback();
        revertAvailableMovesGeneration();
        revertDeadHeroNotification();
    }

    private void revertAvailableMovesGeneration() {
        availableMoves = availableMovesBackup;
    }

    private void revertPreviousPickCardFromDeck() {
        if (punishForEmptyDeck > 0) {
            health = health + punishForEmptyDeck;
            punishForEmptyDeck--;
        } else {
            deck.add(hand.get(hand.size() - 1));
            hand.remove(hand.get(hand.size() - 1));
        }
    }

    protected void revertDeadHeroNotification() {
        game.checkForGameEnd();
        if (game.isGameOver()) {
            game.setGameOver(false);
            game.setWinner(null);
        }
    }

    private void notifyIfDeadHero() {
        if (isDead()) {
            notifyAboutDeadHero();
        }
    }

    private void notifyAboutDeadHero() {
        game.deadHeroNotification(this);
    }


    private void increaseMana() {
        if (mana < MAXIMUM_MANA_POINTS) {
            mana++;
            increasedMana = 1;
        } else {
            increasedMana = 0;
        }
    }

    @Override
    public void increaseMana(int value) {
        mana += value;
        if (mana > MAXIMUM_MANA_POINTS) {
            mana = MAXIMUM_MANA_POINTS;
        }
    }

    private void activateMinionsOnBoard() {
        activatedMinions = new ArrayList<>();
        for (Card c : board) {
            if (!((Minion) c).isActive()) {
                activatedMinions.add(c);
                ((Minion) c).setActive(true);
            }
        }
    }

    private void deactivateMinionsOnBoard() {
        for (Card c : activatedMinions) {
            ((Minion) c).setActive(false);
        }
    }

    private void initHand(int initialHandSize) {
        hand = new ArrayList<>();
        for (int i = 0; i < initialHandSize; i++) {
            pickCardFromDeck();
        }
    }

    private void pickCardFromDeck() {
        if (deck.isEmpty()) {
            punishForEmptyDeck++;
            health = health - punishForEmptyDeck;
            return;
        }

        if (hand.size() == MAXIMUM_HAND_SIZE) {
            return;
        }

        lastPickedCardBackup = deck.get(0);
        hand.add(deck.get(0));
        hand.get(hand.size() - 1).setOwner(this);
        deck.remove(deck.get(0));
    }

    public void revertDeadMinionNotification(Minion minion) {
        board.add(minion);
    }

    public void generateAvailableMoves() {
        availableMovesBackup = new ArrayList<>(availableMoves);
        availableMoves = possibleMoves();
    }

    public List<Move> possibleMoves() {
        List<Move> possibleMoves = new ArrayList<>();

        for (int cardInHandIndex = 0; cardInHandIndex < this.hand.size(); cardInHandIndex++) {
            if (hand.get(cardInHandIndex).getCost() <= this.mana && hand.get(cardInHandIndex) instanceof Minion) {
                possibleMoves.add(new PutCard(cardInHandIndex, this, game.getEnemyOf(this)));
            }
        }

        for (int cardOnBoardIndex = 0; cardOnBoardIndex < board.size(); cardOnBoardIndex++) {
            if (((Minion) board.get(cardOnBoardIndex)).isActive()) {
                possibleMoves.add(new AttackHero(cardOnBoardIndex, board, game.getEnemyOf(this)));
                for (int cardOnEnemyBoardIndex = 0; cardOnEnemyBoardIndex < game.getEnemyOf(this).getBoard().size(); cardOnEnemyBoardIndex++) {
                    possibleMoves.add(new AttackMinion(cardOnBoardIndex, board, game.getEnemyOf(this).getBoard().get(cardOnEnemyBoardIndex), cardOnEnemyBoardIndex));
                }
            }
        }

        possibleMoves.add(new EndRound(this));

        return possibleMoves;
    }

    public void endRound() {
        if (game.getActiveHero() == null) {
            return;
        }
        if (!(game.getActiveHero().equals(this))) {
        }

        notifyAboutRoundEnd();
    }


    @Override
    public boolean isDead() {
        return health <= 0;
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
    }

    public void revertDamage(int damage) {
        this.health += damage;
        if (health > MAXIMUM_HEALTH_POINTS) {
            this.health = MAXIMUM_HEALTH_POINTS;
        }
    }

    protected void notifyIfAliveHero() {
        if (isDead()) {
            notifyAboutDeadHero();
        }
    }

    public void deadMinionNotification(Minion minion) {
        board.remove(minion);
    }


    private void notifyAboutRoundEnd() {
        game.roundEndNotification();
    }


    @Override
    public void decreaseMana(int value) {
        mana -= value;
    }

    @Override
    public int increaseHealth(int value) {
        int oldHealth = health;

        health += value;

        return value;
    }

    @Override
    public void decreaseHealth(int value) {
        health -= value;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPunishForEmptyDeck() {
        return punishForEmptyDeck;
    }

    public void setPunishForEmptyDeck(int punishForEmptyDeck) {
        this.punishForEmptyDeck = punishForEmptyDeck;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getBoard() {
        return board;
    }

    public void setBoard(List<Card> board) {
        this.board = board;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    public List<Move> getAvailableMoves() {
        return availableMoves;
    }

    public void setAvailableMoves(List<Move> availableMoves) {
        this.availableMoves = availableMoves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Hero deepCopy(Node root) {
        AbstractHero hero = copyHeroInstance();

        hero.setName(new String(name));
        hero.setHealth(getHealth());
        hero.setMana(getMana());
        hero.setRound(getRound());
        hero.setPunishForEmptyDeck(getPunishForEmptyDeck());
        ArrayList<Card> deck = new ArrayList<>();
        for (Card c : getDeck()) {
            Card temp = c.deepCopy();
            temp.setOwner(hero);
            deck.add(temp);
        }
        hero.setDeck(deck);
        ArrayList<Card> hand = new ArrayList<>();
        for (Card c : getHand()) {
            Card temp = c.deepCopy();
            temp.setOwner(hero);
            hand.add(temp);
        }
        hero.setHand(hand);
        ArrayList<Card> board = new ArrayList<>();
        for (Card c : getBoard()) {
            Card temp = c.deepCopy();
            temp.setOwner(hero);
            board.add(temp);
        }
        hero.setBoard(board);

        return hero;
    }

    private AbstractHero copyHeroInstance() {
        AbstractHero res = null;
        if (this instanceof DefaultHero)
            res = new DefaultHero();
        if (this instanceof AgresiveHero)
            res = new AgresiveHero();
        if (this instanceof PassiveHero)
            res = new PassiveHero();
        if (this instanceof RandomHero)
            res = new RandomHero();
        if (this instanceof MctsHero)
            res = new MctsHero();
        return res;
    }

    @Override
    public List<Move> copyMovesTo(Node root, AbstractHero target, List<Move> toCopy) {
        ArrayList<Move> copy = new ArrayList<>();
        for (Move m : toCopy) {
            Move newMove = null;
            Hero enemy = target.game.getEnemyOf(target);
            if (m instanceof AttackHero) {
                newMove = new AttackHero(m.getCardIndex(), target.board, enemy);
            }
            if (m instanceof AttackMinion) {
                AttackMinion source = (AttackMinion) m;
                newMove = new AttackMinion(m.getCardIndex(), target.board,
                        enemy.getBoard().get(source.getMinionIndex()), source.getMinionIndex());
            }
            if (m instanceof EndRound) {
                newMove = new EndRound();
            }
            if (m instanceof PutCard) {
                newMove = new PutCard(m.getCardIndex(), target, enemy);
            }

            copy.add(newMove);
        }
        return copy;
    }

    public List<Move> getMovesInRoundBackup() {
        return movesInRoundBackup;
    }

    public void setMovesInRoundBackup(List<Move> movesInRoundBackup) {
        this.movesInRoundBackup = movesInRoundBackup;
    }

    @Override
    public void chooseRandomSimulationalMove() {
        Move toDo = null;
        while (!(toDo instanceof EndRound)) {
            int bestFound = -1;
            int bestFoundValue = Integer.MIN_VALUE;
            for (int index = 0; index < availableMoves.size(); index++) {
                int currentValue = evaluate(availableMoves.get(index));
                if (currentValue > bestFoundValue) {
                    bestFound = index;
                    bestFoundValue = currentValue;
                }
            }
            toDo = availableMoves.get(bestFound);
            performMove(toDo);
        }
    }

    private int evaluate(Move toDo) {
        return (int) (Math.random() * 100);
    }
}
