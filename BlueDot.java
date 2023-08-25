import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.List;

public class BlueDot extends Dot {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BASE_SPEED = 15;
    private static final double SPEED_DIVISOR = 25.0;

    public BlueDot(Point position, int size) {
        super(position, size, Color.BLUE);
    }

    public void moveTowardsTarget(List<Dot> redDots) {
        Point targetPosition;

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
            targetPosition = closestRedDot.getPosition();
        } else {
            //if no food go to this point on the screen
            targetPosition = new Point(400, 400);
        }

        double dx = targetPosition.x - getPosition().x;
        double dy = targetPosition.y - getPosition().y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double adjustedSpeed = BASE_SPEED / (1 + (getSize() / SPEED_DIVISOR));

        if (adjustedSpeed < 5) {
            adjustedSpeed = 5;
        }

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

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        g.fillOval(getPosition().x - getSize() / 2, getPosition().y - getSize() / 2, getSize(), getSize());
    }
}
