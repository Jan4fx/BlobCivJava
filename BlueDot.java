import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;

public class BlueDot extends Dot {
    private static final int BASE_SPEED = 10;
    private static final double SPEED_DIVISOR = 25.0;

    public BlueDot(Point position, int size) {
        super(position, size, Color.BLUE);
        this.speed = BASE_SPEED;
    }

    public void moveTowardsTarget(Dot target) {
        if (target != null) {
            double dx = target.getPosition().x - position.x;
            double dy = target.getPosition().y - position.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            
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
