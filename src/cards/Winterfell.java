package cards;

import java.util.ArrayList;

public final class Winterfell extends EnvironmentCard {

    public Winterfell(final int mana, final String description,
                      final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public MinionCard useAbility(final ArrayList<MinionCard> minions) {
        for (MinionCard minion : minions) {
            minion.setFrozen(true);
        }
        return null;
    }

    @Override
    public EnvironmentCard copyEnvironment() {
        return new Winterfell(getMana(), getDescription(), getColors(), getName());
    }
}
