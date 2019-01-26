package src.io;

import src.entities.court.Field;

public class Printer {

    public static void printIntro() {
        System.out.println("*****************************");
        System.out.println("*                           *");
        System.out.println("* Welcome to the golf game! *");
        System.out.println("*                           *");
        System.out.println("*****************************");
        System.out.println("\n\n");
    }

    public static void printBallAnimation(double vInitial, double angle) throws InterruptedException {
        int height = (int) (vInitial * Math.toRadians(angle));
        int horizontalWidth = 10;

        System.out.println("\\\\");
        System.out.println(" \\\\");
        System.out.println("  \\\\");
        System.out.println("    ___");
        System.out.print("    \\_/0--");

        System.out.println("");
        for (int i = horizontalWidth; i < 10 + horizontalWidth; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print(" ");
            }
            System.out.println("\\");
            Thread.sleep(100);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }

    }

    public static void printGameResult(Field field, double vInitial, double yMax, int iterations) {
        field.fieldPrint("          ");
        System.out.println("Conditions: Initial speed= " + vInitial + "m/s; Angle = " + field.getBall().getAngle() + "º");
        System.out.println(iterations + " iterations were made!");
        field.printResult();
        System.out.println("The ball reached the maximum height " + yMax + "meters");
    }
}
