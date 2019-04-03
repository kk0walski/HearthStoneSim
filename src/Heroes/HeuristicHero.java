package Heroes;

import Moves.Move;

public interface HeuristicHero {

    void chooseHeuristicMove();

    int evaluate(Move toDo);
}
