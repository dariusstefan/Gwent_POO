package cards;

import java.util.ArrayList;

public final class HeartHound extends EnvironmentCard {

    public HeartHound(final int mana, final String description,
                      final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public MinionCard useAbility(final ArrayList<MinionCard> minions) {
        int maxHealth = 0;
        MinionCard maxHealthMinion = null;
        for (MinionCard minion : minions) {
            if (minion.getHealth() > maxHealth) {
                maxHealth = minion.getHealth();
                maxHealthMinion = minion;
            }
        }
        return maxHealthMinion;
    }

    @Override
    public EnvironmentCard copyEnvironment() {
        return new HeartHound(getMana(), getDescription(), getColors(), getName());
    }
}
