package impl.protocol.message;

import api.ICard;
import api.protocol.message.IMessage;

import java.io.Serializable;
import java.util.List;

public class Message implements IMessage, Serializable {
    private String header;
    private String message;
    private int cardIndex;
    private List<ICard> cardHand;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public List<ICard> getCardHand() {
        return cardHand;
    }

    public void setCardHand(List<ICard> cardHand) {
        this.cardHand = cardHand;
    }
}
