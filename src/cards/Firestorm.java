package cards;

import java.util.ArrayList;

public class Firestorm extends EnvironmentCard {

    public Firestorm(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("FIRESTORM!\n");
    }
}
