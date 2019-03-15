package strategy;

import game.Action;
import game.Card;
import game.Player;

import java.util.Optional;

/**
 * This strategy focuses on doing damage to the enemy hero. <br>
 * Whenever possible plays new cards and attacks with ones ready to attack. <br>
 * This strategy does not consider attack/health/mana-cost ratio of cards,
 * assumes that the more expensive (mana cost) the better given card is. <br>
 */
public class AttackHeroStrategy extends Strategy {

    @Override
    public void nextMove(Player player, Player opponent) {
        if (player.canPlayCards()) {
            Optional<Card> highestToPlay = highestCard(player.getMana(), player.getHand());
            highestToPlay.ifPresent(card ->
                    player.makeAction(card, null, null, Action.PLAY_CARD)
            );
        } else if (player.canAnyMonsterAttack()) {
            Optional<Card> nextAttacker = player.getActiveMonsters().stream().filter(card -> card.canAttack).findAny();
            nextAttacker.ifPresent(card -> player.makeAction(card, opponent, null, Action.ATTACK_HERO));
        }
    }

}
