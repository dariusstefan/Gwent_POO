package entities;

import cards.MinionCard;
import fileio.GameInput;

import java.util.ArrayList;

public class Game {
    private final GameInput gameInput;

    public Game(GameInput gameInput) {
        this.gameInput = gameInput;
    }

    private ArrayList<ArrayList<MinionCard>> board;

    private int activePlayerIdx;

    public GameInput getGameInput() {
        return gameInput;
    }

    public ArrayList<ArrayList<MinionCard>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<MinionCard>> board) {
        this.board = board;
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
}
