package cards;

import java.util.ArrayList;

final public class GeneralKocioraw extends HeroCard {

    public GeneralKocioraw(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public HeroCard copyHero() {
        HeroCard copy = new GeneralKocioraw(this.getMana(), this.getDescription(), this.getColors(), this.getName());
        copy.setHealth(this.getHealth());
        return copy;
    }

    @Override
    public void useAbility(ArrayList<MinionCard> cards) {
        for (MinionCard card : cards) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
    }
}
