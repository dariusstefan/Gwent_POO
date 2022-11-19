package cards;

import java.util.ArrayList;

public final class EmpressThorina extends HeroCard {

    public EmpressThorina(final int mana, final String description,
                          final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public HeroCard copyHero() {
        HeroCard copy = new EmpressThorina(this.getMana(), this.getDescription(),
                this.getColors(), this.getName());
        copy.setHealth(this.getHealth());
        return copy;
    }

    @Override
    public void useAbility(final ArrayList<MinionCard> cards) {
        MinionCard highestHealth = null;
        int maxHealth = 0;
        for (MinionCard card : cards) {
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
                highestHealth = card;
            }
        }
        if (highestHealth != null) {
            cards.remove(highestHealth);
        }
    }
}
