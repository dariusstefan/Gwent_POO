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

    public Card(final int mana, final String description, final ArrayList<String> colors,
                final String name, final boolean placeable) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.placeable = placeable;
    }

    public final int getMana() {
        return mana;
    }

    public final String getDescription() {
        return description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final String getName() {
        return name;
    }

    public final boolean isPlaceable() {
        return placeable;
    }

    /**Should return a deep-copied object of class Card.*/
    public abstract Card copyCard();
}
