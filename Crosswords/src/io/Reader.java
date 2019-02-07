package src.io;

import src.entities.WordGrid;
import src.utils.api.WordSorter;
import src.utils.impl.WordSorterImpl;
import java.util.Scanner;

public class Reader {
    private Scanner scan;
    private WordSorter wordSorter;

    public Reader() {
        scan = new Scanner(System.in);
        wordSorter = new WordSorterImpl();
    }


    public String[] getWordsArray() {
        boolean flag = false;
        System.out.print("Please insert an odd number of words for the game grid: ");
        String word = scan.nextLine();
        word = word.toLowerCase();
        String[] wordArray = word.split(" ");

        while(wordArray.length % 2 == 0) {
            System.out.print("Please insert an odd number of words for the game grid: ");
            word = scan.nextLine();
            word = word.toLowerCase();
            wordArray = word.split(" ");
        }

        while (!flag) {
            flag = true;

            outerloop:
            for (int wordIndex = 0; wordIndex < wordArray.length; wordIndex++) {
                for (int i = 0; i < wordArray[wordIndex].length(); i++) {
                    if (!Character.isLetter(wordArray[wordIndex].charAt(i))) {
                        flag = false;
                        System.out.println("Invalid character " + wordArray[wordIndex].charAt(i));
                        System.out.print("Please insert an odd number of wanted words in the game grid: ");
                        word = scan.nextLine();
                        word = word.toLowerCase();
                        wordArray = word.split(" ");

                        break outerloop;
                    }
                }
            }
        }

        return wordArray;
    }

    public String[] getSortedWordsArray() {
        return wordSorter.sortWordsBySize(getWordsArray());
    }

    public int getGridSize() {
        System.out.print("Introduce the grid's size: ");
        int size = scan.nextInt();
        while(size <= 0) {
            System.out.println("Please insert a positive number");
            size = scan.nextInt();
        }
        return size;
    }

    public int getValidGridSizeFor(WordGrid wordGrid) {
        int validSize = getGridSize();
        while (!wordGrid.isValidSize(validSize)) {
            System.out.println("One or more words are bigger than the grid size :/");
            validSize = getGridSize();
        }
        return validSize;
    }

}
