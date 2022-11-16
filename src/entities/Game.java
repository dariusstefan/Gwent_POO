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

    public GameInput getGameInput() {
        return gameInput;
    }

    public ArrayList<ArrayList<MinionCard>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<MinionCard>> board) {
        this.board = board;
    }
}
