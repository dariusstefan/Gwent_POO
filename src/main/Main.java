package main;

import cards.Card;
import cards.HeroCard;
import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Game;
import entities.Player;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import interpreters.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();
        Logger logger = new Logger(objectMapper, output);

        Player playerOne = new Player(inputData.getPlayerOneDecks());
        Player playerTwo = new Player(inputData.getPlayerTwoDecks());

        ArrayList<Game> games = new ArrayList<Game>();
        for (GameInput gameInput : inputData.getGames()) {
            games.add(new Game(gameInput));
        }

        for (Game game : games) {
            int seed = game.getGameInput().getStartGame().getShuffleSeed();
            int playerOneDeckIdx = game.getGameInput().getStartGame().getPlayerOneDeckIdx();
            int playerTwoDeckIdx = game.getGameInput().getStartGame().getPlayerTwoDeckIdx();

            playerOne.setHero(game.getGameInput().getStartGame().getPlayerOneHero());
            playerTwo.setHero(game.getGameInput().getStartGame().getPlayerTwoHero());

            playerOne.setDeckInUsage(playerOneDeckIdx);
            playerTwo.setDeckInUsage(playerTwoDeckIdx);

            playerOne.resetHand();
            playerTwo.resetHand();

            Collections.shuffle(playerOne.getDeckInUsage(), new Random(seed));
            Collections.shuffle(playerTwo.getDeckInUsage(), new Random(seed));

            playerOne.addInHand();
            playerTwo.addInHand();

            playerOne.getHero().initHealth();
            playerTwo.getHero().initHealth();

            game.setActivePlayerIdx(game.getGameInput().getStartGame().getStartingPlayer());

            for (ActionsInput action : game.getGameInput().getActions()) {
                logger.makeAction(game, playerOne, playerTwo, action);
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
