package cards;

import java.util.ArrayList;

public final class KingMudface extends HeroCard {

    public KingMudface(final int mana, final String description,
                       final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public HeroCard copyHero() {
        HeroCard copy = new KingMudface(this.getMana(), this.getDescription(),
                this.getColors(), this.getName());
        copy.setHealth(this.getHealth());
        return copy;
    }

    @Override
    public void useAbility(final ArrayList<MinionCard> cards) {
        for (MinionCard card : cards) {
            card.setHealth(card.getHealth() + 1);
        }
    }
}
