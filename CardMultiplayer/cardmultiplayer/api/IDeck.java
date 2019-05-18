package cardmultiplayer.api;

public interface IDeck {

    ICard[] getCards();

    ICard.ISuit getTrunfo();

    void shuffle();

    void manualShuffle();

    void cut();
}
