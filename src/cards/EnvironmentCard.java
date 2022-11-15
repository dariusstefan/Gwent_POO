package cards;

import java.util.ArrayList;

public abstract class EnvironmentCard extends Card {
    public EnvironmentCard(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    abstract void useAbility();
}

class Firestorm extends EnvironmentCard {

    public Firestorm(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("FIRESTORM!\n");
    }
}

class Winterfell extends EnvironmentCard {

    public Winterfell(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("WINTERFELL!\n");
    }
}

class HeartHound extends EnvironmentCard {

    public HeartHound(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("HEARTHOUND!\n");
    }
}




