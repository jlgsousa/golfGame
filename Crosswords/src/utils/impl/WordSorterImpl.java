package src.utils.impl;

import src.utils.api.WordSorter;

public class WordSorterImpl implements WordSorter {
    
    @Override
    public String[] sortWordsBySize(String[] words) {

        //Bubble sort
        boolean marcador = true;
        String cena;
        while (marcador) {
            marcador = false;
            for (int x = 0; x < words.length - 1; x++) {
                if (words[x].length() < words[x + 1].length()) {
                    cena = words[x];
                    words[x] = words[x + 1];
                    words[x + 1] = cena;
                    marcador = true;
                }
            }
        }
        
        return words;
    }
}
