package cards;

import java.util.ArrayList;

public class Firestorm extends EnvironmentCard {

    public Firestorm(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public MinionCard useAbility(ArrayList<MinionCard> minions) {
        for (MinionCard minion : minions) {
            minion.setHealth(minion.getHealth() - 1);
        }
        minions.removeIf(c -> c.getHealth() == 0);
        return null;
    }
}
