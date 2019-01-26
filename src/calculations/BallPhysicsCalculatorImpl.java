package src.calculations;

import src.entities.court.Ball;

public class BallPhysicsCalculatorImpl implements BallPhysicsCalculator {

    /* x1 = x0 + vx0 * 0.01 */
    @Override
    public double calculateHorizontalPosition(Ball ball) {
        return ball.getPosition().getX0() + ball.getVelocity().getVx0() * 0.01;
    }

    /* y1 = y0 + vy0 * 0.01 */
    @Override
    public double calculateVerticalPosition(Ball ball) {
        return ball.getPosition().getY0() + ball.getVelocity().getVy0() * 0.01;
    }

    /* vx1 = vx0 + ax0 * 0.01 */
    @Override
    public double calculateHorizontalVelocity(Ball ball) {
        return ball.getVelocity().getVx0() + ball.getAcceleration().getAx0() * 0.01;
    }

    /* vy1 = vy0 + ay0 * 0.01 */
    @Override
    public double calculateVerticalVelocity(Ball ball) {
        return ball.getVelocity().getVy0() + ball.getAcceleration().getAy0() * 0.01;
    }

    /* v1 = sqrt( vx1^2 + vy1^2) */
    @Override
    public double calculateVelocity(Ball ball) {
        return Math.sqrt(Math.pow(ball.getVelocity().getVx1(), 2) + Math.pow(ball.getVelocity().getVy1(), 2));
    }

    /* ax1 = -0.0028 * v0 * vx1 */
    @Override
    public double calculateHorizontalAcceleration(Ball ball) {
        return -(2.8E-3) * ball.getVelocity().getV0() * ball.getVelocity().getVx1();
    }

    /* ay1 = -9.8 - 0.0028 * v0 * vy1 */
    @Override
    public double calculateVerticalAcceleration(Ball ball) {
        return -9.8 - (2.8E-3) * ball.getVelocity().getV0() * ball.getVelocity().getVy1();
    }
}
