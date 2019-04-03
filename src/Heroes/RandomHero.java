package Heroes;

import Cards.Card;
import Engine.Game;
import Moves.EndRound;
import Moves.Move;

import java.util.List;

public class RandomHero extends AbstractHero implements HeuristicHero {

    public RandomHero(Game game, String name, List<Card> initialDeck, boolean isOponent, int initialHandSize) {
        super(game, name, initialDeck, isOponent, initialHandSize);
    }

    public RandomHero() {
        super(null, null, null, false, -1);
    }


    public void chooseHeuristicMove() {
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

    @Override
    public int evaluate(Move toDo) {
        return (int) (Math.random() * 100);
    }
}
