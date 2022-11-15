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
}

class LordRoyce extends HeroCard {

    public LordRoyce(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("SUBZERO!\n");
    }
}

class EmpressaThorina extends HeroCard {

    public EmpressaThorina(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("LOWBLOW!\n");
    }
}

class KingMudface extends HeroCard {

    public KingMudface(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("EARTHBORN!\n");
    }
}

class GeneralKocioraw extends HeroCard {

    public GeneralKocioraw(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("BLOODTHIRST!\n");
    }
}
