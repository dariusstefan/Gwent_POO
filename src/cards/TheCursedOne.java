package cards;

import java.util.ArrayList;

public class TheCursedOne extends MinionCard {

    public TheCursedOne(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("SHAPESHIFT!\n");
    }
}
