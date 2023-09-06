import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
    private BlueDot blueDot;
    private GreenDot greenDot;
    private YellowDot yellowDot;
    private List<Dot> redDots;
    private Random rand;
    private Timer spawnTimer;
    private Timer dotLifeTimer;

    public GamePanel() {
        rand = new Random();

        blueDot = new BlueDot(new Point(rand.nextInt(800), rand.nextInt(600)), 10);
        greenDot = new GreenDot(new Point(rand.nextInt(800), rand.nextInt(600)), 10);
        //yellowDot = new YellowDot(new Point(rand.nextInt(800), rand.nextInt(600)), 10);
        redDots = new ArrayList<>();

        // Spawn 5 red dots initially
        for (int i = 0; i < 15; i++) {
            spawnRedDot();
        }

        // Set up a timer to spawn red dots every 1-3 seconds
        spawnTimer = new Timer(true);
        spawnTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spawnRedDot();
            }
        }, (rand.nextInt(1) + 1) * 1000, (rand.nextInt(3) + 1) * 1000);

        // Set up a timer to decrease dot's food points every second
        dotLifeTimer = new Timer(true);
        dotLifeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (blueDot != null) {
                    blueDot.setSize(blueDot.getSize() - 1);
                    if (blueDot.getSize() <= 3) {
                        blueDot = null;
                        //dotLifeTimer.cancel();
                    }
                }
                if (greenDot != null) {
                    greenDot.setSize(greenDot.getSize() - 1);
                    if (greenDot.getSize() <= 3) {
                        greenDot = null;
                    }
                }
            }
        }, 0, 1000);
    }

    private void spawnRedDot() {
        Dot newRedDot = new Dot(new Point(rand.nextInt(800), rand.nextInt(600)), 5, Color.RED);
        redDots.add(newRedDot);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (blueDot != null) {
            blueDot.draw(g2d);
        }

        if (greenDot != null) {
            greenDot.draw(g2d);
        }

        if (yellowDot != null) {
            yellowDot.draw(g2d);
        }

        for (Dot dot : redDots) {
            dot.draw(g2d);
        }
    }

    private void consumeRedDot(Dot consumerDot) {
        Dot redDotToConsume = null;

        for (Dot redDot : redDots) {
            double distance = consumerDot.getPosition().distance(redDot.getPosition());
            if (distance <= consumerDot.getSize() / 2 + redDot.getSize() / 2) {
                redDotToConsume = redDot;
                consumerDot.setSize(consumerDot.getSize() + 8);  // Increase size when eating red dot
                break;
            }
        }

        // Remove the consumed red dot
        if (redDotToConsume != null) {
            redDots.remove(redDotToConsume);
        }
    }

    // Handles interaction between any two dots
    public void handleDotInteraction(Dot dot1, Dot dot2) {
        double distance = dot1.getPosition().distance(dot2.getPosition());
        if (distance <= dot1.getSize() / 2 + dot2.getSize() / 2) {
            // Calculate 10% of each dot's size
            int dot1Deduction = (int) (0.10 * dot2.getSize());
            int dot2Deduction = (int) (0.10 * dot1.getSize());

            // Deduct from each dot's size
            dot1.setSize(dot1.getSize() - dot1Deduction);
            dot2.setSize(dot2.getSize() - dot2Deduction);

            // Determine which dot is smaller
            Dot smallerDot, largerDot;
            int smallerBounce, largerBounce;
            if (dot1.getSize() < dot2.getSize()) {
                smallerDot = dot1;
                largerDot = dot2;
                smallerBounce = 25;
                largerBounce = 15;
            } else {
                smallerDot = dot2;
                largerDot = dot1;
                smallerBounce = 25;
                largerBounce = 15;
            }

            // Determine the direction of the bounce
            double dx = smallerDot.getPosition().x - largerDot.getPosition().x;
            double dy = smallerDot.getPosition().y - largerDot.getPosition().y;
            double magnitude = Math.sqrt(dx * dx + dy * dy);

            // Normalize the direction vector
            dx /= magnitude;
            dy /= magnitude;

            // Apply the bounce effect
            smallerDot.getPosition().translate((int) (smallerBounce * dx), (int) (smallerBounce * dy));
            largerDot.getPosition().translate((int) (-largerBounce * dx), (int) (-largerBounce * dy));
        }
    }

    public void update() {
        // Blue and Green dot interaction
        if (blueDot != null && greenDot != null) {
            handleDotInteraction(blueDot, greenDot);
        }

        // Yellow and Blue dot interaction
        if (yellowDot != null && blueDot != null) {
            handleDotInteraction(yellowDot, blueDot);
        }

        // Yellow and Green dot interaction
        if (yellowDot != null && greenDot != null) {
            handleDotInteraction(yellowDot, greenDot);
        }

        // Blue dot and red dots interaction
        if (blueDot != null) {
            blueDot.moveTowardsTarget(redDots);
            consumeRedDot(blueDot);
        }

        // Green dot moves towards Red dots
        if (greenDot != null) {
            greenDot.moveTowardsTarget(redDots, blueDot, yellowDot);
            consumeRedDot(greenDot);
        }

        if (yellowDot != null) {
            yellowDot.moveTowardsTarget(redDots, greenDot, blueDot);
            consumeRedDot(yellowDot);
        }

        repaint();
    }
}
