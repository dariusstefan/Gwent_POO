package cards;

import java.util.ArrayList;

final public class TheCursedOne extends MinionCard {

    public TheCursedOne(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(MinionCard card) {
        int attackDamage = card.getAttackDamage();
        card.setAttackDamage(card.getHealth());
        card.setHealth(attackDamage);
    }
}
