package src.entities;

import java.util.Random;

public class WordGrid {
    private int size;
    private char[][] wordPool;
    private String[] wordsArray;
    private static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'w', 'y', 'z'};
    private Random rand;

    WordGrid() {
        size = 10;
        wordPool = new char[size + 2][size + 2];
        rand = new Random();
    }

    public void firstWordInTheGrid() {
        int line = rand.nextInt(size) + 1;
        int column = rand.nextInt(size) + 1;

        while (isColumnNotValid(column)) {
            column = rand.nextInt(size) + 1;
        }

        for (int x = 0; x < wordsArray[0].length(); x++) {
            wordPool[line][column + x] = wordsArray[0].charAt(x);
        }
    }

    boolean isColumnNotValid(int column) {
        return column + wordsArray[0].length() > size;
    }

    public int getSize(){
        return size;
    }

    protected void setSize(int size) {
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

    public char getRandomLetter() {
        return alphabet[rand.nextInt(alphabet.length)];
    }
}
