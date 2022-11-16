package cards;

import java.util.ArrayList;

public abstract class HeroCard extends Card {
    private int health;

    public HeroCard(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

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

    public boolean isDead() {
        return this.health == 0;
    }

    abstract void useAbility();

    @Override
    public String toString() {
        return "HeroCard{" +
                "health=" + health +
                ", manaCost=" + getManaCost() +
                ", description='" + getDescription() + '\'' +
                ", colors=" + getColors() +
                ", name='" + getName() + '\'' +
                '}';
    }
}

