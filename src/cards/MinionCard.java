package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class MinionCard extends Card {
    @JsonIgnore
    private boolean firstRow;
    private int health;

    private int attackDamage;
    @JsonIgnore
    private boolean frozen;
    @JsonIgnore
    private boolean tank;

    public MinionCard(final int mana, final String description,
                      final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name, true);
    }

    public MinionCard(final MinionCard minion) {
        super(minion.getMana(), minion.getDescription(), minion.getColors(),
                minion.getName(), true);
        this.setMinionStats(minion.getHealth(), minion.getAttackDamage(),
                minion.isFrozen(), minion.isTank());
        this.setFirstRow(minion.isFirstRow());
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final boolean isFrozen() {
        return frozen;
    }

    public final void setFrozen(final boolean status) {
        this.frozen = status;
    }

    public final boolean isTank() {
        return tank;
    }

    public final void setTank(final boolean status) {
        this.tank = status;
    }

    /**This method should be used to set parameters for an object of class MinionCard.
     * Created to be used to "complete" the constructor method.*/
    public final void setMinionStats(final int minionHealth, final int minionAttackDamage,
                               final boolean frozenStatus, final boolean tankStatus) {
        this.setHealth(minionHealth);
        this.setAttackDamage(minionAttackDamage);
        this.setFrozen(frozenStatus);
        this.setTank(tankStatus);
    }

    public final boolean isFirstRow() {
        return firstRow;
    }

    public final void setFirstRow(final boolean status) {
        this.firstRow = status;
    }

    /**This method uses the ability of a minion. For standard minions this method does nothing.
     * It is created for legendary minions classes that extend MinionCard.
     * Parameter card is the target of the ability.*/
    public void useAbility(final MinionCard card) { }

    /**This method implements the attack of a minion to another minion.
     * It reduces health of target with attackDamage of attacker minion.*/
    public final void attack(final MinionCard card) {
        card.setHealth(card.getHealth() - this.getAttackDamage());
    }

    @Override
    public final Card copyCard() {
        return new MinionCard(this);
    }

    /**This method implements the attack of a minion to enemy hero.
     * It reduces health of enemy hero with attackDamage of attacker minion.*/
    public final void attackHero(final HeroCard hero) {
        hero.setHealth(hero.getHealth() - this.getAttackDamage());
    }
}

