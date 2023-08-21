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
    private Dot blueDot;
    private List<Dot> redDots;
    private Random rand;
    private Timer spawnTimer;
    private Timer blueDotLifeTimer;

    public GamePanel() {
        rand = new Random();

        blueDot = new Dot(new Point(rand.nextInt(800), rand.nextInt(600)), 10, Color.BLUE);
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

        for (Dot dot : redDots) {
            dot.draw(g2d);
        }
    }

    public void update() {
        // Check if the blue dot has reached its target red dot
        if (blueDot != null && blueDot.getTarget() != null) {
            double distanceToTarget = blueDot.getPosition().distance(blueDot.getTarget().getPosition());
            if (distanceToTarget <= 5) {  // If within a certain range, assume the blue dot has consumed the red dot
                System.out.println("Before consuming red dot, blue dot size: " + blueDot.getSize());
                redDots.remove(blueDot.getTarget());
                blueDot.setSize(blueDot.getSize() + 2); 
                System.out.println("After consuming red dot, blue dot size: " + blueDot.getSize());
                blueDot.setTarget(null); 
            }
        }
    
        // Find the nearest red dot to the blue dot
        double minDistance = Double.MAX_VALUE;
        Dot nearestRedDot = null;
        for (Dot redDot : redDots) {
            double distance = blueDot.getPosition().distance(redDot.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                nearestRedDot = redDot;
            }
        }
    
        // If a red dot is found, set it as the blue dot's target
        if (nearestRedDot != null) {
            blueDot.setTarget(nearestRedDot);
        }
    
        // Make the blue dot move towards its target red dot
        if (blueDot.getTarget() != null) {
            blueDot.moveTowardsTarget();
        }
    
        repaint();
    }
    
    
    
}
