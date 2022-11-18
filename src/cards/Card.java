package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public abstract class Card {
    @JsonIgnore
    private final boolean placeable;
    private final int mana;

    private final String description;

    private final ArrayList<String> colors;

    private final String name;

    public Card(int mana, String description, ArrayList<String> colors,
                String name, boolean placeable) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.placeable = placeable;
    }

    public int getMana() {
        return mana;
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

    public boolean isPlaceable() {
        return placeable;
    }

    public abstract Card copyCard();
}
