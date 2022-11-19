package cards;

import java.util.ArrayList;

public final class TheCursedOne extends MinionCard {

    public TheCursedOne(final int mana, final String description,
                        final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(final MinionCard card) {
        int attackDamage = card.getAttackDamage();
        card.setAttackDamage(card.getHealth());
        card.setHealth(attackDamage);
    }
}
