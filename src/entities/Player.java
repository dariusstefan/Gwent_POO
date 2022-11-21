package entities;

import cards.Card;
import cards.HeroCard;
import fileio.CardInput;
import fileio.DecksInput;
import interpreters.CardInterpreter;

import java.util.ArrayList;
public final class Player {
    private final int frontRow;
    private final int backRow;
    public static final int MAX_MANA_PER_ROUND = 10;
    private int gamesWon;
    private int gamesPlayed;
    private int mana = 0;
    private final DecksInput decksInput;
    private HeroCard hero;
    private ArrayList<Card> deckInUsage;

    private ArrayList<Card> hand;

    private byte heroAttacked;

    public Player(final DecksInput decksInput, final int frontRow, final int backRow) {
        this.decksInput = decksInput;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.frontRow = frontRow;
        this.backRow = backRow;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    /**This method increases the number of games won for a player.*/
    public void incGamesWon() {
        this.gamesWon += 1;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**This method increases the number of games played for a player.*/
    public void incGamesPlayed() {
        this.gamesPlayed += 1;
    }

    public int getPlayerMana() {
        return mana;
    }

    /**This method adds mana to a player.*/
    public void addPlayerMana(final int round) {
        this.mana += Math.min(round, MAX_MANA_PER_ROUND);
    }

    /**This method is used to reduce mana of a player with a specified cost.*/
    public void subPlayerMana(final int cost) {
        this.mana -= cost;
        if (this.mana < 0) {
            this.mana = 0;
        }
    }

    /**This method resets mana for a player to zero. It is used when a game is finished.*/
    public void resetPlayerMana() {
        this.subPlayerMana(this.getPlayerMana());
    }

    public DecksInput getDecksInput() {
        return decksInput;
    }

    public HeroCard getHero() {
        return hero;
    }

    public void setHero(final CardInput hero) {
        this.hero = (HeroCard) CardInterpreter.getCardObject(hero);
    }

    public ArrayList<Card> getDeckInUsage() {
        return deckInUsage;
    }

    /**This method sets the deck that a player uses in a game.
     * It is using the Card Interpreter to extract idx deck from DecksInput field.*/
    public void setDeckInUsage(final int idx) {
        ArrayList<CardInput> deck = this.getDecksInput().getDecks().get(idx);
        this.deckInUsage = new ArrayList<>();
        for (CardInput card : deck) {
            this.deckInUsage.add(CardInterpreter.getCardObject(card));
        }
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    /**This method resets the arraylist of cards that represents the hand of a player.*/
    public void resetHand() {
        this.hand = new ArrayList<>();
    }

    /**This method adds a card to the hand of a player from his current deck.*/
    public void addInHand() {
        if (this.deckInUsage.size() > 0) {
            this.hand.add(this.deckInUsage.remove(0));
        }
    }

    public int getFrontRow() {
        return frontRow;
    }

    public int getBackRow() {
        return backRow;
    }

    public byte getHeroAttacked() {
        return heroAttacked;
    }

    /**This method resets the field which specifies if a player
     * used his hero ability in the current round.*/
    public void resetHeroAttacked() {
        this.heroAttacked = 0;
    }

    /**This method sets that a player used his hero ability in the current round.*/
    public void  setHeroAttacked() {
        this.heroAttacked = 1;
    }
}
