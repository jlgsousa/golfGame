package src;

import src.entities.WordGrid;
import src.io.Reader;

import java.util.Random;
import java.util.Scanner;

public class Projetofinal2 {
    public static void main(String[] args) {
        Reader reader = new Reader();
        Random rand = new Random();

        WordGrid gameGrid = new WordGrid();
        WordGrid solutionGrid = new WordGrid();

        String[] sortedWordsArray = reader.sortWordsBySize(reader.getWordsArray());
        gameGrid.setWordsArray(sortedWordsArray);
        solutionGrid.setWordsArray(sortedWordsArray);

        int gridSize = reader.getGridSize();

        while (!gameGrid.isValidSize(gridSize)) {
            System.out.println("One or more words are bigger than the grid size :/");
            gridSize = reader.getGridSize();
        }

        gameGrid.setSize(gridSize);
        gameGrid.setSize(gridSize);

        gameGrid.prepareGrid(false);
        solutionGrid.prepareGrid(true);

        // primeira palavra
        int linha = rand.nextInt(gridSize) + 1;
        int coluna = rand.nextInt(gridSize) + 1;
        // se nao couber
        int contador = 0;
        while (coluna + wordsArray[0].length() - 1 > gridSize) { // -1 porque a primeira letra ja ocupa uma posicao
            coluna = rand.nextInt(gridSize) + 1;
            contador += 1;
            if (contador == 100) {
                System.out.println("Nao foi possivel introduzir a palavra");
                System.exit(1);
            }
        }
        for (int x = 0; x < wordsArray[0].length(); x++) {
            solutionGrid[linha][coluna + x] = wordsArray[0].charAt(x); // por a primeira palavra
            gameGrid[linha][coluna + x] = wordsArray[0].charAt(x);
        }
        boolean casoraro = false;
        // //////////////////////////////////BIG WHILE///////////////////////////////////////
        // /////////////////// ////////////////////
        int contadorparidade = 0;
        outerloop:
        while (contadorparidade < wordsArray.length - 1) {

            teste:
            if (contadorparidade % 2 == 0) {
                // //////////////////////////////////// palavra vertical/////////////////////////////////////////////
                for (int q = 0; q < wordsArray[contadorparidade].length(); q++) {
                    for (int w = 0; w < wordsArray[contadorparidade + 1].length(); w++) { // relembrar que w da o numero de letras pa tras
                        if (wordsArray[contadorparidade].charAt(q) == wordsArray[contadorparidade + 1].charAt(w) && !casoraro) {
                            if (linha - w >= 1 && linha + wordsArray[contadorparidade + 1].length() - (w + 1) <= gridSize) { // cabe em cima e em baixo

                                boolean flagy = true;
                                boolean mark = true;
                                for (int vazio = 0; vazio < wordsArray[contadorparidade + 1].length(); vazio++) { // ver se nao ha letras naquelas posicoes

                                    if (vazio == w) { // letra comum
                                        mark = true;
                                    } else if (solutionGrid[linha - w + vazio][coluna + q] != ' ') { // se nao tiver vazio nao da para por la
                                        mark = false;
                                        flagy = false;
                                    }

                                }
                                if (flagy) {
                                    for (int y = 0; y < wordsArray[contadorparidade + 1].length(); y++) {
                                        solutionGrid[linha - w + y][coluna + q] = wordsArray[contadorparidade + 1].charAt(y); // por a segunda palavra
                                        gameGrid[linha - w + y][coluna + q] = wordsArray[contadorparidade + 1].charAt(y);

                                    }
                                    linha = linha - w; // definir posicao primeira letra palavra seguinte
                                    coluna = coluna + q;
                                    contadorparidade += 1;
                                    if (contadorparidade == wordsArray.length - 1) {
                                        break outerloop;
                                    } else {
                                        break teste;
                                    }

                                } else if (!flagy && q == wordsArray[contadorparidade].length() - 1 && w == wordsArray[contadorparidade + 1].length() - 1) {
                                    casoraro = true;
                                    break teste;
                                }
                            } else if (q == wordsArray[contadorparidade].length() - 1 && w == wordsArray[contadorparidade + 1].length() - 1) {
                                casoraro = true;
                                break teste;
                            }
                        } else if (q == wordsArray[contadorparidade].length() - 1 && w == wordsArray[contadorparidade + 1].length() - 1) { // caso corra todas as letras e nao encontre igualdade

                            // se nao couber
                            boolean mark = false;
                            contador = 0;
                            while (linha + wordsArray[contadorparidade + 1].length() - 1 > gridSize || !mark) { // -1 porque a primeira letra ja ocupa uma posicao
                                coluna = rand.nextInt(gridSize) + 1;
                                linha = rand.nextInt(gridSize) + 1;
                                mark = true;
                                // ver se ta vazio
                                if (linha + wordsArray[contadorparidade + 1].length() - 1 <= gridSize) {
                                    for (int vazio = 0; vazio < wordsArray[contadorparidade + 1].length(); vazio++) { // ver se nao ha letras naquelas posicoes

                                        if (solutionGrid[linha + vazio][coluna] != ' ') { // se nao tiver vazio nao da para por la
                                            mark = false;

                                        }
                                    }
                                } else if (linha + wordsArray[contadorparidade + 1].length() - 1 > gridSize) {
                                    mark = false;
                                }
                                contador += 1;
                                if (contador == 100) {
                                    System.out.println("Nao foi possivel introduzir a palavra " + wordsArray[contadorparidade + 1]);
                                    System.out.println("Esta palavra nao aparecera em ambas as grelhas");
                                    contadorparidade += 1; // proxima palavra
                                    break teste;
                                }
                            }
                            for (int i = 0; i < wordsArray[contadorparidade + 1].length(); i++) {
                                solutionGrid[linha + i][coluna] = wordsArray[contadorparidade + 1].charAt(i);
                                gameGrid[linha + i][coluna] = wordsArray[contadorparidade + 1].charAt(i);


                            }
                            contadorparidade += 1;
                            if (contadorparidade == wordsArray.length - 1) {
                                break outerloop;
                            } else {
                                break teste;
                            }
                        }
                    }// for 2
                }// for 1
            }

            teste:
            if (contadorparidade % 2 != 0) {
                // ///////////////////////////////// palavra horizontal /////////////////////////////////////

                for (int q = 0; q < wordsArray[contadorparidade].length(); q++) {
                    for (int w = 0; w < wordsArray[contadorparidade + 1].length(); w++) { // relembrar que w da o numero de letras pa tras

                        if (wordsArray[contadorparidade].charAt(q) == wordsArray[contadorparidade + 1].charAt(w) && !casoraro) {
                            if (coluna - w >= 1 && coluna + wordsArray[contadorparidade + 1].length() - (w + 1) <= gridSize) { // cabe em cima e em baixo
                                boolean flagy = true;
                                boolean mark = true;
                                for (int vazio = 0; vazio < wordsArray[contadorparidade + 1].length(); vazio++) { // ver se nao ha letras naquelas posicoes

                                    if (vazio == w) { // letra comum
                                        mark = true;
                                    } else if (solutionGrid[linha + q][coluna - w + vazio] != ' ') { // se nao tiver vazio nao da para por la
                                        mark = false;
                                        flagy = false;
                                    }
                                }
                                if (flagy) {

                                    for (int i = 0; i < wordsArray[contadorparidade + 1].length(); i++) {
                                        solutionGrid[linha + q][coluna - w + i] = wordsArray[contadorparidade + 1].charAt(i);
                                        gameGrid[linha + q][coluna - w + i] = wordsArray[contadorparidade + 1].charAt(i);

                                    }
                                    linha = linha + q;
                                    coluna = coluna - w;
                                    contadorparidade += 1;
                                    if (contadorparidade == wordsArray.length - 1) {
                                        break outerloop;
                                    } else {
                                        break teste;
                                    }
                                } else if (!flagy && q == wordsArray[contadorparidade].length() - 1 && w == wordsArray[contadorparidade + 1].length() - 1) {
                                    casoraro = true;
                                    break teste;
                                }
                            } else if (q == wordsArray[contadorparidade].length() - 1 && w == wordsArray[contadorparidade + 1].length() - 1) {
                                casoraro = true;
                                break teste;
                            }
                        } else if (q == wordsArray[contadorparidade].length() - 1 && w == wordsArray[contadorparidade + 1].length() - 1) { // caso corra todas as letras e nao encontre igualdade

                            boolean mark = false;
                            contador = 0;
                            while (coluna + wordsArray[contadorparidade + 1].length() - 1 > gridSize || !mark) { // -1 porque a primeira letra ja ocupa uma posicao
                                coluna = rand.nextInt(gridSize) + 1;
                                linha = rand.nextInt(gridSize) + 1;
                                mark = true;

                                // ver se ta vazio
                                if (coluna + wordsArray[contadorparidade + 1].length() - 1 <= gridSize) {
                                    for (int vazio = 0; vazio < wordsArray[contadorparidade + 1].length(); vazio++) { // ver se nao ha letras naquelas posicoes

                                        if (solutionGrid[linha][coluna + vazio] != ' ') { // se nao tiver vazio nao da para por la
                                            mark = false;
                                        }
                                    }
                                } else if (coluna + wordsArray[contadorparidade + 1].length() - 1 > gridSize) {
                                    mark = false;
                                }
                                contador += 1;
                                if (contador == 100) {
                                    System.out.println("Nao foi possivel introduzir a palavra " + wordsArray[contadorparidade + 1]);
                                    System.out.println("Esta palavra nao aparecera em ambas as grelhas");
                                    contadorparidade += 1; //ira partir para a proxima palavra
                                    break teste;

                                }

                            }
                            for (int i = 0; i < wordsArray[contadorparidade + 1].length(); i++) {
                                solutionGrid[linha][coluna + i] = wordsArray[contadorparidade + 1].charAt(i);
                                gameGrid[linha][coluna + i] = wordsArray[contadorparidade + 1].charAt(i);
                            }

                            contadorparidade += 1;
                            if (contadorparidade == wordsArray.length - 1) {
                                break outerloop;
                            } else {
                                break teste;
                            }
                        }
                    }
                }
            }
        }// end of while
        System.out.println(" ");
        System.out.println("Solu��o: ");
        // Print grelha solucao
        for (coluna = 0; coluna < gridSize + 2; coluna++) {
            System.out.print(solutionGrid[0][coluna]);
        }
        for (linha = 1; linha < gridSize + 2; linha++) {
            System.out.println(" ");
            for (coluna = 0; coluna < gridSize + 2; coluna++) {
                System.out.print(solutionGrid[linha][coluna]);
            }
        }
        System.out.println(" ");
        System.out.println("Grelha de jogo: ");
        // Print grelha de jogo
        for (linha = 0; linha < gridSize + 2; linha++) {
            System.out.println(" ");
            for (coluna = 0; coluna < gridSize + 2; coluna++) {
                System.out.print(gameGrid[linha][coluna]);
            }
        }
    }
}