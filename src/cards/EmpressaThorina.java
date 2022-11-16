package cards;

import java.util.ArrayList;

public class EmpressaThorina extends HeroCard {

    public EmpressaThorina(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public HeroCard copyHero() {
        HeroCard copy = new EmpressaThorina(this.getMana(), this.getDescription(), this.getColors(), this.getName());
        copy.setHealth(this.getHealth());
        return copy;
    }

    @Override
    void useAbility() {
        System.out.println("LOWBLOW!\n");
    }
}
