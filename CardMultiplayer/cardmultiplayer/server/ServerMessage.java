package cardmultiplayer.server;

import cardmultiplayer.api.ICard;
import cardmultiplayer.protocol.IMessage;

import java.util.List;

public class ServerMessage implements IMessage {
    private String header;
    private String message;
    private List<ICard> cardHand;

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getPlayedCard() throws Exception {
        throw new Exception("Client message does not possess a card");
    }

    @Override
    public List<ICard> getCardHand() {
        return cardHand;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCardHand(List<ICard> cardHand) {
        this.cardHand = cardHand;
    }
}
