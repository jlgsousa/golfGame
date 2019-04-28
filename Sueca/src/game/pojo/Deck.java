package src.game.pojo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private Card[] cards;

    public Card[] getCards() {
        return cards;
    }

    public Card.Suit getTrunfo() {
        return cards[39].getNaipe();
    }

    public void setTrunfo() {
        Card lastCard = cards[39];
        for (Card card : cards) {
            card.setIsTrunfo(card.getNaipe() == lastCard.getNaipe());
        }
    }

    public Deck() {
        cards = new Card[40];
        int cardIndex = 0;

        for (Card.Symbol symbol : Card.Symbol.values()) {
            cards[cardIndex] = new Card(Card.Suit.DIAMONDS, symbol);
            cards[++cardIndex] = new Card(Card.Suit.HEARTS, symbol);
            cards[++cardIndex] = new Card(Card.Suit.SPADES, symbol);
            cards[++cardIndex] = new Card(Card.Suit.CLUBS, symbol);
            cardIndex++;
        }
    }

    public void shuffle() {
        List<Card> cardsList = Arrays.asList(getCards());
        Collections.shuffle(cardsList);

        cards = cardsList.toArray(new Card[0]);
    }

    public void manualShuffle() {
        Random rand = new Random();
        Card tempCard;
        for (int i = 0; i < cards.length; i++) {
            int randomIndex = rand.nextInt(cards.length);
            tempCard = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = tempCard;
        }
    }

    public void cut() {
        Random rand = new Random();
        int cutIndex = rand.nextInt(36) + 2;
        Card[] newDeck = new Card[40];

        System.arraycopy(cards, cutIndex, newDeck, 0, cards.length - cutIndex);
        System.arraycopy(cards, 0, newDeck, (cards.length - cutIndex), cutIndex);

        cards = newDeck;
    }
}
