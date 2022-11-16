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

    public void setMinionStats(int health, int attackDamage, boolean frozen, boolean tank) {
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        this.setFrozen(frozen);
        this.setTankStatus(tank);
    }

    public void useAbility() {}

    @Override
    public String toString() {
        return "MinionCard{" +
                "health=" + health +
                ", name='" + getName() + '\'' +
                '}';
    }
}

