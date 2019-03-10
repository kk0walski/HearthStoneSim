package game;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;

public class Card implements Comparable<Card> {

    private final int maxHealth;
    private int health;
    private final int mana;
    private final int attack;
    private boolean hasBeenUsed;
    private boolean inHand;


    public static List<Card> list(Integer... values) {
        return stream(values).map(Card::new).collect(toCollection(ArrayList::new));
    }

    public Card(int mana){
        Random generator = new Random();
        this.mana = mana;
        this.maxHealth = generator.nextInt(10) + 1;
        this.health = maxHealth;
        this.attack = generator.nextInt(10) + 1;
        this.hasBeenUsed = false;
        this.inHand = false;
    }

    public Card(Card card){
        this.mana = card.getMana();
        this.maxHealth = card.getMaxHealth();
        this.health = card.getHealth();
        this.attack = card.getAttack();
        this.hasBeenUsed = card.getHasBeenUsed();
        this.inHand = card.getInHand();
    }

    public Card(int health, int mana, int attack) {
        this.maxHealth = health;
        this.health = health;
        this.mana = mana;
        this.attack = attack;
        this.hasBeenUsed = false;
        this.inHand = false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) { this.health = newHealth; }

    public int getMaxHealth() { return maxHealth; }

    public int getAttack() { return attack; }

    public int getMana() { return mana; }

    public boolean getInHand() { return inHand; }

    public void isInHand() { inHand = true; }

    public void notInHand() { inHand = false; }

    public boolean getHasBeenUsed() { return hasBeenUsed; }

    public void wasUsed() { hasBeenUsed = true; }

    public void notUsed() { hasBeenUsed = false; }

    @Override
    public String toString() {
        return "{ health:" + health + " attack: " + attack + " mana: " + mana + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return maxHealth == card.maxHealth &&
                attack == card.attack && mana == card.mana
                && hasBeenUsed == card.hasBeenUsed && inHand == card.inHand;
    }

    @Override
    public int hashCode() {
        int reasult = 0;
        reasult = 31 * reasult + maxHealth;
        reasult = 31 * reasult + mana;
        reasult = 31 * reasult + health;
        reasult = 31 * reasult + attack;
        reasult = 31 * reasult + (hasBeenUsed ? 1 : 0);
        reasult = 31 * reasult + (inHand ? 1 : 0);
        return reasult;
    }

    @Override
    public int compareTo(Card other) {
        return mana != other.mana ? mana - other.mana : attack != other.attack ? attack - other.attack : health - other.health;
    }
}