package cards;

import java.util.ArrayList;

final public class Winterfell extends EnvironmentCard {

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

    @Override
    public EnvironmentCard copyEnvironment() {
        return new Winterfell(getMana(), getDescription(), getColors(), getName());
    }
}
