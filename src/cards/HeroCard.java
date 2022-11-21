package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public abstract class HeroCard extends Card {
    public static final int INITIAL_HEALTH_HERO = 30;
    private int health;

    public HeroCard(final int mana, final String description,
                    final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name, false);
    }

    /**Returns a deep-copied object of class HeroCard.*/
    public abstract HeroCard copyHero();

    /**This method initialize health for an object of class HeroCard.*/
    public final void initHealth() {
        this.health = INITIAL_HEALTH_HERO;
    }

    /**This method returns health for an object of class HeroCard.*/
    public final int getHealth() {
        return health;
    }

    /**This method sets health for an object of class HeroCard.*/
    public final void setHealth(final int health) {
        this.health = health;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    @JsonIgnore
    public final boolean isDead() {
        return this.health == 0;
    }

    /**This method should implement ability of a hero card.*/
    public abstract void useAbility(ArrayList<MinionCard> minions);

    /**This method implements copyCard abstract of parent class Card.*/
    public Card copyCard() {
        return copyHero();
    }
}
