package cards;

import java.util.ArrayList;

public abstract class EnvironmentCard extends Card {
    public EnvironmentCard(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    abstract void useAbility();
}




