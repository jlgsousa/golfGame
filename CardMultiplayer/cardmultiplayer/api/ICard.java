package cardmultiplayer.api;

import java.util.List;

public interface ICard {
    ISymbol getSymbol();

    ISuit getNaipe();

    boolean isTrunfo();

    void print();

    int compareTo(ICard card, List<ISuit> sortedNaipes);

    String toString();

    interface ISuit {
        String getString();
    }

    interface ISymbol {
        String getString();

        int getPoints();

        int getNumber();
    }
}
