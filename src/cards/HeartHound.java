package cards;

import java.util.ArrayList;

public class HeartHound extends EnvironmentCard {

    public HeartHound(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("HEARTHOUND!\n");
    }
}
