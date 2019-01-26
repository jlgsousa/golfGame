package src.entities.court;

public class Field {
    private int lowest;
    private int highest;
    private Lake lake;
    private Hole hole;
    private Ball ball;

    public Field() {
        lowest = 0;
        highest = 400;
        lake = new Lake();
        hole = new Hole();
        ball = new Ball();
    }

    public int getLowest() {
        return lowest;
    }

    public void setLowest(int lowest) {
        this.lowest = lowest;
    }

    public int getHighest() {
        return highest;
    }

    public void setHighest(int highest) {
        this.highest = highest;
    }

    public Lake getLake() {
        return lake;
    }

    public void setLake(Lake lake) {
        this.lake = lake;
    }

    public Hole getHole() {
        return hole;
    }

    public void setHole(Hole hole) {
        this.hole = hole;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public boolean isValidLake() {
        return !exceedLeft(lake.getCenter(), lake.getRadius())
                && !exceedRight(lake.getCenter(), lake.getRadius());
    }

    private boolean exceedLeft(int center, int radius) {
        return center - radius < 1;
    }

    private boolean exceedRight(int center, int radius) {
        return center + radius > highest;
    }

    public boolean isValidHole() {
        return hole.getCenter() > (lake.getCenter() + lake.getRadius()) || hole.getCenter() < (lake.getCenter() - lake.getRadius());
    }

    public void fieldPrint(String offset) {
        System.out.print(offset);
        double ballHorizontalPosition = getRelativePosition(ball.getPosition().getX0());

        double lakeHorizontalCenter = getRelativePosition(lake.getCenter());
        double lakeRadius = getRelativePosition(lake.getRadius());
        double holeHorizontalPosition = getRelativePosition(hole.getCenter());

        int printPosition = 1;
        while (printPosition < 101) {
            if (printPosition == (lakeHorizontalCenter - lakeRadius)) {
                for (; printPosition <= (lakeHorizontalCenter + lakeRadius); printPosition += 1) {
                    System.out.print("~");
                }
            }

            if (printPosition == holeHorizontalPosition) {
                System.out.print("U");
            } else if (printPosition == ballHorizontalPosition) {
                if (printPosition != holeHorizontalPosition
                        || printPosition < (lakeHorizontalCenter - lakeRadius)
                        || printPosition > (lakeHorizontalCenter + lakeRadius)) {
                    System.out.print("0");
                }
                if (printPosition == holeHorizontalPosition) {
                    System.out.println("U");
                }
            } else if (printPosition == 100) {
                System.out.println(">");
            } else {
                System.out.print("=");
            }
            printPosition = printPosition + 1;
        }

        printPosition = 1;
        while (printPosition <= ballHorizontalPosition) {
            if (printPosition == ballHorizontalPosition) {
                System.out.println(offset + "^");
                for (int i = 1; i < printPosition; i++) {
                    System.out.print(" ");
                }
                System.out.println(offset + "|");
                for (int i = 1; i < printPosition; i++) {
                    System.out.print(" ");
                }
                System.out.println(offset + ball.getPosition().getX0() + " meters");
            } else {
                System.out.print(" ");
            }
            printPosition = printPosition + 1;
        }

        System.out.println(" ");
    }

    private double getRelativePosition(double rawValue) {
        return Math.round(100 * rawValue / this.highest);
    }

    public void printResult() {
        double distance = Math.abs(ball.getPosition().getX0() - hole.getCenter());
        double ballRelativePosition = getRelativePosition(ball.getPosition().getX0());
        double holeRelativePosition = getRelativePosition(hole.getCenter());
        double lakeRelativeRightLimit = getRelativePosition(lake.getCenter() + lake.getRadius());
        double lakeRelativeLeftLimit = getRelativePosition(lake.getCenter() - lake.getRadius());

        printAsterisks(99);

        if (ballRelativePosition == holeRelativePosition) {
            System.out.println("Congratulations!!! You've hit the hole");
        } else if (ballRelativePosition > 100) {
            System.out.println("Too much strength :/ The ball went out of the field");
        } else if (ballRelativePosition < holeRelativePosition && ballRelativePosition < (lake.getCenter() - lake.getRadius())
                || ballRelativePosition < holeRelativePosition && ballRelativePosition > (lake.getCenter() + lake.getRadius())) {
            System.out.println("Not enough strength it was " + distance + " meters left to the hole");
        } else if (ballRelativePosition <= lakeRelativeRightLimit && ballRelativePosition >= lakeRelativeLeftLimit) {
            System.out.println("You've lost the ball, it got into the lake :(");
        } else {
            System.out.println("Too much strength :/ it was " + distance + " meters far to the hole");
        }

        printAsterisks(99);
    }

    private void printAsterisks(int number) {
        for (int i = 0; i < number ; i++) {
            System.out.print("*");
        }
        System.out.println("");
    }
}
