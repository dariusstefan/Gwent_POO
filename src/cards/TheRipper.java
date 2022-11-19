package cards;

import java.util.ArrayList;

public final class TheRipper extends MinionCard {

    public TheRipper(final int mana, final String description,
                     final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(final MinionCard card) {
        card.setAttackDamage(card.getAttackDamage() - 2);
        if (card.getAttackDamage() < 0) {
            card.setAttackDamage(0);
        }
    }
}
