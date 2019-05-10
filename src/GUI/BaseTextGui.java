package GUI;

import Cards.Card;
import Cards.Minion;
import Engine.Game;
import Heroes.HeuristicHero;
import Heroes.MctsHero;
import Moves.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BaseTextGui {
    private Game game;
    private Scanner keyboard;
    private final int selfHero = 1;
    private final int selfMinion = 2;
    private final int enemyHero = 1;
    private final int enemyMinion = 2;

    private final int putCard = 1;
    private final int attack = 2;
    private final int end = 3;

    BaseTextGui(Game game) {
        super();
        this.game = game;
        keyboard = new Scanner(System.in);
    }

    private static void longBunchRandom(StringBuilder stringBuilder) {
        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations;
        /*
          move: 1s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 1;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 2s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 2s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 20;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 4s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 4s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 20;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 6s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 6;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 6s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 6;
        totalTimeForMctsMove = 30;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 8s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 8;
        totalTimeForMctsMove = 20;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 8s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 8;
        totalTimeForMctsMove = 30;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 10s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 10;
        totalTimeForMctsMove = 20;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 4s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 10;
        totalTimeForMctsMove = 30;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
    }

    private static void longBunchAggresive(StringBuilder stringBuilder) {
        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations;
        /*
          move: 1s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 1;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 2s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 2s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 20;
        iterations = 20;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 4s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);


        /*
          move: 4s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 20;
        iterations = 20;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 6s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 6;
        totalTimeForMctsMove = 20;
        iterations = 20;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);


        /*
          move: 6s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 6;
        totalTimeForMctsMove = 30;
        iterations = 20;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 8s
          total moves: 60s
          iterations: 5
         */

        timeForMctsMove = 8;
        totalTimeForMctsMove = 60;
        iterations = 20;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
    }

    private static void longBunchPassive(StringBuilder stringBuilder) {
        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations;
        /*
          move: 1s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 1;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 2s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 2s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 20;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 4s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 4s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 20;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);


        /*
          move: 6s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 6;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 6s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 6;
        totalTimeForMctsMove = 20;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 8s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 8;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 8s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 8;
        totalTimeForMctsMove = 20;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 10s
          total moves: 10s
          iterations: 5
         */

        timeForMctsMove = 10;
        totalTimeForMctsMove = 10;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /*
          move: 10s
          total moves: 20s
          iterations: 5
         */

        timeForMctsMove = 10;
        totalTimeForMctsMove = 20;
        iterations = 20;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
    }

    private static void shortBunch(StringBuilder stringBuilder) {
        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations;

        /*
          move: 1s
          total moves: 2s
          iterations: 5
         */

        timeForMctsMove = 1;
        totalTimeForMctsMove = 2;
        iterations = 20;

        stringBuilder.append("single_move_time;round_time;mcts_win;maximal_number_of_playouts;max_tree_depth\n");
        try {
            Files.write(Paths.get("/home/karol/IdeaProjects/HearthStoneSim/Dane/random.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        stringBuilder.setLength(0);

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        stringBuilder.append("single_move_time;round_time;mcts_win;maximal_number_of_playouts;max_tree_depth\n");
        try {
            Files.write(Paths.get("/home/karol/IdeaProjects/HearthStoneSim/Dane/aggresive.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        stringBuilder.setLength(0);

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        stringBuilder.append("single_move_time;round_time;mcts_win;maximal_number_of_playouts;max_tree_depth\n");
        try {
            Files.write(Paths.get("/home/karol/IdeaProjects/HearthStoneSim/Dane/passive.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        stringBuilder.setLength(0);

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
    }

    private static void performTestMctsWithRandom(int timeForMctsMove, int totalTimeForMctsMove, int iterations, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        for (int i = 0; i < iterations; i++) {
            BaseTextGui g = new BaseTextGui(new Game());
            int numberOfWinningHero = g.mctsWithRandomPlay(timeForMctsMove, totalTimeForMctsMove);
            int maxNumberOfPlayouts = Collections.max(((MctsHero) g.game.getFirstHero()).getNumbersOfPlayouts());
            int maxTreeDepth = Collections.max(((MctsHero) g.game.getFirstHero()).getMaximumTreeDepths());
            stringBuilder.append(timeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(totalTimeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(numberOfWinningHero);
            stringBuilder.append(";");
            stringBuilder.append(maxNumberOfPlayouts);
            stringBuilder.append(";");
            stringBuilder.append(maxTreeDepth);
            stringBuilder.append("\n");
            try {
                Files.write(Paths.get("/home/karol/IdeaProjects/HearthStoneSim/Dane/random.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
                System.out.println(e.toString());
            }
            stringBuilder.setLength(0);
        }
    }

    private static void performTestMctsWithAggresive(int timeForMctsMove, int totalTimeForMctsMove, int iterations, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);

        for (int i = 0; i < iterations; i++) {
            BaseTextGui g = new BaseTextGui(new Game());
            int numberOfWinningHero = g.mctsWithAggresivePlay(timeForMctsMove, totalTimeForMctsMove);
            int maxNumberOfPlayouts = Collections.max(((MctsHero) g.game.getFirstHero()).getNumbersOfPlayouts());
            int maxTreeDepth = Collections.max(((MctsHero) g.game.getFirstHero()).getMaximumTreeDepths());
            stringBuilder.append(timeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(totalTimeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(numberOfWinningHero);
            stringBuilder.append(";");
            stringBuilder.append(maxNumberOfPlayouts);
            stringBuilder.append(";");
            stringBuilder.append(maxTreeDepth);
            stringBuilder.append("\n");
            try {
                Files.write(Paths.get("/home/karol/IdeaProjects/HearthStoneSim/Dane/aggresive.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            stringBuilder.setLength(0);
        }
    }

    private static void performTestMctsWithPassive(int timeForMctsMove, int totalTimeForMctsMove, int iterations, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);

        for (int i = 0; i < iterations; i++) {
            BaseTextGui g = new BaseTextGui(new Game());
            int numberOfWinningHero = g.mctsWithPassivePlay(timeForMctsMove, totalTimeForMctsMove);
            int maxNumberOfPlayouts = Collections.max(((MctsHero) g.game.getFirstHero()).getNumbersOfPlayouts());
            int maxTreeDepth = Collections.max(((MctsHero) g.game.getFirstHero()).getMaximumTreeDepths());
            stringBuilder.append(timeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(totalTimeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(numberOfWinningHero);
            stringBuilder.append(";");
            stringBuilder.append(maxNumberOfPlayouts);
            stringBuilder.append(";");
            stringBuilder.append(maxTreeDepth);
            stringBuilder.append("\n");
            try {
                Files.write(Paths.get("/home/karol/IdeaProjects/HearthStoneSim/Dane/passive.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            stringBuilder.setLength(0);
        }
    }

    private void baseInfo() {
        System.out.println("\nAktywny gracz: " + game.getActiveHero().getName() + "\n");
        System.out.println("Pierwszy gracz: " + game.getFirstHero().getName());
        System.out.println("HP: " + game.getFirstHero().getHealth());
        System.out.println("Mana: " + game.getFirstHero().getMana());
        System.out.println("Liczba kart w decku: " + game.getFirstHero().getDeck().size());
        System.out.println("Kara za pusty deck: " + game.getFirstHero().getPunishForEmptyDeck());
        System.out.println("Karty w ręce: ");
        printCards(game.getFirstHero().getHand());
        System.out.println("Karty na stole: ");
        printCards(game.getFirstHero().getBoard());
        System.out.println("------------------------------");

        System.out.println("Drugi gracz: " + game.getSecondHero().getName());
        System.out.println("HP: " + game.getSecondHero().getHealth());
        System.out.println("Mana: " + game.getSecondHero().getMana());
        System.out.println("Liczba kart w decku: " + game.getSecondHero().getDeck().size());
        System.out.println("Kara za pusty deck: " + game.getSecondHero().getPunishForEmptyDeck());
        System.out.println("Karty w ręce: ");
        printCards(game.getSecondHero().getHand());
        System.out.println("Karty na stole: ");
        printCards(game.getSecondHero().getBoard());
        System.out.println("------------------------------");
    }

    public void startGameMctsWithRandom() {
        System.out.println("Gra rozpoczeta");
        game.initializeAndStartHumanWithMctsGame();
        // game.initializeAndStartRandomWithMctsGame(timeForMctsMove, totalTimeForMctsMoves);
        //prepareCards();
        // System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    private void startGameMctsWithRandom(int timeForMctsMove, int totalTimeForMctsMoves) {
        System.out.println("Gra rozpoczeta");
        // game.initializeAndStartHumanWithMctsGame();
        game.initializeAndStartRandomWithMctsGame(timeForMctsMove, totalTimeForMctsMoves);
        // prepareCards();
        // System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    private void startGameMctsWithAggresive(int timeForMctsMove, int totalTimeForMctsMoves) {
        System.out.println("Gra rozpoczeta");
        // game.initializeAndStartHumanWithMctsGame();
        game.initializeAndStartAggresiveWithMctsGame(timeForMctsMove, totalTimeForMctsMoves);
        // prepareCards();
        // System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    private void startGameMctsWithPassive(int timeForMctsMove, int totalTimeForMctsMoves) {
        System.out.println("Gra rozpoczeta");
        // game.initializeAndStartHumanWithMctsGame();
        game.initializeAndStartPassiveWithMctsGame(timeForMctsMove, totalTimeForMctsMoves);
        // prepareCards();
        // System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    private void startGame() {
        System.out.println("Gra rozpoczeta");
        game.initializeAndStartAggresiveWithMctsGame(100, 100);
        System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    public void play(int iterations) {
        int winPlayer1 = 0;
        int winPlayer2 = 0;
        for (int i = 0; i < iterations; i++) {
            startGame();
            while ((game.getWinner() == null)) {
                makeMove();
                if (game.getWinner() != null)
                    break;
                baseInfo();
            }
            //System.out.println("Playouts: " + ((MctsHero)game.getFirstHero()).getNumbersOfPlayouts());
            //System.out.println("Tree depths: " + ((MctsHero)game.getFirstHero()).getMaximumTreeDepths());
            if (game.getWinner() == game.getFirstHero()) { //celowe por�wnanie referencji
                System.out.println("Brawo gracz 1 wygrywa");
                winPlayer1++;
            } else {
                System.out.println("Brawo gracz 2 wygrywa");
                winPlayer2++;
            }
        }
        System.out.println("Gracz " + game.getFirstHero().getName() + " wygrał: " + winPlayer1 + " razy.");
        System.out.println("Gracz " + game.getSecondHero().getName() + " wygrał: " + winPlayer2 + " razy.");
    }

    private int mctsWithRandomPlay(int timeForMctsMove, int totalTimeForMctsMoves) {
        startGameMctsWithRandom(timeForMctsMove, totalTimeForMctsMoves);
        while ((game.getWinner() == null)) {
            makeMove();
            if (game.getWinner() != null)
                break;
            baseInfo();
        }

        if (game.getWinner() == game.getFirstHero()) { // mcts is first
            System.out.println("Brawo gracz 1 wygrywa");
            return 1;
        } else {
            System.out.println("Brawo gracz 2 wygrywa");
            return 0;
        }
    }

    private int mctsWithAggresivePlay(int timeForMctsMove, int totalTimeForMctsMoves) {
        startGameMctsWithAggresive(timeForMctsMove, totalTimeForMctsMoves);
        while ((game.getWinner() == null)) {
            makeMove();
            if (game.getWinner() != null)
                break;
            baseInfo();
        }

        if (game.getWinner() == game.getFirstHero()) { // mcts is first
            System.out.println("Brawo gracz 1 wygrywa");
            return 1;
        } else {
            System.out.println("Brawo gracz 2 wygrywa");
            return 0;
        }
    }

    private int mctsWithPassivePlay(int timeForMctsMove, int totalTimeForMctsMoves) {
        startGameMctsWithPassive(timeForMctsMove, totalTimeForMctsMoves);
        while ((game.getWinner() == null)) {
            makeMove();
            if (game.getWinner() != null)
                break;
            baseInfo();
        }

        if (game.getWinner() == game.getFirstHero()) { // mcts is first
            System.out.println("Brawo gracz 1 wygrywa");
            return 1;
        } else {
            System.out.println("Brawo gracz 2 wygrywa");
            return 0;
        }
    }

    private void makeMove() {
        if (game.getActiveHero() instanceof HeuristicHero) {
            ((HeuristicHero) game.getActiveHero()).chooseHeuristicMove();
            return;
        }
        System.out.println("1.Poloz karte 2.Uzyj karty ze stolu 3.Skoncz ture ");
        int move = keyboard.nextInt();
        Move m = prepareMove(move);
        boolean isMoveDone = false;
        if (m != null)
            isMoveDone = game.getActiveHero().performMove(m);
        if (isMoveDone) {
            System.out.println();
        } else {
            System.out.println("Ruch nie mozliwy do wykonania");
        }
    }

    private Move prepareMove(int move) {
        switch (move) {
            case putCard:
                return preparePutCard();
            case attack:
                return prepareAttack();
            case end:
                return prepareEnd();
        }
        return null;
    }

    private EndRound prepareEnd() {
        return new EndRound(game.getActiveHero());
    }

    private Move prepareAttack() {
        System.out.println("Wybierz karte");
        int minion = keyboard.nextInt();
        if (minion >= game.getActiveHero().getBoard().size())
            return null;
        else {
            System.out.println("Wybierz cel");
            System.out.println("1.Heros 2.Minion");
            int target = keyboard.nextInt();
            switch (target) {
                case enemyHero:
                    return new AttackHero(minion, game.getActiveHero().getBoard(), game.getEnemyOf(game.getActiveHero()));
                case enemyMinion:
                    System.out.println("Wybierz miniona");
                    int targetMinion = keyboard.nextInt();
                    if (minion < game.getEnemyOf(game.getActiveHero()).getBoard().size())
                        return new AttackMinion(minion, game.getActiveHero().getBoard(),
                                game.getEnemyOf(game.getActiveHero()).getBoard().get(targetMinion));
                    else
                        return null;
            }
            return null;
        }
    }

    private PutCard preparePutCard() {
        System.out.println("Wybierz karte");
        int minion = keyboard.nextInt();
        if (game.getActiveHero().getHand().size() <= minion)
            return null;

        if (game.getActiveHero().getHand().get(minion) instanceof Minion) {
            return new PutCard(minion, game.getActiveHero(), game.getEnemyOf(game.getActiveHero()));
        } else {
            return null;
        }
    }

    private void printCards(List<Card> cards) {
        StringBuilder allCards = new StringBuilder();
        for (Card c : cards) {
            if (c instanceof Minion) {
                String klasa = c.getClass().getSimpleName();
                int health = ((Minion) c).getHealth();
                int cost = c.getCost();
                int attack = ((Minion) c).getAttack();
                boolean isActive = ((Minion) c).isActive();
                allCards.append(klasa).append(" zycie ").append(health).append(" atak ").append(attack).append(" koszt ").append(cost).append(" aktywny ").append(isActive).append(" | ");
            }
        }
        System.out.println(allCards);
    }

    public static void main(String[] args) {


        //int timeForMctsMove;
        //int totalTimeForMctsMove;
        //int iterations;

        StringBuilder stringBuilder = new StringBuilder();

        /*
         Mcts vs Random
         */

        shortBunch(stringBuilder);

        longBunchRandom(stringBuilder);

        /*
         Mcts vs Aggresive
         */

        longBunchAggresive(stringBuilder);

        /*
         Mcts vs Passive
         */
        longBunchPassive(stringBuilder);

        //Game game = new Game();
        //BaseTextGui gui = new BaseTextGui(game);
        //performTestMctsWithAggresive(20, 50, 30, stringBuilder);
    }
}
