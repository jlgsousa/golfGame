package src.calculations;

import src.entities.court.Ball;

public interface BallPhysicsCalculator {

    /**
     * Gets x position on next instant
     * @param ball
     * @return
     */
    double calculateHorizontalPosition(Ball ball);

    /**
     * Gets y position on next instant
     * @param ball
     * @return
     */
    double calculateVerticalPosition(Ball ball);

    /**
     * Gets x velocity on next instant
     * @param ball
     * @return
     */
    double calculateHorizontalVelocity(Ball ball);

    /**
     * Gets y velocity on next instant
     * @param ball
     * @return
     */
    double calculateVerticalVelocity(Ball ball);

    /**
     * Gets total velocity on next instant
     * @param ball
     * @return
     */
    double calculateVelocity(Ball ball);

    /**
     * Gets x acceleration on next instant
     * @param ball
     * @return
     */
    double calculateHorizontalAcceleration(Ball ball);

    /**
     * Gets y acceleration on next instant
     * @param ball
     * @return
     */
    double calculateVerticalAcceleration(Ball ball);

}
