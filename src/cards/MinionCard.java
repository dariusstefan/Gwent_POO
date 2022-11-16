package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class MinionCard extends Card{
    private int health;

    private int attackDamage;
    @JsonIgnore
    private boolean frozenStatus;
    @JsonIgnore
    private boolean tankStatus;

    public MinionCard(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public MinionCard(MinionCard minion) {
        super(minion.getMana(), minion.getDescription(), minion.getColors(), minion.getName());
        this.setMinionStats(minion.getHealth(), minion.getAttackDamage(), minion.frozenStatus, minion.getTankStatus());
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
}

