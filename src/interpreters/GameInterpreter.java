package interpreters;

import cards.Card;
import cards.EnvironmentCard;
import cards.MinionCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Game;
import entities.Player;
import fileio.ActionsInput;
import fileio.Coordinates;

import java.io.ObjectStreamException;
import java.util.ArrayList;

public class GameInterpreter {
    private final ObjectMapper mapper;

    private final ArrayNode output;

    public GameInterpreter(ObjectMapper mapper, ArrayNode output) {
        this.mapper = mapper;
        this.output = output;
    }

    public void makeCommand(Game game, Player playerOne, Player playerTwo, ActionsInput action) {
        Coordinates atkCoords = action.getCardAttacker();
        Coordinates tgtCoords = action.getCardAttacked();
        int x = action.getX();
        int y = action.getY();

        switch (action.getCommand()) {
            case "endPlayerTurn":
                endTurn(game, playerOne, playerTwo);
                break;
            case "placeCard":
                if (game.getActivePlayerIdx() == 1) {
                    placeCard(game, playerOne, action.getCommand(), action.getHandIdx());
                } else {
                    placeCard(game, playerTwo, action.getCommand(), action.getHandIdx());
                }
                break;
            case "useEnvironmentCard":
                if (game.getActivePlayerIdx() == 1) {
                    useEnvironment(game, playerOne, action.getCommand(), action.getHandIdx(),
                            action.getAffectedRow());
                } else {
                    useEnvironment(game, playerTwo, action.getCommand(), action.getHandIdx(),
                            action.getAffectedRow());
                }
                break;
            case "cardUsesAttack":
                if (game.getActivePlayerIdx() == 1) {
                    minionAttack(game, action.getCommand(), atkCoords, tgtCoords, playerTwo);
                } else {
                    minionAttack(game, action.getCommand(), atkCoords, tgtCoords, playerOne);
                }
                break;
            case "cardUsesAbility":
                String name = game.getBoard().get(atkCoords.getX()).get(atkCoords.getY()).getName();
                if (game.getActivePlayerIdx() == 1) {
                    if (name.equals("Disciple")) {
                        minionAbility(game, action.getCommand(), atkCoords,
                                tgtCoords, playerOne, true);
                    } else {
                        minionAbility(game, action.getCommand(), atkCoords,
                                tgtCoords, playerTwo, false);
                    }
                } else {
                    if (name.equals("Disciple")) {
                        minionAbility(game, action.getCommand(), atkCoords,
                                tgtCoords, playerTwo, true);
                    } else {
                        minionAbility(game, action.getCommand(), atkCoords,
                                tgtCoords, playerOne, false);
                    }
                }
                break;
            case "useAttackHero":
                if (game.getActivePlayerIdx() == 1) {
                    attackHero(game, action.getCommand(), atkCoords, playerTwo);
                    if (playerTwo.getHero().isDead()) {
                        ObjectNode node = mapper.createObjectNode();
                        node.put("gameEnded", "Player one killed the enemy hero.");
                        output.add(node);
                        game.finish();
                        playerOne.incGamesWon();
                        playerOne.incGamesPlayed();
                        playerTwo.incGamesPlayed();
                    }
                } else {
                    attackHero(game, action.getCommand(), atkCoords, playerOne);
                    if (playerOne.getHero().isDead()) {
                        ObjectNode node = mapper.createObjectNode();
                        node.put("gameEnded", "Player two killed the enemy hero.");
                        output.add(node);
                        game.finish();
                        playerTwo.incGamesWon();
                        playerOne.incGamesPlayed();
                        playerTwo.incGamesPlayed();
                    }
                }
                break;
            case "useHeroAbility":
                break;
            default:
                break;
        }
    }

    private void attackHero(Game game, String command, Coordinates striker, Player target) {
        int strikerX = striker.getX();
        int strikerY = striker.getY();

        boolean errorFlag = false;
        String errorText = null;

        if (game.getAttackMask()[strikerX][strikerY] == 0) {
            if (!game.getBoard().get(strikerX).get(strikerY).isFrozen()) {
                MinionCard tank = getTankFromRow(game, target);
                if (tank == null) {
                    game.getBoard().get(strikerX).get(strikerY).attackHero(target.getHero());
                    game.getAttackMask()[strikerX][strikerY] = 1;
                } else {
                    errorFlag = true;
                    errorText = "Attacked card is not of type 'Tank'.";
                }
            } else {
                errorFlag = true;
                errorText = "Attacker card is frozen.";
            }
        } else {
            errorFlag = true;
            errorText = "Attacker card has already attacked this turn.";
        }
        if (errorFlag) {
            ObjectNode error = mapper.createObjectNode();
            error.put("command", command);
            error.putPOJO("cardAttacker", striker);
            error.put("error", errorText);
            output.add(error);
        }
    }

