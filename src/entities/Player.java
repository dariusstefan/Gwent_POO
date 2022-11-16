package entities;

import cards.Card;
import cards.HeroCard;
import fileio.CardInput;
import fileio.DecksInput;
import interpreters.CardInterpreter;

import java.util.ArrayList;

public class Player {
    private int gamesWon;
    private int gamesPlayed;
    private int mana;
    private final DecksInput decksInput;
    private HeroCard hero;
    private ArrayList<Card> deckInUsage;

    private ArrayList<Card> hand;

    public Player(DecksInput decksInput) {
        this.decksInput = decksInput;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getPlayerMana() {
        return mana;
    }

    public void setPlayerMana(int mana) {
        this.mana = mana;
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
        this.deckInUsage = new ArrayList<Card>();
        for(CardInput card : deck) {
            this.deckInUsage.add(CardInterpreter.getCardObject(card));
        }
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public void resetHand() {
        this.hand = new ArrayList<Card>();
    }

    public void addInHand() {
        this.hand.add(this.deckInUsage.remove(0));
    }
}
