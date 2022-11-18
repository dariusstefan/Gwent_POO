package entities;

import cards.MinionCard;
import fileio.GameInput;

import java.util.ArrayList;

public class Game {
    private final GameInput gameInput;

    public Game(GameInput gameInput) {
        this.gameInput = gameInput;
        this.startingPlayerIdx = gameInput.getStartGame().getStartingPlayer();
    }

    private ArrayList<ArrayList<MinionCard>> board;

    private final int startingPlayerIdx;

    private int activePlayerIdx;

    private int round = 1;

    private int turnOfRound = 1;

    public GameInput getGameInput() {
        return gameInput;
    }

    public ArrayList<ArrayList<MinionCard>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<MinionCard>> board) {
        this.board = board;
        board.add(new ArrayList<>());
        board.add(new ArrayList<>());
        board.add(new ArrayList<>());
        board.add(new ArrayList<>());
    }

    public int getActivePlayerIdx() {
        return activePlayerIdx;
    }

    public void setActivePlayerIdx(int activePlayerIdx) {
        this.activePlayerIdx = activePlayerIdx;
    }

    public void newTurn() {
        if (activePlayerIdx == 1)
            activePlayerIdx = 2;
        else
            activePlayerIdx = 1;
    }

    public int getRound() {
        return round;
    }

    public void incRound() {
        this.round += 1;
    }

    public int getTurnOfRound() {
        return turnOfRound;
    }

    public void incTurnOfRound() {
        this.turnOfRound += 1;
    }

    public void resetTurnOfRound() {
        this.turnOfRound = 1;
    }

    public int getStartingPlayerIdx() {
        return startingPlayerIdx;
    }
}
