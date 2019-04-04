package Heroes;

import Cards.Card;
import Cards.Minion;
import Engine.Game;
import Moves.*;

import java.util.List;

public class PassiveHero extends AbstractHero implements HeuristicHero {

    public PassiveHero(Game game, String name, List<Card> initialDeck, boolean isOponent, int initialHandSize) {
        super(game, name, initialDeck, isOponent, initialHandSize);
    }

    public PassiveHero() {
        super(null, null, null, false, -1);
    }

    @Override
    public void chooseHeuristicMove() {
        Move toDo = null;
        while (!(toDo instanceof EndRound)) {
            int i = 0;
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
            printMoveInfo(toDo);
            performMove(toDo);
        }
    }

    @Override
    public int evaluate(Move toDo) {
        if (toDo instanceof PutCard) {
            if (toDo.getCard() instanceof Minion) {
                return 3 * ((Minion) ((PutCard) toDo).getCard()).getAttack() +
                        ((Minion) ((PutCard) toDo).getCard()).getHealth();
            } else {
                return 4;
            }
        } else if (toDo instanceof AttackMinion) {
            return 3 * ((Minion) ((AttackMinion) toDo).getCard()).getAttack();
        } else if (toDo instanceof AttackHero) {
            return ((Minion) ((AttackHero) toDo).getCard()).getAttack();
        } else {
            return 1;
        }
    }
}
