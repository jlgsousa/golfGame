package src.game;

import src.game.pojo.*;

import java.util.*;

public class Game {

    private String[] namesDictionary = {
            "Antoine", "Antonio", "Abel", "Ahmed",
            "Bernardo", "Beto", "Barbara", "Beatriz",
            "Catarina", "Christine", "Charlie", "Chloe",
            "Daniel", "David", "Dakota", "Diogo"
    };

    public void init() {
        Scanner scan = new Scanner(System.in);

        String[] playerNames = generatePlayerNames();

        System.out.println("==>Welcome to the Sueca Game<==");
        System.out.println("What shall your name be?");

        playerNames[1] = scan.next();

        Table gameTable = new Table(playerNames);
        Team teamA = gameTable.getTeamA();
        Team teamB = gameTable.getTeamB();
        GameRound gameRound = gameTable.getGameRound();
        Card.Suit trunfo = Card.Suit.HEARTS;

        List<Player> players = gameTable.getPlayers();
        Player user = teamB.getPlayer1();

        System.out.println("You will be player 2!");

        while (gameTable.noTeamWon() ) {

            if (gameRound.isStartRound()) { // 1 11 21 31
                System.out.println(" ");
                System.out.println("=>New round<=");
                System.out.println(" ");

                gameTable.shuffleDeck();
                gameTable.cutDeck();
                gameTable.getDeck().setTrunfo();

                trunfo = gameTable.getDeck().getTrunfo();
                gameRound.getSortedNaipes().add(trunfo);

                gameTable.giveCards();

                System.out.println("Player " + players.get(0).getName() + " shuffled");
                System.out.println("Player " + players.get(2).getName() + " cut");
                System.out.println("Player " + players.get(3).getName() + " gave the cards");
            }

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Trunfo--> " + trunfo.getString() + " <<<<<<<<<<<<<<<<<<<");
            System.out.println("===============Cards over the table in the round " + gameRound.getRoundNumber()+ " =============");

            players.sort(Player::compareTo);

            for (Player player : players) {
                Card playedCard;
                if (isMyTurn(gameTable, player)) {
                    System.out.println("");
                    System.out.println("================Cards in your hand in the round " + gameRound.getRoundNumber() + " =============");
                    user.printHand();

                    playedCard = getCardToPlay(teamB.getPlayer1().getHand(), scan);
                    if (!gameRound.isFirstTurn()) {

                        Card.Suit naipeToAssist = gameRound.getFirstPlayedCard().getNaipe();
                        while (mustAssist(naipeToAssist, player.getHand()) && playedCard.getNaipe() != naipeToAssist ) {
                            System.out.println("If you have a card matching " + naipeToAssist.getString() + " you need to use it");
                            playedCard = getCardToPlay(teamB.getPlayer1().getHand(), scan);
                        }
                    }

                } else {
                    playedCard = player.play();
                }

                System.out.println(player.getName());
                playedCard.print();

                gameRound.getSortedNaipes().add(playedCard.getNaipe());

                gameRound.getPlayedCards().put(player, playedCard);
                player.getHand().remove(playedCard);
            }

            gameRound.showPlayedCards();

            Player winningPlayer = gameRound.endRound();

            gameTable.showAndSetRoundResults(gameRound.getRoundNumber(), winningPlayer);

            if (gameRound.isFinishRound()) { // end of one game
                gameTable.updateTeamsVictories();

                gameTable.showGameReport();

                if (teamB.getVictories() >= 4) {
                    gameTable.showFinalResult(true);
                } else if (teamA.getVictories() >= 4) {
                    gameTable.showFinalResult(false);
                }

                gameTable.resetTeamsPoints();
            }

            gameRound.getSortedNaipes().clear();
            gameRound.getPlayedCards().clear();
            gameTable.reorderPlayers(winningPlayer);

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

    private boolean isMyTurn(Table gameTable, Player player) {
        return gameTable.getTeamB().getPlayer1() == player;
    }

    private Card getCardToPlay(List<Card> hand, Scanner scan) {
        int cardNumber = 0;
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

    private boolean mustAssist(Card.Suit firstNaipe, List<Card> playerHand) {
        return playerHand.stream().anyMatch(card -> card.getNaipe() == firstNaipe);
    }
}