    private void minionAbility(Game game, String command, Coordinates striker,
                               Coordinates attacked, Player target, boolean isDisciple) {
        int strikerX = striker.getX();
        int strikerY = striker.getY();

        boolean errorFlag = false;
        String errorText = null;
        if (attacked.getX() == target.getFrontRow() || attacked.getX() == target.getBackRow()) {
            if (game.getAttackMask()[strikerX][strikerY] == 0) {
                if (!game.getBoard().get(strikerX).get(strikerY).isFrozen()) {
                    MinionCard card = game.getBoard().get(attacked.getX()).get(attacked.getY());
                    if (!isDisciple) {
                        boolean isTank = card.isTank();
                        MinionCard tank = getTankFromRow(game, target);
                        if (tank == null || isTank) {
                            game.getBoard().get(strikerX).get(strikerY).useAbility(card);
                            if (card.getHealth() <= 0) {
                                game.getBoard().get(attacked.getX()).remove(card);
                            }
                            game.getAttackMask()[strikerX][strikerY] = 1;
                        } else {
                            errorFlag = true;
                            errorText = "Attacked card is not of type 'Tank'.";
                        }
                    } else {
                        game.getBoard().get(strikerX).get(strikerY).useAbility(card);
                        game.getAttackMask()[strikerX][strikerY] = 1;
                    }
                } else {
                    errorFlag = true;
                    errorText = "Attacker card is frozen.";
                }
            } else {
                errorFlag = true;
                errorText = "Attacker card has already attacked this turn.";
            }
        } else {
            errorFlag = true;
            if (isDisciple) {
                errorText = "Attacked card does not belong to the current player.";
            } else {
                errorText = "Attacked card does not belong to the enemy.";
            }
        }
        if (errorFlag) {
            ObjectNode error = mapper.createObjectNode();
            error.putPOJO("cardAttacked", attacked);
            error.putPOJO("cardAttacker", striker);
            error.put("command", command);
            error.put("error", errorText);
            output.add(error);
        }
    }

    private void minionAttack(Game game, String command, Coordinates striker,
                              Coordinates attacked, Player target) {
        int strikerX = striker.getX();
        int strikerY = striker.getY();

        boolean errorFlag = false;
        String errorText = null;
        if (attacked.getX() == target.getFrontRow() || attacked.getX() == target.getBackRow()) {
            if (game.getAttackMask()[strikerX][strikerY] == 0) {
                if (!game.getBoard().get(strikerX).get(strikerY).isFrozen()) {
                    MinionCard card = game.getBoard().get(attacked.getX()).get(attacked.getY());
                    boolean isTank = card.isTank();
                    MinionCard tank = getTankFromRow(game, target);
                    if (tank == null || isTank) {
                        game.getBoard().get(strikerX).get(strikerY).attack(card);
                        if (card.getHealth() <= 0) {
                            game.getBoard().get(attacked.getX()).remove(card);
                        }
                        game.getAttackMask()[strikerX][strikerY] = 1;
                    } else {
                        errorFlag = true;
                        errorText = "Attacked card is not of type 'Tank'.";
                    }
                } else {
                    errorFlag = true;
                    errorText = "Attacker card is frozen.";
                }
            } else {
                errorFlag = true;
                errorText = "Attacker card has already attacked this turn.";
            }
        } else {
            errorFlag = true;
            errorText = "Attacked card does not belong to the enemy.";
        }
        if (errorFlag) {
            ObjectNode error = mapper.createObjectNode();
            error.putPOJO("cardAttacked", attacked);
            error.putPOJO("cardAttacker", striker);
            error.put("command", command);
            error.put("error", errorText);
            output.add(error);
        }
    }

    private MinionCard getTankFromRow(Game game, Player target) {
        MinionCard tank = null;
        ArrayList<MinionCard> rowToCheck = game.getBoard().get(target.getFrontRow());
        for (MinionCard minion : rowToCheck) {
            if (minion.isTank()) {
                tank = minion;
                break;
            }
        }
        return tank;
    }

