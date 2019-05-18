package cardmultiplayer.protocol;

import cardmultiplayer.api.ICard;

import java.io.Serializable;
import java.util.List;

public interface IMessage extends Serializable {

    String getHeader();
    String getMessage();
    int getPlayedCard() throws Exception;
    List<ICard> getCardHand() throws Exception;
}
