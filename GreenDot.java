import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;

public class GreenDot extends Dot {
    private static final int BASE_SPEED = 10;
    private static final double SPEED_DIVISOR = 25.0;
    private static final double PROXIMITY_THRESHOLD = 50.0;
    private static final double BOOSTED_SPEED_MULTIPLIER = 2.0;
    private static final double BOOSTED_FOOD_LOSS_RATE = 2.5;

    public GreenDot(Point position, int size) {
        super(position, size, Color.GREEN);
        this.speed = BASE_SPEED;
    }

    private boolean isInProximityToBlueDot(Dot blueDot) {
        if (blueDot != null) {
            double distance = this.position.distance(blueDot.getPosition());
            return distance <= PROXIMITY_THRESHOLD;
        }
        return false;
    }

    public void moveTowardsTarget(Dot target) {
        if (target != null) {
            double dx = target.getPosition().x - position.x;
            double dy = target.getPosition().y - position.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
    
            // Adjust speed based on proximity to blue dot
            if (isInProximityToBlueDot(target)) {
                speed = BASE_SPEED * BOOSTED_SPEED_MULTIPLIER;
                this.size -= (int) (this.size * BOOSTED_FOOD_LOSS_RATE / 100.0);
            } else {
                speed = BASE_SPEED;
            }
            
            // Determine speed based on the size of the dot
            double adjustedSpeed = speed / (1 + (size / SPEED_DIVISOR));
            
            if (distance > 0) {
                int moveX = (int) (dx / distance * adjustedSpeed);  // Moving pixels towards target
                int moveY = (int) (dy / distance * adjustedSpeed);
    
                // Update position
                position.translate(moveX, moveY);
    
                // Ensure the dot stays within screen boundaries
                position.x = Math.min(Math.max(position.x, 0), SCREEN_WIDTH);
                position.y = Math.min(Math.max(position.y, 0), SCREEN_HEIGHT);
            }
        }
    }
}
