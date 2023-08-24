import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;

public class Dot {
    protected Point position;
    protected int size;
    protected Color color;
    protected double speed;
    protected static final int SCREEN_WIDTH = 800;
    protected static final int SCREEN_HEIGHT = 600;

    public Dot(Point position, int size, Color color) {
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public Point getPosition() {
        return position;
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

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(position.x - size / 2, position.y - size / 2, size, size);
    }

    public void setPosition(Point newPosition) {
        this.position = newPosition;
    }
    

    // Other common methods for Dot can be added here
}
