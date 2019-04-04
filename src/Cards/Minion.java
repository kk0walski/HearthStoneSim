package Cards;

import Heroes.Hero;


public class Minion implements Card {

    private int cost;
    private int attack;
    private int health;
    private int baseHealth;
    // at the beginning of each turn set this to true IF card is already active
    private Hero owner;
    private boolean active;

    public Minion() {
    }

    public Minion(int health, int mana, int attack, Hero owner) {
        this.health = health;
        this.baseHealth = health;
        this.cost = mana;
        this.attack = attack;
        this.active = false;
        this.owner = owner;
    }

    public Minion(int health, int mana, int attack) {
        this.health = health;
        this.baseHealth = health;
        this.cost = mana;
        this.attack = attack;
        this.active = false;
    }

    @Override
    public Card deepCopy() {
        Minion copy = null;

        try {
            copy = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        copy.setCost(cost);
        copy.setAttack(attack);
        copy.setHealth(health);
        copy.setOwner(owner);
        copy.setBaseHealth(baseHealth);
        copy.setActive(active);
        return copy;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public Hero getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Hero owner) {
        this.owner = owner;
    }


    @Override
    public void doAction(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion) {
        // blank default action for minion
    }

    public void attack(Hero enemyHero) {
        enemyHero.receiveDamage(attack);
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
        notifyHeroIfDeadMinion();
    }

    public void notifyHeroIfDeadMinion() {
        if (isDead()) {
            owner.deadMinionNotification(this);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void revertAttack(Hero enemyHero) {
        enemyHero.revertDamage(attack);
    }

    public void revertDamage(int damage) {
        revertHeroNotificationIfDeadMinion();
        health += damage;
    }

    public void revertHeroNotificationIfDeadMinion() {
        if (isDead()) {
            owner.revertDeadMinionNotification(this);
        }
    }

    public void attack(Minion enemyMinion) {
        enemyMinion.receiveDamage(attack);
    }

    public void revertAttack(Minion enemyMinion) {
        enemyMinion.revertDamage(attack);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void deactivate() {
        active = false;
    }

    public void activate() {
        active = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Minion minion = (Minion) o;

        if (cost != minion.cost) return false;
        if (attack != minion.attack) return false;
        if (health != minion.health) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = cost;
        result = 31 * result + attack;
        result = 31 * result + health;
        return result;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }
}
