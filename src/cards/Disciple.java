package cards;

import java.util.ArrayList;

final public class Disciple extends MinionCard {

    public Disciple(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(MinionCard card) {
        card.setHealth(card.getHealth() + 2);
    }
}
