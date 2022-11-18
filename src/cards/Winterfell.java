package cards;

import java.util.ArrayList;

public class Winterfell extends EnvironmentCard {

    public Winterfell(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public MinionCard useAbility(ArrayList<MinionCard> minions) {
        for(MinionCard minion : minions) {
            minion.setFrozen(true);
        }
        return null;
    }
}
