package game;

import java.util.Optional;

public class Move {

    private final Optional<Card> card;
    private final Action action;
    private final Optional<Card> enemyCard;

    public Move(Optional<Card> card, Action action, Optional<Card> enemyCard) {
        this.card = card;
        this.action = action;
        this.enemyCard = enemyCard;
    }

    public Move(Optional<Card> card, Action action) {
        this(card, action, Optional.empty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (action != move.action) return false;
        if (card != null ? !card.equals(move.card) : move.card != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = card != null ? card.hashCode() : 0;
        result = 31 * result + (enemyCard != null ? card.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    public Optional<Card> getCard() {
        return card;
    }

    public Optional<Card> getEnemyCard() { return enemyCard; }

    @Override
    public String toString() {
        return "Move{" +
                "card=" + card +
                ", action=" + action +
                ", enemy card index=" + enemyCard +
                '}';
    }

    public Action getAction() {
        return action;
    }
}
