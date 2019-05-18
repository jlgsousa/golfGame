package cardmultiplayer.client;

import cardmultiplayer.api.ICard;
import cardmultiplayer.protocol.IMessage;

import java.util.List;

class ClientMessage implements IMessage {
    private String header;
    private String message;
    private int cardIndex;

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getPlayedCard() {
        return cardIndex;
    }

    @Override
    public List<ICard> getCardHand() throws Exception {
        throw new Exception("Client message does not possess list of cards");
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
}
