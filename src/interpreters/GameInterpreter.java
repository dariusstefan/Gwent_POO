package interpreters;

import cards.EnvironmentCard;
import cards.MinionCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Game;
import entities.Player;
import fileio.ActionsInput;
import fileio.Coordinates;
import java.util.ArrayList;

public final class GameInterpreter {
    public static final int OPP_ROWS_SUM = 3;
    public static final int FULL_ROW_SZ = 5;
    private final ObjectMapper mapper;

    private final ArrayNode output;

    public GameInterpreter(final ObjectMapper mapper, final ArrayNode output) {
        this.mapper = mapper;
        this.output = output;
    }

    /**This method interprets a command that is not for debugging.
     * It is used to make game command, and it calls functions that implements thes commands.*/
    public void makeCommand(final Game game, final Player playerOne,
                            final Player playerTwo, final ActionsInput action) {
        Coordinates atkCoords = action.getCardAttacker();
        Coordinates tgtCoords = action.getCardAttacked();

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
                if (game.getActivePlayerIdx() == 1) {
                    useHeroAbility(game, playerOne, action.getCommand(),
                            action.getAffectedRow());
                } else {
                    useHeroAbility(game, playerTwo, action.getCommand(),
                            action.getAffectedRow());
                }
                break;
            default:
                break;
        }
    }

    private void useHeroAbility(final Game game, final Player player,
                                final String command, final int affectedRow) {

        boolean isAttack = player.getHero().getName().equals("Lord Royce")
                || player.getHero().getName().equals("Empress Thorina");
        boolean errorFlag = false;
        String errorText = null;
        if (player.getPlayerMana() >= player.getHero().getMana()) {
            if (player.getHeroAttacked() == 0) {
                if (affectedRow == player.getBackRow() || affectedRow == player.getFrontRow()) {
                    if (isAttack) {
                        errorFlag = true;
                        errorText = "Selected row does not belong to the enemy.";
                    } else {
                        player.getHero().useAbility(game.getBoard().get(affectedRow));
                        player.subPlayerMana(player.getHero().getMana());
                        player.setHeroAttacked();
                    }
                } else {
                    if (isAttack) {
                        player.getHero().useAbility(game.getBoard().get(affectedRow));
                        player.subPlayerMana(player.getHero().getMana());
                        player.setHeroAttacked();
                    } else {
                        errorFlag = true;
                        errorText = "Selected row does not belong to the current player.";
                    }
                }
            } else {
                errorFlag = true;
                errorText = "Hero has already attacked this turn.";
            }
        } else {
            errorFlag = true;
            errorText = "Not enough mana to use hero's ability.";
        }
        if (errorFlag) {
            ObjectNode error = mapper.createObjectNode();
            error.put("command", command);
            error.put("affectedRow", affectedRow);
            error.put("error", errorText);
            output.add(error);
        }
    }

    private void attackHero(final Game game, final String command,
                            final Coordinates striker, final Player target) {
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

    private void minionAbility(final Game game, final String command, final Coordinates striker,
                               final Coordinates attacked, final Player target,
                               final boolean isDisciple) {
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

    private void minionAttack(final Game game, final String command, final Coordinates striker,
                              final Coordinates attacked, final Player target) {
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

    private MinionCard getTankFromRow(final Game game, final Player target) {
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

    private void endTurn(final Game game, final Player playerOne, final Player playerTwo) {
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
            playerOne.resetHeroAttacked();
            playerTwo.resetHeroAttacked();
        }
    }

    private void useEnvironment(final Game game, final Player player, final String command,
                                final int handIdx, final int affectedRow) {
        boolean errorFlag = false;
        String errorText = null;

        if (!player.getHand().get(handIdx).isPlaceable()) {
            EnvironmentCard envToUse = (EnvironmentCard) player.getHand().get(handIdx);
            if (player.getPlayerMana() >= envToUse.getMana()) {
                if (affectedRow != player.getBackRow() && affectedRow != player.getFrontRow()) {
                    MinionCard stolenCard = envToUse.useAbility(game.getBoard().get(affectedRow));
                    if (stolenCard != null) {
                        if (game.getBoard().get(OPP_ROWS_SUM - affectedRow).size() < FULL_ROW_SZ) {
                            game.getBoard().get(OPP_ROWS_SUM - affectedRow).add(stolenCard);
                            game.getBoard().get(affectedRow).remove(stolenCard);
                        } else {
                            errorFlag = true;
                            errorText = "Cannot steal enemy card since the player's row is full.";
                        }
                    }
                    if (!errorFlag) {
                        player.getHand().remove(handIdx);
                        player.subPlayerMana(envToUse.getMana());
                    }
                } else {
                    errorFlag = true;
                    errorText = "Chosen row does not belong to the enemy.";
                }
            } else {
                errorFlag = true;
                errorText = "Not enough mana to use environment card.";
            }
        } else {
            errorFlag = true;
            errorText = "Chosen card is not of type environment.";
        }
        if (errorFlag) {
            ObjectNode error = mapper.createObjectNode();
            error.put("command", command);
            error.put("handIdx", handIdx);
            error.put("affectedRow", affectedRow);
            error.put("error", errorText);
            output.add(error);
        }
    }
    private void placeCard(final Game game, final Player player,
                           final String command, final int handIdx) {
        boolean errorFlag = false;
        String errorText = null;

        if (player.getHand().get(handIdx).isPlaceable()) {
            MinionCard cardToPlace = (MinionCard) player.getHand().get(handIdx);
            if (player.getPlayerMana() >= cardToPlace.getMana()) {
                int row;
                if (cardToPlace.isFirstRow()) {
                    row = player.getFrontRow();
                } else {
                    row = player.getBackRow();
                }
                if (game.getBoard().get(row).size() < FULL_ROW_SZ) {
                    game.getBoard().get(row).add(cardToPlace);
                    player.getHand().remove(handIdx);
                    player.subPlayerMana(cardToPlace.getMana());
                } else {
                    errorFlag = true;
                    errorText = "Cannot place card on table since row is full.";
                }
            } else {
                errorFlag = true;
                errorText = "Not enough mana to place card on table.";
            }
        } else {
            errorFlag = true;
            errorText = "Cannot place environment card on table.";
        }
        if (errorFlag) {
            ObjectNode error = mapper.createObjectNode();
            error.put("command", command);
            error.put("handIdx", handIdx);
            error.put("error", errorText);
            output.add(error);
        }
    }
}
