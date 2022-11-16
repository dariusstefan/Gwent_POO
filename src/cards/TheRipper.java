package cards;

import java.util.ArrayList;

public class TheRipper extends MinionCard {

    public TheRipper(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("WEAKKNEES!\n");
    }
}
