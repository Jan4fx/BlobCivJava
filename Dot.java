import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

public class Dot {
    private Point position;
    private int size;
    private Color color;
    private Dot target;  // Target dot (used by blue dot to track red dots)
    private static final Random rand = new Random();
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BASE_SPEED = 10;
    private static final double SPEED_DIVISOR = 25.0;



    public Dot(Point position, int size, Color color) {
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public Dot getTarget() {
        return this.target;
    }
    

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setTarget(Dot target) {
        this.target = target;
    }

    public void moveTowardsTarget() {
        if (target != null) {
            double dx = target.getPosition().x - position.x;
            double dy = target.getPosition().y - position.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
    
            // Determine speed based on the size of the dot
            double adjustedSpeed = BASE_SPEED / (1 + (size / SPEED_DIVISOR));
            
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
    
    

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(position.x - size / 2, position.y - size / 2, size, size);
    }
}
