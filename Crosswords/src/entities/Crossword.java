package src.entities;

import java.util.Random;

public class Crossword {
    private WordGrid gameGrid;
    private WordGrid solutionGrid;
    private Random random;

    public Crossword() {
        gameGrid = new WordGrid();
        solutionGrid = new WordGrid();
        random = new Random();
    }

    public void setGridsSize(int size) {
        gameGrid.setSize(size);
        solutionGrid.setSize(size);
    }

    public void prepareGrids() {
        int gridSize = gameGrid.getSize() + 1;
        for (int line = 0; line <= gridSize; line++) {
            for (int column = 0; column <= gridSize; column++) {
                if (column == 0 || column == gridSize) {
                    setCharInGrids(line, column, '|');
                } else if (line == 0 && column > 0 && column < gridSize) {
                    setCharInGrids(line, column, '=');
                } else if (line == gridSize && column > 0 && column < gridSize) {
                    setCharInGrids(line, column, '=');
                } else {
                    setCharInGameGrid(line, column, gameGrid.getRandomLetter());
                    setCharInSolutionGrid(line, column, ' ');
                }
            }
        }
    }

    private void setCharInGameGrid(int line, int column, char c) {
        gameGrid.getWordPool()[line][column] = c;
    }

    private void setCharInSolutionGrid(int line, int column, char c) {
        solutionGrid.getWordPool()[line][column] = c;
    }

    private void setCharInGrids(int line, int column, char c) {
        gameGrid.getWordPool()[line][column] = solutionGrid.getWordPool()[line][column] = c;
    }

    public int[] putFirstWordInGrids() {
        int line = new Random().nextInt(gameGrid.getSize()) + 1;
        int column = new Random().nextInt(gameGrid.getSize()) + 1;

        while (gameGrid.isColumnNotValid(column)) {
            column = new Random().nextInt(gameGrid.getSize()) + 1;
        }

        for (int x = 0; x < gameGrid.getWordsArray()[0].length(); x++) {
            setCharInGrids(line, column + x, gameGrid.getWordsArray()[0].charAt(x));
        }

        return new int[]{line, column};
    }

    public WordIterable verticalWordPlacement(WordIterable wordIterable) {
        int line = wordIterable.getLine();
        int column = wordIterable.getColumn();
        int index = wordIterable.getNumber();

        String wordToPlace = gameGrid.getWordsArray()[index];
        String wordToCheck = gameGrid.getWordsArray()[index + 1];
        for (int indexWordToPlace = 0; indexWordToPlace < wordToPlace.length(); indexWordToPlace++) {
            for (int indexCharToCheck = 0; indexCharToCheck < wordToCheck.length(); indexCharToCheck++) {
                if (wordToPlace.charAt(indexWordToPlace) == wordToCheck.charAt(indexCharToCheck)) {
                    if (wordFitsInGameGrid(wordToCheck, indexCharToCheck, line)) {
                        if (isVerticalSpaceInSolutionAvailable(wordToCheck, line, column, indexCharToCheck, indexWordToPlace)) {
                            for (int y = 0; y < wordToCheck.length(); y++) {
                                solutionGrid.getWordPool()[line - indexCharToCheck + y][column + indexWordToPlace] = wordToCheck.charAt(y);
                                gameGrid.getWordPool()[line - indexCharToCheck + y][column + indexWordToPlace] = wordToCheck.charAt(y);
                            }
                            wordIterable.setLine(wordIterable.getLine() - indexCharToCheck);
                            wordIterable.setColumn(wordIterable.getColumn() + indexWordToPlace);
                            wordIterable.setNumber(++index);
                            return wordIterable;

                        }
                    }
                } else if (isLastChar(wordToPlace, indexWordToPlace) && isLastChar(wordToCheck, indexCharToCheck)) {
                    boolean isAvailable = false;
                    int counter = 0;
                    while (line + wordToCheck.length() - 1 > gameGrid.getSize() || !isAvailable) {
                        column = random.nextInt(gameGrid.getSize()) + 1;
                        line = random.nextInt(gameGrid.getSize()) + 1;
                        isAvailable = isSpaceInSolutionAvailableLine(wordToCheck, line, column);
                        counter += 1;

                        if (counter == 100) {
                            System.out.println("Not possible to introduce word + "+ wordToCheck);
                            System.out.println("It will not show in the grids");
                            wordIterable.setNumber(++index);
                            return wordIterable;
                        }
                    }
                    for (int i = 0; i < wordToCheck.length(); i++) {
                        solutionGrid.getWordPool()[line + i][column] = gameGrid.getWordsArray()[index + 1].charAt(i);
                        gameGrid.getWordPool()[line + i][column] = gameGrid.getWordsArray()[index + 1].charAt(i);
                    }
                    wordIterable.setLine(line);
                    wordIterable.setColumn(column);
                }
            }
        }
        wordIterable.setNumber(++index);
        return wordIterable;
    }

    private boolean wordFitsInGameGrid(String wordToCheck, int indexCharToCheck, int placementPosition) {
        return placementPosition - indexCharToCheck >= 1 && placementPosition + wordToCheck.length() - (indexCharToCheck + 1) <= gameGrid.getSize();
    }

