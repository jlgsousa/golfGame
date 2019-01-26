package src.entities.court;


public class Lake {
    private int center;
    private int radius;
    private int diameter;
    private int limit;

    Lake() {
        center = 500;
        radius = 500;
        diameter = 500;
        limit = 50;
    }

    public int getCenter() {
        return center;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
        this.radius = diameter / 2;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
