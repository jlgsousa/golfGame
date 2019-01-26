package src.entities.court;

public class Ball {
    private double angle;
    private Position position;
    private Velocity velocity;
    private Acceleration acceleration;

    Ball() {
        position = new Position();
        velocity = new Velocity();
        acceleration = new Acceleration();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public Acceleration getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    public void setInitialConditions(double initialSpeed) {
        position.setX0(0);
        position.setY0(0);
        position.setY1(1);
        velocity.setVx0(initialSpeed * Math.cos(Math.toRadians(angle)));
        velocity.setVy0(initialSpeed * Math.sin(Math.toRadians(angle)));
        velocity.setV0(initialSpeed);
        acceleration.setAx0(0);
        acceleration.setAy0(0);
    }

    public class Position {
        private double x0;
        private double x1;
        private double y0;
        private double y1;
        private double yMax;

        public double getX0() {
            return x0;
        }

        public void setX0(double x0) {
            this.x0 = x0;
        }

        public double getX1() {
            return x1;
        }

        public void setX1(double x1) {
            this.x1 = x1;
        }

        public double getY0() {
            return y0;
        }

        public void setY0(double y0) {
            this.y0 = y0;
        }

        public double getY1() {
            return y1;
        }

        public void setY1(double y1) {
            this.y1 = y1;
        }

        public double getyMax() {
            return yMax;
        }

        public void setyMax(double yMax) {
            this.yMax = yMax;
        }
    }

    public class Velocity {
        private double v0;
        private double vx0;
        private double vx1;
        private double vy0;
        private double vy1;
        private double v1;

        public double getVx0() {
            return vx0;
        }

        public void setVx0(double vx0) {
            this.vx0 = vx0;
        }

        public double getVx1() {
            return vx1;
        }

        public void setVx1(double vx1) {
            this.vx1 = vx1;
        }

        public double getVy0() {
            return vy0;
        }

        public void setVy0(double vy0) {
            this.vy0 = vy0;
        }

        public double getVy1() {
            return vy1;
        }

        public void setVy1(double vy1) {
            this.vy1 = vy1;
        }

        public double getV0() {
            return v0;
        }

        public void setV0(double v0) {
            this.v0 = v0;
        }

        public double getV1() {
            return v1;
        }

        public void setV1(double v1) {
            this.v1 = v1;
        }
    }

    public class Acceleration {
        private double ax0;
        private double ax1;
        private double ay0;
        private double ay1;

        public double getAx0() {
            return ax0;
        }

        public void setAx0(double ax0) {
            this.ax0 = ax0;
        }

        public double getAx1() {
            return ax1;
        }

        public void setAx1(double ax1) {
            this.ax1 = ax1;
        }

        public double getAy0() {
            return ay0;
        }

        public void setAy0(double ay0) {
            this.ay0 = ay0;
        }

        public double getAy1() {
            return ay1;
        }

        public void setAy1(double ay1) {
            this.ay1 = ay1;
        }
    }
}
