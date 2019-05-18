package cardmultiplayer.server.pojo.game;

import cardmultiplayer.api.ICard;
import cardmultiplayer.server.pojo.player.Team;
import cardmultiplayer.server.pojo.Utils;
import cardmultiplayer.server.pojo.player.Player;

import java.io.IOException;
import java.util.*;

import static cardmultiplayer.server.SuecaServer.sendToAll;

public class Game {

    public static Table gameTable;
    public static GameRound gameRound;

    private Scanner scan;

    public Game() {
        this.scan = new Scanner(System.in);
    }

    public void start(List<String> playerNames) throws IOException {
        gameTable = new Table(playerNames.toArray(new String[playerNames.size()]));
        gameRound = gameTable.getGameRound();

        String roles = "Player " + playerNames.get(0) + " shuffled\n" +
                "Player " + playerNames.get(2) + " cut\n" +
                "Player " + playerNames.get(3) + " gave the cards\n";

        sendToAll(roles);
    }

    public void setDeck() throws IOException {
        sendToAll("\n=>New round<=\n");

        gameTable.shuffleDeck();
        gameTable.cutDeck();
        gameTable.getDeck().setTrunfo();
        gameTable.giveCards();

        gameRound.getSortedNaipes().add(gameTable.getDeck().getTrunfo());
    }

    public List<Player> getSortedPlayers() {
        gameTable.getPlayers().sort(Player::compareTo);

        return gameTable.getPlayers();
    }

    public void finishRound() throws IOException {

        sendToAll(gameTable.getGameRound().getStringOfPlayedCards());

        Player winningPlayer = gameRound.endRound();
        Team winningTeam = gameTable.getWinningTeam(winningPlayer);

        sendToAll(gameTable.getRoundResultString(winningTeam, winningPlayer, gameRound.getRoundNumber()));

        winningTeam.setPoints(winningTeam.getPoints() + gameRound.getTotalPoints());

        if (gameRound.isFinishRound()) { // end of one sueca
            gameTable.updateTeamsVictories();

            sendToAll(gameTable.getGameReportString());

            sendToAll(gameTable.getFinalResultString());

            gameTable.resetTeamsPoints();
        }

        gameTable.reorderPlayers(winningPlayer);

        gameRound.resetGameRound();
        gameRound.incrementRoundNumber();
    }

    private String getTrunfoString() {
        return (">>>>>>>>>>>>>>>>>>>>>>>>>>Trunfo--> " + gameTable.getDeck().getTrunfo().getString() + " <<<<<<<<<<<<<<<<<<<\n");
    }

    private boolean isMyTurn(Table gameTable, Player player) {
        return gameTable.getTeamB().getPlayer1() == player;
    }

    private String getHandHeaderString() {
        return ("================Cards in your hand in the round " + gameTable.getGameRound().getRoundNumber() + " =============\n");
    }

    public static boolean isPlayedCardValid(Player player, ICard playedCard) {
        if (!gameTable.getGameRound().isFirstTurn()) {
            ICard.ISuit naipeToAssist = gameTable.getGameRound().getFirstPlayedCard().getNaipe();
            return !isPlayerCheating(naipeToAssist, player.getHand(), playedCard);
        }
        return true;
    }

    private static boolean isPlayerCheating(ICard.ISuit naipeToAssist, List<ICard> playerHand, ICard playedCard) {
        return mustAssist(naipeToAssist, playerHand) && playedCard.getNaipe() != naipeToAssist;
    }

    private static boolean mustAssist(ICard.ISuit firstPlayedNaipe, List<ICard> playerHand) {
        return playerHand.stream().anyMatch(card -> card.getNaipe() == firstPlayedNaipe);
    }
}
