package strategy;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import game.Card;
import game.Move;

public abstract class Strategy {

    public abstract Move nextMove(int availableMana, int currentHealth, List<Card> availableCards);

    protected Optional<Card> highestCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getValue() <= availableMana).max(Comparator.<Card>naturalOrder());
    }

    protected Optional<Card> lowestCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getValue() <= availableMana).min(Comparator.<Card>naturalOrder());
    }
}
