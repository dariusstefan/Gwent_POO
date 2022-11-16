package interpreters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Game;
import entities.Player;
import fileio.ActionsInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class Logger {
    private final ObjectMapper mapper;
    private final ArrayNode output;

    public Logger(ObjectMapper mapper, ArrayNode output) {
        this.mapper = mapper;
        this.output = output;
    }

    public void makeAction(Game game, Player playerOne, Player playerTwo, ActionsInput action) {
        String command = action.getCommand();
        int handIdx = action.getHandIdx();
        Coordinates attackerCoords = action.getCardAttacker();
        Coordinates targetCoords = action.getCardAttacked();
        int affectedRow = action.getAffectedRow();
        int playerIdx = action.getPlayerIdx();
        int x = action.getX();
        int y = action.getY();

        ObjectNode objectNode = mapper.createObjectNode();

        switch (command) {
            case "getCardsInHand":
                objectNode.put("command", command);
                objectNode.put("playerIdx", playerIdx);
                if (playerIdx == 1) {
                    objectNode.putPOJO("output", new ArrayList<>(playerOne.getHand()));
                } else {
                    objectNode.putPOJO("output", new ArrayList<>(playerTwo.getHand()));
                }
                break;
            case "getPlayerDeck":
                objectNode.put("command", command);
                objectNode.put("playerIdx", playerIdx);
                if (playerIdx == 1) {
                    objectNode.putPOJO("output", new ArrayList<>(playerOne.getDeckInUsage()));
                } else {
                    objectNode.putPOJO("output", new ArrayList<>(playerTwo.getDeckInUsage()));
                }
                break;
            case "getCardsOnTable":
                objectNode.put("command", command);
                break;
            case "getPlayerTurn":
                objectNode.put("command", command);
                objectNode.put("output", game.getActivePlayerIdx());
                break;
            case "getPlayerHero":
                objectNode.put("command", command);
                objectNode.put("playerIdx", playerIdx);
                if (playerIdx == 1) {
                    objectNode.putPOJO("output", playerOne.getHero().copyHero());
                } else {
                    objectNode.putPOJO("output", playerTwo.getHero().copyHero());
                }
                break;
            case "getCardAtPosition":
                objectNode.put("command", command);
                objectNode.put("x", x);
                objectNode.put("y", y);
                break;
            case "getPlayerMana":
                objectNode.put("command", command);
                objectNode.put("playerIdx", playerIdx);
                break;
            case "getEnvironmentCardsInHand":
                objectNode.put("command", command);
                objectNode.put("playerIdx", playerIdx);
                break;
            case "getFrozenCardsOnTable":
                objectNode.put("command", command);
                break;
            case "getTotalGamesPlayed":
                objectNode.put("command", command);
                break;
            case "getPlayerOneWins":
                objectNode.put("command", command);
                break;
            case "getPlayerTwoWins":
                objectNode.put("command", command);
                break;
            default:
                break;
        }

        output.add(objectNode);
    }
}
