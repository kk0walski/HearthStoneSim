package GUI;

import Cards.Card;
import Cards.Minion;
import Engine.Game;
import Heroes.HeuristicHero;
import Moves.*;

import java.util.List;
import java.util.Scanner;

public class BaseTextGui {
    Game game;
    Scanner keyboard;
    private final int selfHero = 1;
    private final int selfMinion = 2;
    private final int enemyHero = 1;
    private final int enemyMinion = 2;

    private final int putCard = 1;
    private final int attack = 2;
    private final int end = 3;

    public BaseTextGui(Game game) {
        super();
        this.game = game;
        keyboard = new Scanner(System.in);
    }

    public void baseInfo() {
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

    public void startGame() {
        System.out.println("Gra rozpoczeta");
        game.initializeAndStartPassiveWithAggressiveGame();
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

    public void makeMove() {
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
        String allCards = new String();
        for (Card c : cards) {
            if (c instanceof Minion) {
                String clazz = c.getClass().getSimpleName();
                int health = ((Minion) c).getHealth();
                int cost = c.getCost();
                int attack = ((Minion) c).getAttack();
                boolean isActive = ((Minion) c).isActive();
                allCards += clazz + " zycie " + health + " atak " + attack + " koszt " + cost + " aktywny " + isActive + " | ";
            }
        }
        System.out.println(allCards);
    }

    public static void main(String[] args) {
        BaseTextGui g = new BaseTextGui(new Game());
        g.play(100);
    }
}
