package cards;

import java.util.ArrayList;

public final class Miraj extends MinionCard {

    public Miraj(final int mana, final String description,
                 final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(final MinionCard card) {
        int health = card.getHealth();
        card.setHealth(this.getHealth());
        this.setHealth(health);
    }
}
