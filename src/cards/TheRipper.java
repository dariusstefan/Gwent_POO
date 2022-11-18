package cards;

import java.util.ArrayList;

public class TheRipper extends MinionCard {

    public TheRipper(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(MinionCard card) {
        card.setAttackDamage(card.getAttackDamage() - 2);
        if (card.getAttackDamage() < 0) {
            card.setAttackDamage(0);
        }
    }
}
