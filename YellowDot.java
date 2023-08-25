import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Random;

//Has issues finding the blue or green dot when it is feeling aggressive, like it doesn't predict where opponent will be along the path

public class YellowDot extends Dot {
    private static final int BASE_SPEED = 15;
    private static final double SPEED_DIVISOR = 25.0;
    private static final Random rand = new Random();
    private static final int CHECK_CHANCE = 30;

    public YellowDot(Point position, int size) {
        super(position, 15, Color.YELLOW);
    }

    public void moveTowardsTarget(List<Dot> redDots, Dot greenDot, Dot blueDot) {
        Dot closestDot = null;
        Dot smallerDot = null;
        Dot targetDot = null;

        if (greenDot != null && blueDot != null) {
            double distanceToGreen = getPosition().distance(greenDot.getPosition());
            double distanceToBlue = getPosition().distance(blueDot.getPosition());
            closestDot = distanceToGreen < distanceToBlue ? greenDot : blueDot;
        } else if (greenDot != null) {
            closestDot = greenDot;
        } else if (blueDot != null) {
            closestDot = blueDot;
        }

        if (closestDot != null) {
            if (getSize() > closestDot.getSize()) {
                int randomNum = rand.nextInt(CHECK_CHANCE);
                if (randomNum < 3) {
                    targetDot = closestDot;
                }
            } else {
                smallerDot = closestDot;
            }
        }

        double adjustedSpeed = BASE_SPEED / (1 + (getSize() / SPEED_DIVISOR));
        if (adjustedSpeed < 5) {
            adjustedSpeed = 5;
        }

        // Only double the speed and burn size by 2.5 if the target is not a red (food) dot
        if (targetDot != null && targetDot.getColor() != Color.RED) {
            adjustedSpeed *= 2;
            setSize((int)(getSize() / 2.5));  // Decrease size to signify burning more "food"
        }

        if (targetDot != null) {
            moveInDirection(targetDot.getPosition(), adjustedSpeed);
        } else if (smallerDot != null) {
            if (redDots != null && !redDots.isEmpty()) {
                Dot closestRedDot = redDots.get(0);
                double minDistance = getPosition().distance(closestRedDot.getPosition());

                for (Dot redDot : redDots) {
                    double currentDistance = getPosition().distance(redDot.getPosition());
                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;
                        closestRedDot = redDot;
                    }
                }
                moveInDirection(closestRedDot.getPosition(), adjustedSpeed);
            }
        }
    }

    private void moveInDirection(Point target, double adjustedSpeed) {
        double dx = target.x - getPosition().x;
        double dy = target.y - getPosition().y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            int moveX = (int) (dx / distance * adjustedSpeed);
            int moveY = (int) (dy / distance * adjustedSpeed);
            getPosition().translate(moveX, moveY);
        }
    }
}
