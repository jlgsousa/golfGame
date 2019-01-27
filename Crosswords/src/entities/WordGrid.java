package src.entities;

import java.util.Random;

public class WordGrid {
    private int size;
    private char[][] wordPool;
    private String[] wordsArray;
    private char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'w', 'y', 'z'};
    Random rand;

    public WordGrid() {
        size = 10;
        wordPool = new char[size + 2][size + 2];
        rand = new Random();
    }

    public void prepareGrid(boolean isSolutionGrid) {
        for (int line = 0; line <= size + 1; line++) {
            for (int column = 0; column <= size + 1; column++) {
                if (column == 0 || column == size + 1) {
                    wordPool[line][column] = '|';
                } else if (line == 0 && column > 0 && column < size + 1) {
                    wordPool[line][column] = '=';
                } else if (line == size + 1 && column > 0 && column < size + 1) {
                    wordPool[line][column] = '=';
                } else {
                    if (isSolutionGrid) {
                        wordPool[line][column] = isSolutionGrid ? ' ' : alphabet[rand.nextInt(alphabet.length)];
                    }
                }
            }
        }
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        wordPool = new char[size + 2][size + 2];
    }

    public boolean isValidSize(int size) {
        return wordsArray != null && wordsArray[0].length() < size;
    }

    public char[][] getWordPool() {
        return wordPool;
    }

    public void setWordPool(char[][] wordPool) {
        this.wordPool = wordPool;
    }

    public String[] getWordsArray() {
        return wordsArray;
    }

    public void setWordsArray(String[] wordsArray) {
        this.wordsArray = wordsArray;
    }
}
