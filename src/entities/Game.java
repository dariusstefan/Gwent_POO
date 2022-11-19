package entities;

import cards.MinionCard;
import checker.CheckerConstants;
import fileio.GameInput;

import java.util.ArrayList;
 public final class Game {
    private final GameInput gameInput;

    public Game(final GameInput gameInput) {
        this.gameInput = gameInput;
        this.startingPlayerIdx = gameInput.getStartGame().getStartingPlayer();
    }

    private ArrayList<ArrayList<MinionCard>> board;

    private final int startingPlayerIdx;

    private int activePlayerIdx;

    private int round = 1;

    private int turnOfRound = 1;

    private byte[][] attackMask;

    public GameInput getGameInput() {
        return gameInput;
    }

    public ArrayList<ArrayList<MinionCard>> getBoard() {
        return board;
    }

    /**This method sets the board for a new game.*/
    public void setBoard() {
        this.board = new ArrayList<>();
        board.add(new ArrayList<>());
        board.add(new ArrayList<>());
        board.add(new ArrayList<>());
        board.add(new ArrayList<>());
    }

    private boolean finished = false;

    public int getActivePlayerIdx() {
        return activePlayerIdx;
    }

    public void setActivePlayerIdx(final int activePlayerIdx) {
        this.activePlayerIdx = activePlayerIdx;
    }

    /**This method changes the active player of a game.
     * Each game round has 2 turns, one for each player.
     * This method also reset the attackMask which is a matrix of bytes that should tell
     * if a card on board attacked that turn or not. Each byte of the matrix represents a card.*/
    public void newTurn() {
        if (activePlayerIdx == 1) {
            activePlayerIdx = 2;
            this.resetAttackMask();
        } else {
            activePlayerIdx = 1;
            this.resetAttackMask();
        }
    }

    public int getRound() {
        return round;
    }

    /**This method increments the current number of rounds.*/
    public void incRound() {
        this.round += 1;
    }

    public int getTurnOfRound() {
        return turnOfRound;
    }

    /**This method increments the current turn of a round.*/
    public void incTurnOfRound() {
        this.turnOfRound += 1;
    }

    /**This method resets turn for each round to 1.*/
    public void resetTurnOfRound() {
        this.turnOfRound = 1;
    }

    public int getStartingPlayerIdx() {
        return startingPlayerIdx;
    }

    /**This method returns the attackMask which is a matrix of bytes that should tell
     * if a card on board attacked that turn or not. Each byte of the matrix represents a card.*/
    public byte[][] getAttackMask() {
        return attackMask;
    }

    /**This method resets the attackMask for each turn.*/
    public void resetAttackMask() {
        this.attackMask = new byte[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}};
    }

    /**This method returns a deep-copy of the board to be used with JSON output format.*/
    public ArrayList<ArrayList<MinionCard>> copyBoard() {
        ArrayList<ArrayList<MinionCard>> copy = new ArrayList<>();
        copy.add(new ArrayList<>());
        copy.add(new ArrayList<>());
        copy.add(new ArrayList<>());
        copy.add(new ArrayList<>());
        for (int i = 0; i < CheckerConstants.BOARD_NO_ROWS; i++) {
            for (MinionCard minion : this.getBoard().get(i)) {
                copy.get(i).add(new MinionCard(minion));
            }
        }
        return copy;
    }

    public boolean isFinished() {
        return finished;
    }

    /**This method finishes a game.*/
    public void finish() {
        this.finished = true;
    }
}
