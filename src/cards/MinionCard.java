package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class MinionCard extends Card{
    @JsonIgnore
    private boolean firstRow;
    private int health;

    private int attackDamage;
    @JsonIgnore
    private boolean frozen;
    @JsonIgnore
    private boolean tank;

    public MinionCard(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name, true);
    }

    public MinionCard(MinionCard minion) {
        super(minion.getMana(), minion.getDescription(), minion.getColors(), minion.getName(), true);
        this.setMinionStats(minion.getHealth(), minion.getAttackDamage(), minion.isFrozen(), minion.isTank());
        this.setFirstRow(minion.isFirstRow());
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

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean status) {
        this.frozen = status;
    }

    public boolean isTank() {
        return tank;
    }

    public void setTank(boolean status) {
        this.tank = status;
    }

    public void setMinionStats(int health, int attackDamage, boolean frozen, boolean tank) {
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        this.setFrozen(frozen);
        this.setTank(tank);
    }

    public boolean isFirstRow() {
        return firstRow;
    }

    public void setFirstRow(boolean status) {
        this.firstRow = status;
    }

    public void useAbility(MinionCard card) {}

    public void attack(MinionCard card) {
        card.setHealth(card.getHealth() - this.getAttackDamage());
    }

    @Override
    public Card copyCard() {
        return new MinionCard(this);
    }

    public void attackHero(HeroCard hero) {
        hero.setHealth(hero.getHealth() - this.getAttackDamage());
    }
}

