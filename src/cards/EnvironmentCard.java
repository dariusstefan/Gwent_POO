package cards;

import java.util.ArrayList;

public abstract class EnvironmentCard extends Card {
    public EnvironmentCard(final int mana, final String description,
                           final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name, false);
    }

    /**This method should implement ability of an environment card.*/
    public abstract MinionCard useAbility(ArrayList<MinionCard> minions);

    /**This method should return a deep-copied object of a class that extends Environment card.*/
    public abstract EnvironmentCard copyEnvironment();

    /**This method implements the abstract method of parent class Card.*/
    public final Card copyCard() {
        return copyEnvironment();
    }
}




