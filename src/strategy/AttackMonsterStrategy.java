package strategy;

import game.Action;
import game.Card;
import game.Player;

import java.util.List;
import java.util.Optional;

public class AttackMonsterStrategy extends Strategy {

    @Override
    public void nextMove(Player player, Player opponent) {
        if (player.canPlayCards()) {
            Optional<Card> highestToPlay = highestCard(player.getMana(), player.getHand());
            highestToPlay.ifPresent(card ->
                    player.makeAction(card, null, null, Action.PLAY_CARD)
            );
        } else if (player.canAnyMonsterAttack()) {
            Optional<Card> attacker = player.getActiveMonsters().stream()
                    .filter(card -> card.canAttack).findAny();
            List<Card> opponentMonster = opponent.getActiveMonsters();
            if (opponentMonster.isEmpty()) {
                attacker.ifPresent(
                        card -> player.makeAction(card, opponent, null, Action.ATTACK_HERO)
                );
            } else {
                attacker.ifPresent(
                        card -> player.makeAction(card, opponent, opponentMonster.get(0), Action.ATTACK_MONSTER)
                );
            }
        }
    }

}
