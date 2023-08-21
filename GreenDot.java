public class GreenDot extends Dot {
    private static final double PROXIMITY_THRESHOLD = 50.0; // Proximity value in pixels for special ability
    private static final double ACCELERATION_FACTOR = 2.0;
    private static final double FOOD_LOSS_FACTOR = 2.5;

    public GreenDot(Point position, int size, Color color) {
        super(position, size, color);
    }

    public void checkAndAccelerate(Dot blueDot) {
        double distanceToBlueDot = getPosition().distance(blueDot.getPosition());
        if (distanceToBlueDot <= PROXIMITY_THRESHOLD) {
            // Accelerate
            setSpeedFactor(ACCELERATION_FACTOR);
            setSize((int) (getSize() - FOOD_LOSS_FACTOR));
        } else {
            setSpeedFactor(1.0); // Reset speed to normal
        }
    }

    private void setSpeedFactor(double factor) {
        // Adjust base speed of the green dot using the factor
        // This method assumes that the Dot class has a method to adjust its base speed
        setBaseSpeed((int) (BASE_SPEED * factor));
    }
}
