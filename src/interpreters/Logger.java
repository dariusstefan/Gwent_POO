package interpreters;

import cards.MinionCard;
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
    private final GameInterpreter interpreter;

    public Logger(ObjectMapper mapper, ArrayNode output) {
        this.mapper = mapper;
        this.output = output;
        this.interpreter = new GameInterpreter(mapper, output);
    }

    public void addLog(Game game, Player playerOne, Player playerTwo, ActionsInput action) {
        Coordinates attackerCoords = action.getCardAttacker();
        Coordinates targetCoords = action.getCardAttacked();
        int affectedRow = action.getAffectedRow();
        int x = action.getX();
        int y = action.getY();

        ObjectNode objectNode = mapper.createObjectNode();

        switch (action.getCommand()) {
            case "getCardsInHand":
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    objectNode.putPOJO("output", new ArrayList<>(playerOne.getHand()));
                } else {
                    objectNode.putPOJO("output", new ArrayList<>(playerTwo.getHand()));
                }
                output.add(objectNode);
                break;
            case "getPlayerDeck":
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    objectNode.putPOJO("output", new ArrayList<>(playerOne.getDeckInUsage()));
                } else {
                    objectNode.putPOJO("output", new ArrayList<>(playerTwo.getDeckInUsage()));
                }
                output.add(objectNode);
                break;
            case "getCardsOnTable":
                ArrayList<ArrayList<MinionCard>> table = new ArrayList<>();
                table.add(new ArrayList<>(game.getBoard().get(0)));
                table.add(new ArrayList<>(game.getBoard().get(1)));
                table.add(new ArrayList<>(game.getBoard().get(2)));
                table.add(new ArrayList<>(game.getBoard().get(3)));
                objectNode.put("command", action.getCommand());
                objectNode.putPOJO("output", table);
                output.add(objectNode);
                break;
            case "getPlayerTurn":
                objectNode.put("command", action.getCommand());
                objectNode.put("output", game.getActivePlayerIdx());
                output.add(objectNode);
                break;
            case "getPlayerHero":
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    objectNode.putPOJO("output", playerOne.getHero().copyHero());
                } else {
                    objectNode.putPOJO("output", playerTwo.getHero().copyHero());
                }
                output.add(objectNode);
                break;
            case "getCardAtPosition":
                objectNode.put("command", action.getCommand());
                objectNode.put("x", x);
                objectNode.put("y", y);
                output.add(objectNode);
                break;
            case "getPlayerMana":
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    objectNode.put("output", playerOne.getPlayerMana());
                } else {
                    objectNode.put("output", playerTwo.getPlayerMana());
                }
                output.add(objectNode);
                break;
            case "getEnvironmentCardsInHand":
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                output.add(objectNode);
                break;
            case "getFrozenCardsOnTable":
                objectNode.put("command", action.getCommand());
                output.add(objectNode);
                break;
            case "getTotalGamesPlayed":
                objectNode.put("command", action.getCommand());
                output.add(objectNode);
                break;
            case "getPlayerOneWins":
                objectNode.put("command", action.getCommand());
                output.add(objectNode);
                break;
            case "getPlayerTwoWins":
                objectNode.put("command", action.getCommand());
                output.add(objectNode);
                break;
            default:
                interpreter.makeCommand(game, playerOne, playerTwo, action);
                break;
        }
    }
}
