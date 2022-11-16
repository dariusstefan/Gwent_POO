package cards;

import java.util.ArrayList;

public abstract class EnvironmentCard extends Card {
    public EnvironmentCard(int manaCost, String description, ArrayList<String> colors, String name) {
        super(manaCost, description, colors, name);
    }

    abstract void useAbility();

    @Override
    public String toString() {
        return "EnvironmentCard{" +
                "manaCost=" + getManaCost() +
                ", description='" + getDescription() + '\'' +
                ", colors=" + getColors() +
                ", name='" + getName() + '\'' +
                '}';
    }
}




