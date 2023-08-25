import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.awt.Graphics2D;

public class BlueDot extends Dot {
    private static final int BASE_SPEED = 15;
    private static final double SPEED_DIVISOR = 25.0;

    public BlueDot(Point position, int size) {
        super(position, 15, Color.BLUE);
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

            //double adjustedSpeed = Math.max(BASE_SPEED / 3, speed / (1 + (getSize() / SPEED_DIVISOR)));
            //double adjustedSpeed = Math.max(10 / 3, 10 / (1+ (getSize() / 25)));
            //double adjustedSpeed = ((15) / (1 + (getSize() / SPEED_DIVISOR)));
            double adjustedSpeed = BASE_SPEED / (1 + (getSize() / SPEED_DIVISOR));
            if(adjustedSpeed < 5){
                adjustedSpeed = 5;
            }

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
