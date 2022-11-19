package cards;

import java.util.ArrayList;

final public class KingMudface extends HeroCard {

    public KingMudface(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public HeroCard copyHero() {
        HeroCard copy = new KingMudface(this.getMana(), this.getDescription(), this.getColors(), this.getName());
        copy.setHealth(this.getHealth());
        return copy;
    }

    @Override
    public void useAbility(ArrayList<MinionCard> cards) {
        for (MinionCard card : cards) {
            card.setHealth(card.getHealth() + 1);
        }
    }
}
