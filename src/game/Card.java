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

    public Card(Card card) {
        this.mana = card.getMana();
        this.maxHealth = card.getMaxHealth();
        this.health = card.getHealth();
        this.attack = card.getAttack();
    }

    public Card(int health, int mana, int attack) {
        this.maxHealth = health;
        this.health = health;
        this.mana = mana;
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public int getMana() {
        return mana;
    }

    public void damage(int attack) {
        this.health -= attack;
    }

    public boolean isDead() {
        return health <= 0;
    }

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
                attack == card.attack && mana == card.mana;
    }

    @Override
    public int hashCode() {
        int reasult = 0;
        reasult = 31 * reasult + maxHealth;
        reasult = 31 * reasult + mana;
        reasult = 31 * reasult + health;
        reasult = 31 * reasult + attack;
        return reasult;
    }

    @Override
    public int compareTo(Card other) {
        return mana != other.mana ? mana - other.mana : attack != other.attack ? attack - other.attack : health - other.health;
    }
}