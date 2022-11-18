package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public abstract class HeroCard extends Card {
    private int health;

    public HeroCard(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name, false);
    }
    public abstract HeroCard copyHero();

    public void initHealth() {
        this.health = 30;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    @JsonIgnore
    public boolean isDead() {
        return this.health == 0;
    }

    abstract void useAbility();

    public Card copyCard() {
        return copyHero();
    }
}
