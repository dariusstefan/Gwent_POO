package cards;

import java.util.ArrayList;

public class TheRipper extends MinionCard {

    public TheRipper(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("WEAKKNEES!\n");
    }
}
