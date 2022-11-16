package cards;

import java.util.ArrayList;

public class Winterfell extends EnvironmentCard {

    public Winterfell(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("WINTERFELL!\n");
    }
}
