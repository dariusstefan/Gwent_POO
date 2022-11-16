package cards;

import java.util.ArrayList;

public class LordRoyce extends HeroCard {

    public LordRoyce(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("SUBZERO!\n");
    }
}
