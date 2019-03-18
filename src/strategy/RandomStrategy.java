package strategy;

import game.Action;
import game.Card;
import game.Player;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RandomStrategy extends Strategy {

    private static Random random = new Random();

    @Override
    public void nextMove(Player player, Player opponent) {
        if (player.canPlayCards()) {
            Optional<Card> highestToPlay = highestCard(player.getMana(), player.getHand());
            highestToPlay.ifPresent(card ->
                    player.makeAction(card, null, null, Action.PLAY_CARD)
            );
        } else if (player.canAnyMonsterAttack()) {
            Card attacker = player.getActiveMonsters().stream()
                    .filter(card -> card.canAttack).findAny().orElse(null);
            List<Card> opponentMonster = opponent.getActiveMonsters();
            if (opponentMonster.isEmpty()) {
                player.makeAction(attacker, opponent, null, Action.ATTACK_HERO);
            } else {
                // opponent has monsters that can be attacked, but still random may decide to attack hero
                if (random.nextBoolean()) {
                    player.makeAction(attacker, opponent, opponentMonster.get(0), Action.ATTACK_MONSTER);
                } else {
                    player.makeAction(attacker, opponent, null, Action.ATTACK_HERO);
                }

            }
        }
    }
}
