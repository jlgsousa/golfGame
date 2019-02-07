package src.io;

import src.entities.WordGrid;

import java.util.Scanner;

public class Printer {

    public static void printIntro() {
        System.out.println("***********************\n" +
                "*\t\tWelcome\t\t  *\n" +
                "*\t\t   to\t\t  *\n" +
                "*\t\t  the\t\t  *\n" +
                "*\t   crossword\t  *\n" +
                "*\t\t  game\t\t  *\n" +
                "***********************\n\n");
    }

    private static void printGrid(WordGrid wordGrid) {
        for (int line = 0; line < wordGrid.getSize() + 2; line++) {
            System.out.println(" ");
            for (int column = 0; column < wordGrid.getSize() + 2; column++) {
                System.out.print(wordGrid.getWordPool()[line][column]);
            }
        }
    }

    private static void printGameGrid(WordGrid gameGrid) {
        System.out.println(" ");
        System.out.println("Game Grid: ");
        printGrid(gameGrid);
    }

    private static void printSolutionGrid(WordGrid solutionGrid) {
        System.out.println(" ");
        System.out.println("Solution: ");
        printGrid(solutionGrid);
        System.out.println("\n");
    }

    public static void printCrosswordGame(WordGrid gameGrid, WordGrid solutionGrid) {
        Printer.printGameGrid(gameGrid);
        long start = System.nanoTime();
        System.out.println("\n\nReady?");
        new Scanner(System.in).nextLine();
        long elapsedTime = System.nanoTime() - start;
        Printer.printSolutionGrid(solutionGrid);
        Printer.printGameTime(elapsedTime);
    }

    public static void printGameTime(long duration) {
        duration /= 1000000000;
        if (duration < 5) {
            System.out.println("You took " + duration + " seconds that was quite fast");
        } else if (duration >= 5 && duration < 10) {
            System.out.println("You took " + duration + " seconds not bad");
        } else {
            System.out.println("You took " + duration + " seconds, you suck");
        }
    }
}
