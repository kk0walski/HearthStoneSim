package Cards;

import Heroes.Hero;

public interface Card {

    Card deepCopy();

    void doAction(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion);

    int getCost();

    Hero getOwner();

    void setOwner(Hero owner);
}
