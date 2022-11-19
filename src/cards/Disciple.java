package cards;

import java.util.ArrayList;

public final class Disciple extends MinionCard {

    public Disciple(final int mana, final String description,
                    final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(final MinionCard card) {
        card.setHealth(card.getHealth() + 2);
    }
}
