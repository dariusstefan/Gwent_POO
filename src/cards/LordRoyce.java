package cards;

import java.util.ArrayList;

final public class LordRoyce extends HeroCard {

    public LordRoyce(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public HeroCard copyHero() {
        HeroCard copy = new LordRoyce(this.getMana(), this.getDescription(), this.getColors(), this.getName());
        copy.setHealth(this.getHealth());
        return copy;
    }

    @Override
    public void useAbility(ArrayList<MinionCard> cards) {
        MinionCard highestAttack = null;
        int maxAttack = 0;
        for (MinionCard card : cards) {
            if (card.getAttackDamage() > maxAttack) {
                maxAttack = card.getAttackDamage();
                highestAttack = card;
            }
        }
        if (highestAttack != null)
            highestAttack.setFrozen(true);
    }
}
