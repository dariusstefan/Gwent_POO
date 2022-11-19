package entities;

import cards.Card;
import cards.HeroCard;
import fileio.CardInput;
import fileio.DecksInput;
import interpreters.CardInterpreter;

import java.util.ArrayList;

final public class Player {
    private final int frontRow;
    private final int backRow;
    public static final int maxManaPerRound = 10;
    private int gamesWon;
    private int gamesPlayed;
    private int mana = 0;
    private final DecksInput decksInput;
    private HeroCard hero;
    private ArrayList<Card> deckInUsage;

    private ArrayList<Card> hand;

    private byte heroAttacked;

    public Player(DecksInput decksInput, int frontRow, int backRow) {
        this.decksInput = decksInput;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.frontRow = frontRow;
        this.backRow = backRow;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void incGamesWon() {
        this.gamesWon += 1;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void incGamesPlayed() {
        this.gamesPlayed += 1;
    }

    public int getPlayerMana() {
        return mana;
    }

    public void addPlayerMana(int round) {
        this.mana += Math.min(round, maxManaPerRound);
    }

    public void subPlayerMana(int cost) {
        this.mana -= cost;
        if (this.mana < 0) {
            this.mana = 0;
        }
    }

    public void resetPlayerMana() {
        this.subPlayerMana(this.getPlayerMana());
    }

    public DecksInput getDecksInput() {
        return decksInput;
    }

    public HeroCard getHero() {
        return hero;
    }

    public void setHero(CardInput hero) {
        this.hero = (HeroCard) CardInterpreter.getCardObject(hero);
    }

    public ArrayList<Card> getDeckInUsage() {
        return deckInUsage;
    }

    public void setDeckInUsage(int idx) {
        ArrayList<CardInput> deck = this.getDecksInput().getDecks().get(idx);
        this.deckInUsage = new ArrayList<>();
        for(CardInput card : deck) {
            this.deckInUsage.add(CardInterpreter.getCardObject(card));
        }
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public void resetHand() {
        this.hand = new ArrayList<>();
    }

    public void addInHand() {
        if (this.deckInUsage.size() > 0)
            this.hand.add(this.deckInUsage.remove(0));
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

    public void resetHeroAttacked() {
        this.heroAttacked = 0;
    }

    public void  setHeroAttacked() {
        this.heroAttacked = 1;
    }
}
