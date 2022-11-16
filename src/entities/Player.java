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

    public void setHero(HeroCard hero) {
        this.hero = hero;
    }

    public ArrayList<Card> getDeckInUsage() {
        return deckInUsage;
    }

    public void setDeckInUsage(ArrayList<CardInput> deck) {
        this.deckInUsage = new ArrayList<Card>();
        for(CardInput card : deck) {
            this.deckInUsage.add(CardInterpreter.getCardObject(card));
        }
    }
}
