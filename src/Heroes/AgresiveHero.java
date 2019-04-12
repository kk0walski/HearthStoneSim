package Heroes;

import Cards.Card;
import Cards.Minion;
import Engine.Game;
import Moves.*;

import java.util.List;

public class AgresiveHero extends AbstractHero implements HeuristicHero {

    private static int cardsMul = 10;
    private static int attackHeroMul = 15;
    private static int attackMinionMul = 10;
    private static int endRoundMul = 1;

    public AgresiveHero(Game game, String name, List<Card> initialDeck, boolean isOponent, int initialHandSize) {
        super(game, name, initialDeck, isOponent, initialHandSize);
    }

    public AgresiveHero() {
        super(null, null, null, false, -1);
    }

    @Override
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
            printMoveInfo(toDo);
            performMove(toDo);
        }
    }

    @Override
    public int evaluate(Move toDo) {
        int boardSize = getBoard().size() == 0 ? 1 : getBoard().size();
        if (toDo instanceof AttackHero) {
            Card card = getBoard().get(toDo.getCardIndex());
            return attackHeroMul * ((Minion) card).getAttack() - (1 / boardSize) * cardsMul;
        }

        if (toDo instanceof AttackMinion) {
            Card card = getBoard().get(toDo.getCardIndex());
            return attackMinionMul * ((Minion) card).getAttack() - (1 / boardSize) * cardsMul;
        }

        if (toDo instanceof PutCard) {
            Card card = getHand().get(toDo.getCardIndex());
            return attackMinionMul * ((Minion) card).getAttack() - card.getCost() + (1 / boardSize) * cardsMul;
        }

        if (toDo instanceof EndRound) {
            if (!anyAttackAvaliable())
                return endRoundMul;
        }

        return -1;
    }

    boolean anyAttackAvaliable() {
        for (int i = 0; i < availableMoves.size(); i++) {
            if (availableMoves.get(i) instanceof AttackHero || availableMoves.get(i) instanceof AttackMinion)
                return true;
            if (availableMoves.get(i) instanceof PutCard) {
                return true;
            }
        }
        return false;
    }
}
