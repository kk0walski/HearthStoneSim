package strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import game.Move;
import game.Action;
import game.Card;

public class ConsoleInputStrategy extends Strategy {

    private static final Logger logger = Logger.getLogger(ConsoleInputStrategy.class.getName());

    @Override
    public Move nextMove(int availableMana, int currentHealth, List<Card> availableCards) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
            Integer index = -1;
            Action action = Action.DAMAGE;
            while (index < 0 || index >= availableCards.size() || availableCards.get(index).getMana() > availableMana) {
                try {
                    String input = br.readLine();
                    if (input.endsWith("h")) {
                        action = Action.HEALING;
                        input = input.replace("h", "");
                    }
                    index = Integer.decode(input);
                } catch (NumberFormatException e) {
                    logger.warning("Invalid input: " + e.getMessage());
                }
            }
            return new Move(Optional.of(new Card(availableCards.get(index))), action);
        } catch (IOException e) {
            logger.severe("Could not read console input: " + e.getMessage());
            e.printStackTrace();
        }
        return new Move(Optional.empty(), null);
    }


}
