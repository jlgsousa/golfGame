package src.sueca.pojo.game;

import src.sueca.pojo.*;
import src.sueca.pojo.card.Card;
import src.sueca.pojo.player.Player;
import src.sueca.pojo.player.Team;

import java.util.*;

public class Game {

    private Scanner scan;
    private Table gameTable;

    private String[] namesDictionary = {
            "Antoine", "Antonio", "Abel", "Ahmed",
            "Bernardo", "Beto", "Barbara", "Beatriz",
            "Catarina", "Christine", "Charlie", "Chloe",
            "Daniel", "David", "Dakota", "Diogo"
    };

    public void init() {
        scan = new Scanner(System.in);

        String[] playerNames = generatePlayerNames();

        System.out.println("==>Welcome to the Sueca Game<==");
        System.out.println("What shall your name be?");

        playerNames[1] = scan.next();

        gameTable = new Table(playerNames);
        GameRound gameRound = gameTable.getGameRound();

        List<Player> players = gameTable.getPlayers();

        System.out.println("You will be player 2!");

        while (gameTable.noTeamWon() ) {

            if (gameRound.isStartRound()) {
                printNewRound();

                gameTable.shuffleDeck();
                gameTable.cutDeck();
                gameTable.getDeck().setTrunfo();
                gameTable.giveCards();

                gameRound.getSortedNaipes().add(gameTable.getDeck().getTrunfo());

                printRoles(players);
            }

            printTrunfo();
            System.out.println("===============Cards over the table in the round " + gameRound.getRoundNumber()+ " =============");

            players.sort(Player::compareTo);

            for (Player player : players) {
                playCard(player);
            }

            gameRound.showPlayedCards();

            Player winningPlayer = gameRound.endRound();
            Team winningTeam = gameTable.getWinningTeam(winningPlayer);

            gameTable.showRoundResults(winningTeam, winningPlayer, gameRound.getRoundNumber());

            winningTeam.setPoints(winningTeam.getPoints() + gameRound.getTotalPoints());

            if (gameRound.isFinishRound()) { // end of one sueca
                gameTable.updateTeamsVictories();

                gameTable.showGameReport();

                gameTable.showFinalResult();

                gameTable.resetTeamsPoints();
            }

            gameTable.reorderPlayers(winningPlayer);

            gameRound.resetGameRound();
            gameRound.incrementRoundNumber();
        }

        scan.close();
    }

    private String[] generatePlayerNames() {
        Random rand = new Random();
        return new String[]{namesDictionary[rand.nextInt(namesDictionary.length)],
                "user name will fit here",
                namesDictionary[rand.nextInt(namesDictionary.length)],
                namesDictionary[rand.nextInt(namesDictionary.length)]};
    }

    private void printNewRound() {
        System.out.println(" ");
        System.out.println("=>New round<=");
        System.out.println(" ");
    }

    private void printRoles(List<Player> players) {
        System.out.println("Player " + players.get(0).getName() + " shuffled");
        System.out.println("Player " + players.get(2).getName() + " cut");
        System.out.println("Player " + players.get(3).getName() + " gave the cards");
    }

    private void printTrunfo() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Trunfo--> " + gameTable.getDeck().getTrunfo().getString() + " <<<<<<<<<<<<<<<<<<<");
    }

    private void playCard(Player player) {

        Card playedCard;
        if (isMyTurn(gameTable, player)) {
            printHandHeader();
            player.printHand();
            playedCard = getUserPlayedCard(player);
        } else {
            playedCard = player.play();
        }

        System.out.println(player.getName());
        playedCard.print();

        gameTable.getGameRound().getSortedNaipes().add(playedCard.getNaipe());

        gameTable.getGameRound().getPlayedCards().put(player, playedCard);
        player.getHand().remove(playedCard);
    }

    private boolean isMyTurn(Table gameTable, Player player) {
        return gameTable.getTeamB().getPlayer1() == player;
    }

    private void printHandHeader() {
        System.out.println();
        System.out.println("================Cards in your hand in the round " + gameTable.getGameRound().getRoundNumber() + " =============");
    }

    private Card getUserPlayedCard(Player player) {
        Card playedCard = getCardToPlay(player.getHand());
        if (!gameTable.getGameRound().isFirstTurn()) {
            Card.Suit naipeToAssist = gameTable.getGameRound().getFirstPlayedCard().getNaipe();

            while (isPlayerCheating(naipeToAssist, player.getHand(), playedCard) ) {
                System.out.println("If you have a card matching " + naipeToAssist.getString() + " you need to use it");
                playedCard = getCardToPlay(player.getHand());
            }
        }
        return playedCard;
    }

    private boolean isPlayerCheating(Card.Suit naipeToAssist, List<Card> playerHand, Card playedCard) {
        return mustAssist(naipeToAssist, playerHand) && playedCard.getNaipe() != naipeToAssist;
    }

    private boolean mustAssist(Card.Suit firstPlayedNaipe, List<Card> playerHand) {
        return playerHand.stream().anyMatch(card -> card.getNaipe() == firstPlayedNaipe);
    }

    private Card getCardToPlay(List<Card> hand) {
        int cardNumber;
        String input;

        while (true) {
            System.out.print("Which card to play? ");
            System.out.println("Introduce a number between 1 and " + hand.size());
            input = scan.next();

            if (!Utils.isIntegerParseable(input)) {
                continue;
            }

            cardNumber = Integer.parseInt(input) - 1;
            if (cardNumber >= 0 && cardNumber < hand.size()) {
                break;
            }
            scan.reset();
        }

        return hand.get(cardNumber);
    }
}
