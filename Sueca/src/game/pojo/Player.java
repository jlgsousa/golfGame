package src.game.pojo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Player {

    private GameRound gameRound;
    private List<Card> hand;
    private Order order;
    private String name;

    public Player(String name, Order order, GameRound gameRound) {
        this.hand = new ArrayList<>();
        this.order = order;
        this.name = name;
        this.gameRound = gameRound;
    }

    public GameRound getGameRound() {
        return gameRound;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public Card play() {

        sortHandCards();

        return Utils.getLastCard(getHand());
    }

    private void sortHandCards() {
        getHand().sort((card1, card2) -> card1.compareTo(card2, getGameRound().getSortedNaipes()));
    }

    public int compareTo(Player player) {
        return Integer.compare(this.getOrder().getValue(), player.getOrder().value);
    }

    public void printHand() {
        int numberOfCards = this.getHand().size();
        StringBuilder cardNumber = new StringBuilder("");
        StringBuilder horizontalLine = new StringBuilder("");
        StringBuilder upperSymbol = new StringBuilder("");
        StringBuilder suits = new StringBuilder("");
        StringBuilder lowerSymbol = new StringBuilder("");

        for (int i = 0; i < numberOfCards; i++) {
            cardNumber.append("->").append((i+1)).append("<-  ");
            horizontalLine.append("|---|  ");
            upperSymbol.append("|").append(getHand().get(i).getSymbol().getString()).append("  |  ");
            suits.append("|").append(getHand().get(i).getNaipe().getString()).append(" ")
                    .append(getHand().get(i).getNaipe().getString()).append("|  ");
            lowerSymbol.append("|  ").append(getHand().get(i).getSymbol().getString()).append("|  ");

        }
        System.out.println(cardNumber);
        System.out.println(horizontalLine.toString());
        System.out.println(upperSymbol.toString());
        System.out.println(suits.toString());
        System.out.println(lowerSymbol.toString());
        System.out.println(horizontalLine.toString());
    }

    public enum Order {
        FIRST(1),
        SECOND(2),
        THIRD(3),
        FOURTH(4);

        private int value;

        public int getValue() {
            return value;
        }

        Order(int value) {
            this.value = value;
        }
    }

}
