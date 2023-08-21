import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private Timer timer;

    public GameFrame() {
        // Setup the game panel and add it to the frame
        gamePanel = new GamePanel();
        this.add(gamePanel);
        
        // Frame properties
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Blue and Red Dot Game");
        
        // Set up the timer to update game state every 50 ms
        timer = new Timer(50, e -> gamePanel.update());
        timer.start();
    }

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        frame.setVisible(true);
    }
}
