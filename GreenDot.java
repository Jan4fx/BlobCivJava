import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.List;

public class GreenDot extends Dot {
    private double speed;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BASE_SPEED = 10;
    private static final double SPEED_DIVISOR = 25.0;
    private static final double BOOSTED_FOOD_LOSS_RATE = 2.5;
    private long proximityStartTime = 0;
    private static final long PROXIMITY_DURATION = 3000;  // 3 seconds in milliseconds

    public GreenDot(Point position, int size) {
        super(position, size, Color.GREEN);
        this.speed = BASE_SPEED;
    }

    private boolean isInProximityToBlueDot(Dot blueDot) {
        if (blueDot != null) {
            double distance = this.getPosition().distance(blueDot.getPosition());
            return distance <= 50;
        }
        return false;
    }

    public void moveTowardsTarget(List<Dot> redDots, Dot blueDot) {
        if (redDots != null && !redDots.isEmpty()) {
            Dot closestRedDot = redDots.get(0);
            double minDistance = getPosition().distance(closestRedDot.getPosition());
        
            // Find the closest red dot
            for (Dot redDot : redDots) {
                double currentDistance = getPosition().distance(redDot.getPosition());
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    closestRedDot = redDot;
                }
            }

            double dx = closestRedDot.getPosition().x - getPosition().x;
            double dy = closestRedDot.getPosition().y - getPosition().y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (isInProximityToBlueDot(blueDot)) {
                if (proximityStartTime == 0) {  // Start the timer
                    proximityStartTime = System.currentTimeMillis();
                }

                long elapsedTime = System.currentTimeMillis() - proximityStartTime;

                if (elapsedTime <= PROXIMITY_DURATION) {
                    speed = BASE_SPEED * 3;
                    setSize(getSize() - (int) (getSize() * (BOOSTED_FOOD_LOSS_RATE * 2) / 100.0));  // Double the food burn rate
                } else {
                    if (getPosition().distance(blueDot.getPosition()) > 50) {
                        speed = BASE_SPEED;
                        proximityStartTime = 0;  // Reset the timer
                    }
                }
            } else {
                speed = BASE_SPEED;
                proximityStartTime = 0;  // Reset the timer if out of proximity
            }

            double adjustedSpeed = Math.max(BASE_SPEED / 3, speed / (1 + (getSize() / SPEED_DIVISOR)));

        
            if (distance > 0) {
                int moveX = (int) (dx / distance * adjustedSpeed);
                int moveY = (int) (dy / distance * adjustedSpeed);

                Point newPosition = new Point(getPosition().x + moveX, getPosition().y + moveY);

                // Ensure the dot stays within screen boundaries
                newPosition.x = Math.min(Math.max(newPosition.x, 0), SCREEN_WIDTH);
                newPosition.y = Math.min(Math.max(newPosition.y, 0), SCREEN_HEIGHT);

                setPosition(newPosition);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        g.fillOval(getPosition().x - getSize() / 2, getPosition().y - getSize() / 2, getSize(), getSize());
    }
}
