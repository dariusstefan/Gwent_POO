package cards;

import java.util.ArrayList;

final public class EmpressThorina extends HeroCard {

    public EmpressThorina(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public HeroCard copyHero() {
        HeroCard copy = new EmpressThorina(this.getMana(), this.getDescription(), this.getColors(), this.getName());
        copy.setHealth(this.getHealth());
        return copy;
    }

    @Override
    public void useAbility(ArrayList<MinionCard> cards) {
        MinionCard highestHealth = null;
        int maxHealth = 0;
        for (MinionCard card : cards) {
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
                highestHealth = card;
            }
        }
        if (highestHealth != null)
            cards.remove(highestHealth);
    }
}
