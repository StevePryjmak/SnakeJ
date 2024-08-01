import javax.swing.JPanel;
import java.awt.*;
public class SnakeGame extends JPanel {
    int windowWidth;
    int windowHeight;
    int TileSize = 30;
    SnakeGame(int Width, int Height) {
        windowWidth = Width;
        windowHeight = Height;
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBackground(Color.BLACK);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(windowWidth, windowHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, windowWidth, windowHeight);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        for (int x = 0; x < windowWidth; x += TileSize) {
            g.drawLine(x, 0, x, windowHeight);
        }
        for (int y = 0; y < windowHeight; y += TileSize) {
            g.drawLine(0, y, windowWidth, y);
        }

    }


    
}