    private void endTurn(Game game, Player playerOne, Player playerTwo) {
        if (game.getActivePlayerIdx() == 1) {
            for (MinionCard card : game.getBoard().get(playerOne.getFrontRow())) {
                card.setFrozen(false);
            }
            for (MinionCard card : game.getBoard().get(playerOne.getBackRow())) {
                card.setFrozen(false);
            }
        } else {
            for (MinionCard card : game.getBoard().get(playerTwo.getFrontRow())) {
                card.setFrozen(false);
            }
            for (MinionCard card : game.getBoard().get(playerTwo.getBackRow())) {
                card.setFrozen(false);
            }
        }
        if (game.getTurnOfRound() == 1) {
            game.newTurn();
            game.incTurnOfRound();
        } else {
            game.newTurn();
            game.resetTurnOfRound();
            game.incRound();
            playerOne.addPlayerMana(game.getRound());
            playerTwo.addPlayerMana(game.getRound());
            playerOne.addInHand();
            playerTwo.addInHand();
        }
    }

    private void useEnvironment(Game game, Player player, String command, int handIdx, int affectedRow) {
        if (!player.getHand().get(handIdx).isPlaceable()) {
            EnvironmentCard envToUse = (EnvironmentCard) player.getHand().get(handIdx);
            if (player.getPlayerMana() >= envToUse.getMana()) {
                if (affectedRow != player.getBackRow() && affectedRow != player.getFrontRow()) {
                    MinionCard stolenCard = envToUse.useAbility(game.getBoard().get(affectedRow));
                    boolean errorFlag = false;
                    if (stolenCard != null) {
                        if (game.getBoard().get(3 - affectedRow).size() < 5) {
                            game.getBoard().get(3 - affectedRow).add(stolenCard);
                            game.getBoard().get(affectedRow).remove(stolenCard);
                        } else {
                            errorFlag = true;
                            ObjectNode error = mapper.createObjectNode();
                            error.put("command", command);
                            error.put("handIdx", handIdx);
                            error.put("affectedRow", affectedRow);
                            error.put("error", "Cannot steal enemy card since the player's row is full.");
                            output.add(error);
                        }
                    }
                    if (!errorFlag) {
                        player.getHand().remove(handIdx);
                        player.subPlayerMana(envToUse.getMana());
                    }
                } else {
                    ObjectNode error = mapper.createObjectNode();
                    error.put("command", command);
                    error.put("handIdx", handIdx);
                    error.put("affectedRow", affectedRow);
                    error.put("error", "Chosen row does not belong to the enemy.");
                    output.add(error);
                }
            } else {
                ObjectNode error = mapper.createObjectNode();
                error.put("command", command);
                error.put("handIdx", handIdx);
                error.put("affectedRow", affectedRow);
                error.put("error", "Not enough mana to use environment card.");
                output.add(error);
            }
        } else {
            ObjectNode error = mapper.createObjectNode();
            error.put("command", command);
            error.put("handIdx", handIdx);
            error.put("affectedRow", affectedRow);
            error.put("error", "Chosen card is not of type environment.");
            output.add(error);
        }
    }
    private void placeCard(Game game, Player player, String command, int handIdx) {
        if (player.getHand().get(handIdx).isPlaceable()) {
            MinionCard cardToPlace = (MinionCard) player.getHand().get(handIdx);
            if (player.getPlayerMana() >= cardToPlace.getMana()) {
                int row;
                if (cardToPlace.isFirstRow()) {
                    row = player.getFrontRow();
                } else {
                    row = player.getBackRow();
                }
                if (game.getBoard().get(row).size() < 5) {
                    game.getBoard().get(row).add(cardToPlace);
                    player.getHand().remove(handIdx);
                    player.subPlayerMana(cardToPlace.getMana());
                } else {
                    ObjectNode error = mapper.createObjectNode();
                    error.put("command", command);
                    error.put("handIdx", handIdx);
                    error.put("error", "Cannot place card on table since row is full.");
                    output.add(error);
                }
            } else {
                ObjectNode error = mapper.createObjectNode();
                error.put("command", command);
                error.put("handIdx", handIdx);
                error.put("error", "Not enough mana to place card on table.");
                output.add(error);
            }
        } else {
            ObjectNode error = mapper.createObjectNode();
            error.put("command", command);
            error.put("handIdx", handIdx);
            error.put("error", "Cannot place environment card on table.");
            output.add(error);
        }
    }
}
