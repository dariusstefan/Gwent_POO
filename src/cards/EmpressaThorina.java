package cards;

import java.util.ArrayList;

public class EmpressaThorina extends HeroCard {

    public EmpressaThorina(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    @Override
    void useAbility() {
        System.out.println("LOWBLOW!\n");
    }
}
