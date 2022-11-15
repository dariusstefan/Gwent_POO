package cards;

import java.util.ArrayList;

public abstract class Card {
    private final int manaCost;

    private final String description;

    private final ArrayList<String> colors;

    private final String name;

    public Card(int manaCost, String description, ArrayList<String> colors, String name) {
        this.manaCost = manaCost;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public String getName() {
        return name;
    }
}
