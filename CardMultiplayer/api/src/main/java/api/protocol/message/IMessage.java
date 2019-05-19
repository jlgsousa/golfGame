package api.protocol.message;

import api.ICard;

import java.util.List;

public interface IMessage {

    String getHeader();

    void setHeader(String header);

    String getMessage();

    void setMessage(String message);

    int getCardIndex();

    void setCardIndex(int cardIndex);

    List<ICard> getCardHand();

    void setCardHand(List<ICard> cardHand);
}
