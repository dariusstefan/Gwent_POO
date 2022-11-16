package interpreters;

import cards.Card;
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
        int affectedRow = action.getAffectedRow();
        int x = action.getX();
        int y = action.getY();

        switch (action.getCommand()) {
            case "endPlayerTurn":
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
                break;
            case "placeCard":
                if (game.getActivePlayerIdx() == 1) {
                    placeCard(game, playerOne, action.getCommand(), action.getHandIdx());
                } else {
                    placeCard(game, playerTwo, action.getCommand(), action.getHandIdx());
                }
                break;
            case "useEnvironmentCard":
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
