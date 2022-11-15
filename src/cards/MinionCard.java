package cards;

import java.util.ArrayList;

public class MinionCard extends Card{
    private int health;

    private int attackDamage;

    private boolean frozenStatus;

    private boolean tankStatus;

    public MinionCard(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean getFrozenStatus() {
        return frozenStatus;
    }

    public void setFrozen(boolean status) {
        this.frozenStatus = status;
    }

    public boolean getTankStatus() {
        return tankStatus;
    }

    public void setTankStatus(boolean tankStatus) {
        this.tankStatus = tankStatus;
    }

    public void useAbility() {}
}

class TheRipper extends MinionCard{

    public TheRipper(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("WEAKKNEES!\n");
    }
}

class Miraj extends MinionCard{

    public Miraj(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("SKYJACK!\n");
    }
}

class TheCursedOne extends MinionCard{

    public TheCursedOne(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("SHAPESHIFT!\n");
    }
}

class Disciple extends MinionCard{

    public Disciple(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("GODSPLAN!\n");
    }
}
