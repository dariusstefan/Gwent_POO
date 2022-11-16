package cards;

import java.util.ArrayList;

public class Disciple extends MinionCard {

    public Disciple(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    public void useAbility() {
        System.out.println("GODSPLAN!\n");
    }
}
