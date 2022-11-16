package cards;

import java.util.ArrayList;

public class Miraj extends MinionCard {

    public Miraj(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("SKYJACK!\n");
    }
}
