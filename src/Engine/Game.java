package Engine;

import Cards.Card;
import Cards.CardsHelper;
import Heroes.AbstractHero;
import Heroes.DefaultHero;
import Heroes.Hero;
import Heroes.RandomHero;
import MCTS.Node;

import java.util.List;

public class Game {

    private Hero firstHero;
    private Hero secondHero;
    private Hero activeHero;
    private boolean isGameOver;
    private Hero winner;

    public void initializeAndStartStandardGame() {
        initializeStandardHeroes();
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void deadHeroNotification(Hero hero) {
        if (hero.equals(firstHero)) {
            endWithWinner(secondHero);
        } else if (hero.equals(secondHero)) {
            endWithWinner(firstHero);
        }
    }

    public void revertSwitchActiveHero() {
        activeHero.revertStartRound();

        if (activeHero.equals(firstHero)) {
            activeHero = secondHero;
        } else {
            activeHero = firstHero;
        }
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }


    public void checkForGameEnd() {
        if (firstHero.isDead()) {
            endWithWinner(secondHero);
        } else if (secondHero.isDead()) {
            endWithWinner(firstHero);
        }
    }

    public void endWithWinner(Hero winner) {
        this.winner = winner;
        end();
    }

    public void end() {
        setGameOver(true);
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setWinner(Hero winner) {
        this.winner = winner;
    }

    public Hero getEnemyOf(Hero hero) {
        if (hero == firstHero) {
            return secondHero;
        } else {
            return firstHero;
        }
    }

    public Hero getActiveHero() {
        return activeHero;
    }

    public void roundEndNotification() {
        switchActiveHero();
    }

    public void switchActiveHero() {
        if (activeHero == null) {
            return;
        }

        if (activeHero.equals(firstHero)) {
            activeHero = secondHero;
        } else {
            activeHero = firstHero;
        }
        activeHero.startRound();
    }

    public void gameEndNotification() {
        end();
    }

    private void initializeStandardHeroes() {
        firstHero = new DefaultHero(this, "First Hero", generateStandardDeck(), false, 4);
        assignCardsToHero(firstHero);
        secondHero = new DefaultHero(this, "SecondHero", generateStandardDeck(), true, 4);
        assignCardsToHero(secondHero);
    }

    private void initializeCustomHeroes(List<Card> firstHeroDeck, int firstHeroInitialHandSize, List<Card> secondHeroDeck, int secondHeroInitialHandSize) {
        firstHero = new DefaultHero(this, "First Custom Hero", firstHeroDeck, false, firstHeroInitialHandSize);
        assignCardsToHero(firstHero);
        secondHero = new DefaultHero(this, "Second Custom Hero", secondHeroDeck, true, secondHeroInitialHandSize + 1);
        assignCardsToHero(secondHero);
    }

    private void initializeHumanWithRandomHeroes(List<Card> firstHeroDeck, int firstHeroInitialHandSize, List<Card> secondHeroDeck, int secondHeroInitialHandSize) {
        firstHero = new DefaultHero(this, "First Human Default Hero", firstHeroDeck, false, firstHeroInitialHandSize);
        assignCardsToHero(firstHero);
        secondHero = new RandomHero(this, "Second Random Hero", secondHeroDeck, true, secondHeroInitialHandSize + 1);
        assignCardsToHero(secondHero);
    }

    private void assignCardsToHero(Hero hero) {
        hero.getDeck().forEach(card -> card.setOwner(hero));
    }

    private List<Card> generateStandardDeck() {
        return CardsHelper.generateStandardDeck();
    }

    public Hero getFirstHero() {
        return firstHero;
    }

    public void setFirstHero(Hero firstHero) {
        this.firstHero = firstHero;
    }

    public Hero getSecondHero() {
        return secondHero;
    }

    public void setSecondHero(Hero secondHero) {
        this.secondHero = secondHero;
    }

    public void setActiveHero(Hero activeHero) {
        this.activeHero = activeHero;
    }

    public Hero getWinner() {
        return winner;
    }

    public Game deepCopy(Node root) {
        Game result = new Game();
        Hero firstHero = null;
        Hero secHero = null;
        if (this.firstHero == activeHero) {
            firstHero = this.firstHero.deepCopy(root);
            secondHero.generateAvailableMoves();
            secHero = this.secondHero.deepCopy(root);
        }
        if (this.secondHero == activeHero) {
            this.firstHero.generateAvailableMoves();
            firstHero = this.firstHero.deepCopy(root);
            secHero = this.secondHero.deepCopy(root);
        }
        ((AbstractHero) firstHero).setGame(result);
        ((AbstractHero) secHero).setGame(result);

        result.firstHero = firstHero;
        result.secondHero = secHero;

        if (this.activeHero == this.firstHero)
            result.activeHero = firstHero;
        else
            result.activeHero = secHero;

        result.firstHero.setAvailableMoves(this.firstHero.copyMovesTo(root, (AbstractHero) result.firstHero, this.firstHero.getAvailableMoves()));
        result.secondHero.setAvailableMoves(this.secondHero.copyMovesTo(root, (AbstractHero) result.secondHero, this.secondHero.getAvailableMoves()));

        result.firstHero.setGame(result);
        result.secondHero.setGame(result);

        return result;

    }
}