    private boolean isVerticalSpaceInSolutionAvailable(String wordToCheck, int placementLine, int placementColumn, int indexCharToCheck, int indexWordToPlace) {
        boolean isAvailable = true;
        for (int empty = 0; empty < wordToCheck.length(); empty++) {
            if (solutionGrid.getWordPool()[placementLine - indexCharToCheck + empty][placementColumn + indexWordToPlace] != ' '
                    && indexCharToCheck != empty) {
                isAvailable = false;
                break;
            }
        }
        return isAvailable;
    }

    private boolean isHorizontalSpaceInSolutionAvailable(String wordToCheck, int placementLine, int placementColumn, int indexCharToCheck, int indexWordToPlace) {
        boolean isAvailable = true;
        for (int empty = 0; empty < wordToCheck.length(); empty++) {
            if (solutionGrid.getWordPool()[placementLine + indexWordToPlace][placementColumn - indexCharToCheck + empty] != ' '
                    && indexCharToCheck != empty) {
                isAvailable = false;
                break;
            }
        }
        return isAvailable;
    }

    private boolean isSpaceInSolutionAvailableLine(String wordToCheck, int line, int column) {
        boolean isAvailable = true;
        if (line + wordToCheck.length() - 1 <= gameGrid.getSize()) {
            for (int empty = 0; empty < wordToCheck.length(); empty++) {
                if (solutionGrid.getWordPool()[line + empty][column] != ' ') {
                    isAvailable = false;
                    break;
                }
            }
        }
        return isAvailable;
    }

    public WordIterable horizontalWordPlacement(WordIterable wordIterable) {
        int line = wordIterable.getLine();
        int column = wordIterable.getColumn();
        int index = wordIterable.getNumber();

        String wordToPlace = gameGrid.getWordsArray()[index];
        String wordToCheck = gameGrid.getWordsArray()[index + 1];
        for (int indexWordToPlace = 0; indexWordToPlace < wordToPlace.length(); indexWordToPlace++) {
            for (int indexCharToCheck = 0; indexCharToCheck < wordToCheck.length(); indexCharToCheck++) {
                if (wordToPlace.charAt(indexWordToPlace) == wordToCheck.charAt(indexCharToCheck)) {
                    if (wordFitsInGameGrid(wordToCheck, indexCharToCheck, column)) {
                        if (isHorizontalSpaceInSolutionAvailable(wordToCheck, line, column, indexCharToCheck, indexWordToPlace)) {
                            for (int x = 0; x < wordToCheck.length(); x++) {
                                solutionGrid.getWordPool()[line + indexWordToPlace][column - indexCharToCheck + x] = wordToCheck.charAt(x);
                                gameGrid.getWordPool()[line + indexWordToPlace][column - indexCharToCheck + x] = wordToCheck.charAt(x);
                            }
                            wordIterable.setLine(wordIterable.getLine() + indexWordToPlace);
                            wordIterable.setColumn(wordIterable.getColumn() - indexCharToCheck);
                            wordIterable.setNumber(++index);
                            return wordIterable;
                        }
                    }
                } else if (isLastChar(wordToPlace, indexWordToPlace) && isLastChar(wordToCheck, indexCharToCheck)) {
                    boolean isAvailable = false;
                    int counter = 0;
                    while (column + wordToCheck.length() - 1 > gameGrid.getSize() || !isAvailable) {
                        column = random.nextInt(gameGrid.getSize()) + 1;
                        line = random.nextInt(gameGrid.getSize()) + 1;
                        isAvailable = isSpaceInSolutionAvailableColumn(wordToCheck, line, column, index);
                        counter++;
                        if (counter == 100) {
                            System.out.println("Not possible to introduce word + "+ wordToCheck);
                            System.out.println("It will not show in the grids");
                            wordIterable.setNumber(++index);
                            return wordIterable;
                        }
                    }
                    for (int i = 0; i < wordToCheck.length(); i++) {
                        solutionGrid.getWordPool()[line][column + i] = wordToCheck.charAt(i);
                        gameGrid.getWordPool()[line][column + i] = wordToCheck.charAt(i);
                    }
                    wordIterable.setLine(line);
                    wordIterable.setColumn(column);
                }
            }
        }
        wordIterable.setNumber(++index);
        return wordIterable;
    }

    private boolean isSpaceInSolutionAvailableColumn(String wordToCheck, int line, int column, int index) {
        boolean isAvailable = true;
        if (column + wordToCheck.length() - 1 <= gameGrid.getSize()) {
            for (int vazio = 0; vazio < wordToCheck.length(); vazio++) {
                if (solutionGrid.getWordPool()[line][column + vazio] != ' ') {
                    isAvailable = false;
                    break;
                }
            }
        }

        return isAvailable;
    }

    private boolean isLastChar(String word, int charIndex) {
        return charIndex == word.length() - 1;
    }

    public WordGrid getGameGrid() {
        return gameGrid;
    }

    public void setGameGrid(WordGrid gameGrid) {
        this.gameGrid = gameGrid;
    }

    public WordGrid getSolutionGrid() {
        return solutionGrid;
    }

    public void setSolutionGrid(WordGrid solutionGrid) {
        this.solutionGrid = solutionGrid;
    }
}
