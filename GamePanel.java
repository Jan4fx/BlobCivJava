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
    private List<Dot> redDots;
    private Random rand;
    private Timer spawnTimer;
    private Timer blueDotLifeTimer;

    public GamePanel() {
        rand = new Random();

        blueDot = new BlueDot(new Point(rand.nextInt(800), rand.nextInt(600)), 10);
        greenDot = new GreenDot(new Point(rand.nextInt(800), rand.nextInt(600)), 10);
        redDots = new ArrayList<>();

        // Spawn 5 red dots initially
        for (int i = 0; i < 5; i++) {
            spawnRedDot();
        }

        // Set up a timer to spawn red dots every 1-3 seconds
        spawnTimer = new Timer(true);
        spawnTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spawnRedDot();
            }
        }, (rand.nextInt(3) + 1) * 1000, (rand.nextInt(3) + 1) * 1000);

        // Set up a timer to decrease blue dot's food points every second
        blueDotLifeTimer = new Timer(true);
        blueDotLifeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (blueDot != null) {
                    blueDot.setSize(blueDot.getSize() - 1);
                    if (blueDot.getSize() <= 0) {
                        blueDot = null;
                        blueDotLifeTimer.cancel();
                    }
                }
                if (greenDot != null) {
                    greenDot.setSize(greenDot.getSize() - 1);
                    if (greenDot.getSize() <= 0) {
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
                consumerDot.setSize(consumerDot.getSize() + 15);  // Increase size by 15 food points
                break;
            }
        }

        // Remove the consumed red dot
        if (redDotToConsume != null) {
            redDots.remove(redDotToConsume);
        }
    }

    public void update() {
        // Blue and Green dot interaction
        if (blueDot != null && greenDot != null) {
            double distanceBetweenBlueAndGreen = blueDot.getPosition().distance(greenDot.getPosition());
            if (distanceBetweenBlueAndGreen <= blueDot.getSize() / 2 + greenDot.getSize() / 2) {
                // Bounce logic can be implemented here
                blueDot.setSize(blueDot.getSize() - 1); // Decrease food size
                greenDot.setSize(greenDot.getSize() - 1); // Decrease food size
            }
        }

        // Blue dot and red dots interaction
        if (blueDot != null) {
            blueDot.moveTowardsTarget(redDots);
            consumeRedDot(blueDot);
        }

        // Green dot moves towards Red dots
        if (greenDot != null) {
            greenDot.moveTowardsTarget(redDots, blueDot);
            consumeRedDot(greenDot);
        }

        repaint();
    }
}
