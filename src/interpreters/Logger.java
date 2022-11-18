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
            case "getCardsInHand" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                ArrayList<Card> hand = new ArrayList<>();
                if (action.getPlayerIdx() == 1) {
                    for (Card card : playerOne.getHand()) {
                        hand.add(card.copyCard());
                    }
                } else {
                    for (Card card : playerTwo.getHand()) {
                        hand.add(card.copyCard());
                    }
                }
                objectNode.putPOJO("output", hand);
                output.add(objectNode);
            }
            case "getPlayerDeck" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    objectNode.putPOJO("output", new ArrayList<>(playerOne.getDeckInUsage()));
                } else {
                    objectNode.putPOJO("output", new ArrayList<>(playerTwo.getDeckInUsage()));
                }
                output.add(objectNode);
            }
            case "getCardsOnTable" -> {
                objectNode.put("command", action.getCommand());
                objectNode.putPOJO("output", game.copyBoard());
                output.add(objectNode);
            }
            case "getPlayerTurn" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("output", game.getActivePlayerIdx());
                output.add(objectNode);
            }
            case "getPlayerHero" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    objectNode.putPOJO("output", playerOne.getHero().copyHero());
                } else {
                    objectNode.putPOJO("output", playerTwo.getHero().copyHero());
                }
                output.add(objectNode);
            }
            case "getCardAtPosition" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("x", x);
                objectNode.put("y", y);
                if (y >= game.getBoard().get(x).size()) {
                    objectNode.put("output", "No card available at that position.");
                } else {
                    MinionCard card = new MinionCard(game.getBoard().get(x).get(y));
                    objectNode.putPOJO("output", card);
                }
                output.add(objectNode);
            }
            case "getPlayerMana" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    objectNode.put("output", playerOne.getPlayerMana());
                } else {
                    objectNode.put("output", playerTwo.getPlayerMana());
                }
                output.add(objectNode);
            }
            case "getEnvironmentCardsInHand" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("playerIdx", action.getPlayerIdx());
                ArrayList<Card> envCards;
                if (action.getPlayerIdx() == 1) {
                    envCards = new ArrayList<>(playerOne.getHand());
                } else {
                    envCards = new ArrayList<>(playerTwo.getHand());
                }
                envCards.removeIf(Card::isPlaceable);
                objectNode.putPOJO("output", envCards);
                output.add(objectNode);
            }
            case "getFrozenCardsOnTable" -> {
                objectNode.put("command", action.getCommand());
                ArrayList<MinionCard> frozen = new ArrayList<>();
                for (ArrayList<MinionCard> row : game.getBoard()) {
                    for (MinionCard minion : row) {
                        if (minion.isFrozen()) {
                            frozen.add(new MinionCard(minion));
                        }
                    }
                }
                objectNode.putPOJO("output", frozen);
                output.add(objectNode);
            }
            case "getTotalGamesPlayed" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("output", playerOne.getGamesPlayed());
                output.add(objectNode);
            }
            case "getPlayerOneWins" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("output", playerOne.getGamesWon());
                output.add(objectNode);
            }
            case "getPlayerTwoWins" -> {
                objectNode.put("command", action.getCommand());
                objectNode.put("output", playerTwo.getGamesWon());
                output.add(objectNode);
            }
            default -> {
                if (!game.isFinished()) {
                    interpreter.makeCommand(game, playerOne, playerTwo, action);
                }
            }
        }
    }
}
