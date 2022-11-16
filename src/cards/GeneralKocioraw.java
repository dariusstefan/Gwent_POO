package cards;

import java.util.ArrayList;

public class GeneralKocioraw extends HeroCard {

    public GeneralKocioraw(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("BLOODTHIRST!\n");
    }
}
