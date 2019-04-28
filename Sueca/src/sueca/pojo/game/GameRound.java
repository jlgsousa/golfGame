package src.sueca.pojo.game;

import src.sueca.pojo.card.Card;
import src.sueca.pojo.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class GameRound {
    private LinkedHashMap<Player, Card> playedCards;
    private List<Card.Suit> sortedNaipes;
    private int totalPoints;
    private int roundNumber;

    Map<Player, Card> getPlayedCards() {
        return playedCards;
    }

    public List<Card.Suit> getSortedNaipes() {
        return sortedNaipes;
    }

    int getTotalPoints() {
        return totalPoints;
    }

    GameRound() {
        playedCards = new LinkedHashMap<>();
        sortedNaipes = new ArrayList<>();
        roundNumber = 1;
    }

    int getRoundNumber() {
        return roundNumber;
    }

    void incrementRoundNumber() {
        this.roundNumber++;
    }

    Player endRound() {

        totalPoints = 0;
        for (Map.Entry<Player, Card> entry : playedCards.entrySet()) {
            totalPoints += entry.getValue().getSymbol().getPoints();
        }

        playedCards = playedCards.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> o1.compareTo(o2, sortedNaipes)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Player winningPlayer = null;
        for (Map.Entry<Player, Card> entry : playedCards.entrySet()) {
            totalPoints += entry.getValue().getSymbol().getPoints();
            winningPlayer = entry.getKey();
        }

        return winningPlayer;
    }

    boolean isFirstTurn() {
        return playedCards.isEmpty();
    }

    Card getFirstPlayedCard() {
        Iterator<Map.Entry<Player, Card>> iterator = playedCards.entrySet().iterator();
        return iterator.next().getValue();
    }

    void showPlayedCards() {
        List<Card> cards = new ArrayList<>(playedCards.values());
        List<Player> players = new ArrayList<>(playedCards.keySet());

        System.out.println("================Played Cards ===================");
        showPairOfCards(cards.get(0), cards.get(1));
        showPairOfPlayers(players.get(0), players.get(1));
        System.out.println();
        showPairOfCards(cards.get(2), cards.get(3));
        showPairOfPlayers(players.get(2), players.get(3));

    }

    private void showPairOfCards(Card card1, Card card2) {
        System.out.println("|---|                         |---|");
        System.out.println("|" + card1.getSymbol().getString() + "  |                         "
                + "|"+card2.getSymbol().getString() + "  |");
        System.out.println("|" + card1.getNaipe().getString() + " " + card1.getNaipe().getString()+"|                         "
                + "|" + card2.getNaipe().getString() + " " + card2.getNaipe().getString()+"|");
        System.out.println("|  " + card1.getSymbol().getString() + "|                         "
                + "|  "+card2.getSymbol().getString() + "|");
        System.out.println("|---|                         |---|");
    }

    private void showPairOfPlayers(Player player1, Player player2) {
        System.out.println("Played by");
        System.out.println("                              Played by");
        System.out.println(player1.getName());
        System.out.println("                              "+player2.getName());
    }

    boolean isStartRound() {
        return (roundNumber - 1) % 10 == 0;
    }

    boolean isFinishRound() {
        return roundNumber % 10 == 0;
    }

    void resetGameRound() {
        getSortedNaipes().clear();
        getPlayedCards().clear();
    }
}
