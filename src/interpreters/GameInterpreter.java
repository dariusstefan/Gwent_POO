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

public class GameInterpreter {
    private final ObjectMapper mapper;

    private final ArrayNode output;

    public GameInterpreter(ObjectMapper mapper, ArrayNode output) {
        this.mapper = mapper;
        this.output = output;
    }

    public void makeCommand(Game game, Player playerOne, Player playerTwo, ActionsInput action) {
        Coordinates attackerCoords = action.getCardAttacker();
        Coordinates targetCoords = action.getCardAttacked();
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
                    useEnvironment(game, playerOne, action.getCommand(), action.getHandIdx(), action.getAffectedRow());
                } else {
                    useEnvironment(game, playerTwo, action.getCommand(), action.getHandIdx(), action.getAffectedRow());
                }
                break;
            case "cardUsesAttack":
                break;
            case "cardUsesAbility":
                break;
            case "useAttackHero":
                break;
            case "useHeroAbility":
                break;
            default:
                break;
        }
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
