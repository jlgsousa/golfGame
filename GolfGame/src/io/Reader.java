package src.io;

import java.util.Scanner;

public class Reader {
    private Scanner scan;
    private int positiveInt;
    private double positiveDouble;

    public Reader() {
        scan = new Scanner(System.in);
        positiveInt = 0;
        positiveDouble = 0.0;
    }

    public int readIntBetween(String message, int lowest, int highest) {
        System.out.println(message);
        while (!scan.hasNextInt() || (positiveInt=scan.nextInt()) <= lowest || positiveInt >= highest) {
            System.out.println("Input is not a number between " + lowest + " and " + highest + "\n" + message);
            scan.next();
        }
        return positiveInt;
    }

    public double readDoubleBetween(String message, int lowest, int highest) {
        System.out.println(message);
        while (!scan.hasNextDouble() || (positiveDouble=scan.nextDouble()) < lowest || positiveDouble > highest) {
            System.out.println("Input is not a number between " + lowest + " and " + highest + "\n" + message);
            scan.next();
        }
        return positiveDouble;
    }
}
