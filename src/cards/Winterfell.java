package cards;

import java.util.ArrayList;

public class Winterfell extends EnvironmentCard {

    public Winterfell(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("WINTERFELL!\n");
    }
}
