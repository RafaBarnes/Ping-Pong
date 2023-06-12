import javax.swing.JFrame;

public class PongFrame extends JFrame {
    public PongFrame() {
        setTitle("FIBSports by TRANKA Games");
        setResizable(false);

        PongPanel pongPanel = new PongPanel();
        add(pongPanel);

        pack();
        setLocationRelativeTo(null);
    }
}