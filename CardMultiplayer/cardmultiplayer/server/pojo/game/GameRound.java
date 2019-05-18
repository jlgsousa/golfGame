package cardmultiplayer.server.pojo.game;


import cardmultiplayer.api.ICard;
import cardmultiplayer.server.pojo.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class GameRound {
    private LinkedHashMap<Player, ICard> playedCards;
    private List<ICard.ISuit> sortedNaipes;
    private int totalPoints;
    private int roundNumber;

    public Map<Player, ICard> getPlayedCards() {
        return playedCards;
    }

    public List<ICard.ISuit> getSortedNaipes() {
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
        for (Map.Entry<Player, ICard> entry : playedCards.entrySet()) {
            totalPoints += entry.getValue().getSymbol().getPoints();
        }

        playedCards = playedCards.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> o1.compareTo(o2, sortedNaipes)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Player winningPlayer = null;
        for (Map.Entry<Player, ICard> entry : playedCards.entrySet()) {
            totalPoints += entry.getValue().getSymbol().getPoints();
            winningPlayer = entry.getKey();
        }

        return winningPlayer;
    }

    boolean isFirstTurn() {
        return playedCards.isEmpty();
    }

    ICard getFirstPlayedCard() {
        Iterator<Map.Entry<Player, ICard>> iterator = playedCards.entrySet().iterator();
        return iterator.next().getValue();
    }

    public String getStringOfPlayedCards() {
        String playedCardsString = "";

        List<ICard> cards = new ArrayList<>(playedCards.values());
        List<Player> players = new ArrayList<>(playedCards.keySet());

        playedCardsString+= ("\n================Played Cards ===================\n");
        playedCardsString+= getPairOfCardsString(cards.get(0), cards.get(1));
        playedCardsString+= getPairOfPlayersString(players.get(0), players.get(1));
        System.out.println();
        playedCardsString+= getPairOfCardsString(cards.get(2), cards.get(3));
        playedCardsString+= getPairOfPlayersString(players.get(2), players.get(3));

        return playedCardsString;
    }

    private String getPairOfCardsString(ICard card1, ICard card2) {
        String pairOfCards = "";
        pairOfCards+= ("|---|                         |---|\n");
        pairOfCards+= ("|" + card1.getSymbol().getString() + "  |                         "
                + "|"+card2.getSymbol().getString() + "  |\n");
        pairOfCards+= ("|" + card1.getNaipe().getString() + " " + card1.getNaipe().getString()+"|                         "
                + "|" + card2.getNaipe().getString() + " " + card2.getNaipe().getString()+"|\n");
        pairOfCards+= ("|  " + card1.getSymbol().getString() + "|                         "
                + "|  "+card2.getSymbol().getString() + "|\n");
        pairOfCards+= ("|---|                         |---|\n");

        return pairOfCards;
    }

    private String getPairOfPlayersString(Player player1, Player player2) {
        String pairOfPlayers = "";
        pairOfPlayers+= ("Played by\n");
        pairOfPlayers+= ("                              Played by\n");
        pairOfPlayers+= (player1.getName()+"\n");
        pairOfPlayers+= ("                              "+player2.getName()+"\n");

        return pairOfPlayers;
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
