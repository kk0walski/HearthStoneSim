package Heroes;

import Cards.Card;
import Cards.Minion;
import Engine.Game;
import MCTS.MctsAlgorithm;
import Moves.*;
import MCTS.Node;

import java.util.ArrayList;
import java.util.List;

public class MctsHero extends AbstractHero implements HeuristicHero {

    private List<Integer> numbersOfPlayouts = new ArrayList<>();
    private List<Integer> maximumTreeDepths = new ArrayList<>();
    private int timeForMctsMove;
    private int totalTimeForMctsMoves;

    public MctsHero(Game game, String name, List<Card> initialDeck, boolean isOponent, int initialHandSize) {
        super(game, name, initialDeck, isOponent, initialHandSize);
    }

    public MctsHero() {
        super(null, null, null, false, -1);
    }

    public MctsHero(Game game, String name, List<Card> initialDeck,
                    boolean isOponent, int initialHandSize,
                    int timeForMctsMove, int totalTimeForMctsMoves) {
        super(game, name, initialDeck, isOponent, initialHandSize);
        this.timeForMctsMove = timeForMctsMove;
        this.totalTimeForMctsMoves = totalTimeForMctsMoves;
    }

    @Override
    public void chooseHeuristicMove() {
        long start = System.currentTimeMillis();
        long end = start;
        Move bestMove = null;
        while (end - start <= totalTimeForMctsMoves * 1000) {
            game.checkForGameEnd();
            if (game.isGameOver() || bestMove instanceof EndRound) {
                return;
            }
            MctsAlgorithm mctsAlgorithm = new MctsAlgorithm(new Node(game), timeForMctsMove, this);
            bestMove = mctsAlgorithm.run();
            numbersOfPlayouts.add(mctsAlgorithm.getNumberOfPlayouts());
            maximumTreeDepths.add(mctsAlgorithm.getMaximumTreeDepth());
            printMoveInfo(bestMove);
            boolean performed = performMove(bestMove);
            if (!performed) {
                System.out.println("Nie wykonany bo był błędny! LOL");
            }
            end = System.currentTimeMillis();
        }
        game.checkForGameEnd();
        if (game.isGameOver()) {
            System.out.println("Gierka skonczona");
            return;
        }
    }

    @Override
    public int evaluate(Move toDo) {
        return 0;
    }

    public List<Integer> getNumbersOfPlayouts() {
        return numbersOfPlayouts;
    }

    public List<Integer> getMaximumTreeDepths() {
        return maximumTreeDepths;
    }
}
