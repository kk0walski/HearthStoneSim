package game;

import java.util.Random;
import java.util.logging.Logger;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());
    private Player activePlayer;

    private Player opponentPlayer;

    public Game(Player player1, Player player2) {
        activePlayer = StartingPlayerChooser.chooseBetween(player1, player2);
        if (activePlayer == player1) {
            opponentPlayer = player2;
        } else {
            opponentPlayer = player1;
        }
        activePlayer.drawStartingHand();
        opponentPlayer.drawStartingHand();
        opponentPlayer.drawCard(); // extra card to reduce disadvantage from being second to play
    }

    public void beginTurn() {
        activePlayer.addManaSlot();
        activePlayer.fillMana();
        activePlayer.drawCard();
        activePlayer.allowAttackForLiveMonsters();
        logger.info(activePlayer + " plays turn...");
    }

    void switchPlayer() {
        Player previouslyActivePlayer = activePlayer;
        activePlayer = opponentPlayer;
        opponentPlayer = previouslyActivePlayer;
    }

    public void endTurn() {
        logger.info(activePlayer + " ends turn.");
        switchPlayer();
    }

    public Player getWinner() {
        if (activePlayer.getHealth() < 1) {
            return opponentPlayer;
        } else if (opponentPlayer.getHealth() < 1) {
            return activePlayer;
        } else {
            return null;
        }
    }

    public void run() {
        while (true) {
            if (getWinner() == null) {
                beginTurn();
                while (activePlayer.canMakeMove()) {
                    activePlayer.makeMove(opponentPlayer);
                }
                endTurn();
            } else {
                logger.info(getWinner() + " wins the game!");
                break;
            }
        }
    }

    static class StartingPlayerChooser {

        private static Random random = new Random();

        public static Player chooseBetween(Player player1, Player player2) {
            return random.nextBoolean() ? player1 : player2;
        }

    }

}
