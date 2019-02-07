package src;

import src.entities.Crossword;
import src.entities.WordGrid;
import src.entities.WordIterable;
import src.io.Printer;
import src.io.Reader;

public class Game {
    public static void main(String[] args) {
        Printer.printIntro();

        Reader reader = new Reader();
        String[] sortedWordsArray = reader.getSortedWordsArray();

        Crossword crossword = new Crossword();
        WordGrid gameGrid = crossword.getGameGrid();
        gameGrid.setWordsArray(sortedWordsArray);

        WordGrid solutionGrid = crossword.getSolutionGrid();
        solutionGrid.setWordsArray(sortedWordsArray);

        int gridSize = reader.getValidGridSizeFor(gameGrid);

        crossword.setGridsSize(gridSize);
        crossword.prepareGrids();
        int[] indexArray = crossword.putFirstWordInGrids();

        WordIterable word = new WordIterable();
        word.setLine(indexArray[0]);
        word.setColumn(indexArray[1]);

        while (word.getNumber() < gameGrid.getWordsArray().length - 1) {
            word = crossword.verticalWordPlacement(word);
            word = crossword.horizontalWordPlacement(word);
        }

        Printer.printCrosswordGame(gameGrid, solutionGrid);

    }
}