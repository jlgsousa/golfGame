package src.io;

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
        boolean caract = true;
        boolean flag = false;
        System.out.print("Palavras que quer na grelha de jogo: ");
        String palavras1 = scan.nextLine();
        palavras1 = palavras1.toLowerCase();
        String[] palavras = palavras1.split(" ");

        while (!flag) {
            flag = true;
            for (int palavra = 0; palavra < palavras.length; palavra++) {
                for (int i = 0; i < palavras[palavra].length(); i++) {
                    caract = Character.isLetter(palavras[palavra].charAt(i));
                    if (!caract) {
                        flag = false;
                        System.out.println("Caracter invalido introduzido ");
                        System.out.print("Palavras que quer na grelha de jogo: ");
                        palavras1 = scan.nextLine();
                        palavras1 = palavras1.toLowerCase();
                        palavras = palavras1.split(" ");
                    }
                }
            }
        }

        return palavras;
    }

    public String[] sortWordsBySize(String[] words) {
        return wordSorter.sortWordsBySize(words);
    }

    public int getGridSize() {
        // Dimensao
        int size;
        System.out.print("Introduce the grid's size: ");
        while(!scan.hasNextInt() || (size = scan.nextInt()) <= 0) {
            System.out.println("Please insert a positive number");
            scan.next();
        }
        return size;
    }


}
