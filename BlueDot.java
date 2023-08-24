import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.awt.Graphics2D;

public class BlueDot extends Dot {
    private static final int BASE_SPEED = 10;
    private static final double SPEED_DIVISOR = 25.0;

    public BlueDot(Point position, int size) {
        super(position, size, Color.BLUE);
        this.speed = BASE_SPEED;
    }

    public void moveTowardsTarget(List<Dot> redDots) {
        if (redDots != null && !redDots.isEmpty()) {
            Dot closestRedDot = redDots.get(0);
            double minDistance = position.distance(closestRedDot.getPosition());
            
            // Find the closest red dot
            for (Dot redDot : redDots) {
                double currentDistance = position.distance(redDot.getPosition());
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    closestRedDot = redDot;
                }
            }

            double dx = closestRedDot.getPosition().x - position.x;
            double dy = closestRedDot.getPosition().y - position.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            double adjustedSpeed = speed / (1 + (size / SPEED_DIVISOR));
            
            if (distance > 0) {
                int moveX = (int) (dx / distance * adjustedSpeed);
                int moveY = (int) (dy / distance * adjustedSpeed);

                position.translate(moveX, moveY);

                position.x = Math.min(Math.max(position.x, 0), SCREEN_WIDTH);
                position.y = Math.min(Math.max(position.y, 0), SCREEN_HEIGHT);
            }
        }
    }

}
