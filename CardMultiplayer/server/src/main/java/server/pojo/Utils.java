package server.pojo;


import api.ICard;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public final class Utils {

    private Utils() {}

    public static ICard getLastCard(List<ICard> cards) {
        return cards.get(cards.size() - 1);
    }

    public static Optional<ICard> getLastCard(Stream<ICard> cards) {
        return cards.reduce((first, second) -> second);
    }

    public static <K, V> K getLastKey(Map<K, V> map) {
        K lastKey = null;
        for (Map.Entry<K, V> kvEntry : map.entrySet()) {
            lastKey = kvEntry.getKey();
        }

        return lastKey;
    }

    public static  <K,V> V getLastValue(Map<K, V> map) {
        V lastValue = null;
        for (Map.Entry<K, V> kvEntry : map.entrySet()) {
            lastValue = kvEntry.getValue();
        }

        return lastValue;
    }

    public static boolean isIntegerParseable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
