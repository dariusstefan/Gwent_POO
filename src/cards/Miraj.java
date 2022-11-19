package cards;

import java.util.ArrayList;

final public class Miraj extends MinionCard {

    public Miraj(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(MinionCard card) {
        int health = card.getHealth();
        card.setHealth(this.getHealth());
        this.setHealth(health);
    }
}
