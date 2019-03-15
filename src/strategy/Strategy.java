package strategy;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import game.Card;
import game.Move;
import game.Player;

public abstract class Strategy {

    public abstract void nextMove(Player player, Player opponent);

    protected Optional<Card> highestCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream()
                .filter(card -> card.getMana() <= availableMana)
                .max(Comparator.comparingInt(Card::getMana));
    }

    protected Optional<Card> lowestCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream()
                .filter(card -> card.getMana() <= availableMana)
                .min(Comparator.comparingInt(Card::getMana));
    }
}
