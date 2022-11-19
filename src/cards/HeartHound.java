package cards;

import java.util.ArrayList;

final public class HeartHound extends EnvironmentCard {

    public HeartHound(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public MinionCard useAbility(ArrayList<MinionCard> minions) {
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
