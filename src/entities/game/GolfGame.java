package src.entities.game;

import src.calculations.BallPhysicsCalculator;
import src.calculations.BallPhysicsCalculatorImpl;
import src.entities.court.Field;
import src.entities.court.Hole;
import src.entities.court.Lake;
import src.entities.court.Ball;
import src.io.Printer;
import src.io.Reader;

import java.util.Scanner;

import static src.io.Printer.printBallAnimation;
import static src.io.Printer.printGameResult;
import static src.io.Printer.printIntro;

public class GolfGame {

    public void play() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        Reader reader = new Reader();

        Field field = new Field();

        Lake lake = field.getLake();
        Hole hole = field.getHole();
        Ball ball = field.getBall();

        printIntro();

        lake.setCenter(reader.readIntBetween("Introduce lake's center in meters: ",
                field.getLowest(), field.getHighest()));
        lake.setDiameter(reader.readIntBetween("Introduce lake's diameter in meters: ",
                0, lake.getLimit() / 2));

        while (!field.isValidLake()) {
            int maxDiameter = Math.max(lake.getCenter(), field.getHighest()-lake.getCenter()) * 2;
            System.out.println("Lake dimensions exceed the field :/ maximum diameter is " + maxDiameter);
            lake.setDiameter(reader.readIntBetween("Introduce lake's diameter in meters: ",
                    0, maxDiameter * 2));
        }

        hole.setCenter(reader.readIntBetween("Introduce hole's position: ",
                field.getLowest(), field.getHighest()));

        while (!field.isValidHole()) {
            int leftLimit = (lake.getCenter() - lake.getRadius());
            int rightLimit = (lake.getCenter() + lake.getRadius());
            System.out.println("The hole is inside the lake :/Introduce hole's position below " + leftLimit
                    + " or above " + rightLimit);
            hole.setCenter(reader.readIntBetween("Introduce hole's position below " + leftLimit + " or above "
                            + rightLimit, field.getLowest(), field.getHighest()));
        }

        ball.setAngle(reader.readDoubleBetween("Introduce degrees of shot angle: ",0, 90));

        double vInitial = reader.readDoubleBetween("Introduce initial ball's speed:", 0, 100);

        ball.setInitialConditions(vInitial);

        /*-- Main cycle --*/
        int iterations = iterateAndReturn(ball);
        /*----------------*/

        printBallAnimation(vInitial, ball.getAngle());
        printGameResult(field, vInitial, ball.getPosition().getyMax(), iterations);

        scan.close();
    }

    private int iterateAndReturn(Ball ball) {

        BallPhysicsCalculator calculator = new BallPhysicsCalculatorImpl();
        boolean maximumHeight = false;
        int iterations = 0;

        while (!isBallInTheGround(ball.getPosition().getY1())) {

            setBallInNextInstant(calculator, ball);

            if (isBallFalling(ball.getPosition().getY1(), ball.getPosition().getY0()) && !maximumHeight) {
                ball.getPosition().setyMax(ball.getPosition().getY0());
                maximumHeight = true;
            }

            timeShiftBall(ball);

            iterations++;
        }

        return iterations;
    }

    private boolean isBallInTheGround(double y) {
        return y <= 0;
    }

    private boolean isBallFalling(double vy1, double vy0) {
        return vy1 < vy0;
    }

    private void setBallInNextInstant(BallPhysicsCalculator calculator, Ball ball) {
        setBallNextPosition(calculator, ball);
        setBallNextVelocity(calculator, ball);
        setBallNextAcceleration(calculator, ball);
    }

    private void setBallNextPosition(BallPhysicsCalculator calculator, Ball ball) {
        ball.getPosition().setX1(calculator.calculateHorizontalPosition(ball));
        ball.getPosition().setY1(calculator.calculateVerticalPosition(ball));
    }

    private void setBallNextVelocity(BallPhysicsCalculator calculator, Ball ball) {
        ball.getVelocity().setVx1(calculator.calculateHorizontalVelocity(ball));
        ball.getVelocity().setVy1(calculator.calculateVerticalVelocity(ball));
        ball.getVelocity().setV1(calculator.calculateVelocity(ball));
    }

    private void setBallNextAcceleration(BallPhysicsCalculator calculator, Ball ball) {
        ball.getAcceleration().setAx1(calculator.calculateHorizontalAcceleration(ball));
        ball.getAcceleration().setAy1(calculator.calculateVerticalAcceleration(ball));
    }

    private void timeShiftBall(Ball ball) {
        ball.getPosition().setX0(ball.getPosition().getX1());
        ball.getPosition().setY0(ball.getPosition().getY1());
        ball.getVelocity().setVx0(ball.getVelocity().getVx1());
        ball.getVelocity().setVy0(ball.getVelocity().getVy1());
        ball.getAcceleration().setAx0(ball.getAcceleration().getAx1());
        ball.getAcceleration().setAy0(ball.getAcceleration().getAy1());

        ball.getVelocity().setV0(ball.getVelocity().getV1());
    }

}