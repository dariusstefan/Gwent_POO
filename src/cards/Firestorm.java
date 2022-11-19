package cards;

import java.util.ArrayList;

public final class Firestorm extends EnvironmentCard {

    public Firestorm(final int mana, final String description,
                     final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public MinionCard useAbility(final ArrayList<MinionCard> minions) {
        for (MinionCard minion : minions) {
            minion.setHealth(minion.getHealth() - 1);
        }
        minions.removeIf(c -> c.getHealth() == 0);
        return null;
    }

    @Override
    public EnvironmentCard copyEnvironment() {
        return new Firestorm(getMana(), getDescription(), getColors(), getName());
    }
}
