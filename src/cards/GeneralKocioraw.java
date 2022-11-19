package cards;

import java.util.ArrayList;

public final class GeneralKocioraw extends HeroCard {

    public GeneralKocioraw(final int mana, final String description,
                           final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public HeroCard copyHero() {
        HeroCard copy = new GeneralKocioraw(this.getMana(), this.getDescription(),
                this.getColors(), this.getName());
        copy.setHealth(this.getHealth());
        return copy;
    }

    @Override
    public void useAbility(final ArrayList<MinionCard> cards) {
        for (MinionCard card : cards) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
    }
}
