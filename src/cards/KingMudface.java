package cards;

import java.util.ArrayList;

public class KingMudface extends HeroCard {

    public KingMudface(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("EARTHBORN!\n");
    }
}
