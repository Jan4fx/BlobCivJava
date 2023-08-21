import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class BlueDot extends Dot {
    
    // Additional properties or methods unique to BlueDot can be added here if needed

    public BlueDot(Point position, int size) {
        super(position, size, Color.BLUE);
    }

    // If you want any specific behaviors for the blue dot, you can override them here.
    // For instance, if you wanted the blue dot to have a different movement pattern 
    // compared to a generic dot, you can override the moveTowardsTarget() method.

    @Override
    public void moveTowardsTarget() {
        // Example: unique movement code for BlueDot, or just call super to use the parent's behavior
        super.moveTowardsTarget();
    }
    
    // Any additional methods specific to BlueDot can be added below
}
