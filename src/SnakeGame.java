import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import javax.swing.Timer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




public class SnakeGame extends JPanel implements KeyListener{

    private final int windowWidth, windowHeight, TileSize = 20, fps = 10;
    private Tile food;
    private Deque<Tile> snake = new LinkedList<>();
    private final Random random = new Random();
    private final Timer timer;
    private int dx = 1, dy = 0;
    private boolean moveMade = false;

    private class Tile {
        int x;
        int y;
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean checkTileCollision(Tile tile) {
            return x == tile.x && y == tile.y;
        }
        public void draw(Graphics g, Color color) {
            g.setColor(color);
            g.fillRect(x * TileSize, y * TileSize, TileSize, TileSize);
        }
    }
    

    SnakeGame(int Width, int Height) {
        windowWidth = Width;
        windowHeight = Height;
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBackground(Color.BLACK);
        food = randomFoodTile();
        snake.add(new Tile(0, 0));
        // snake.add(new Tile(4, 5));

        setFocusable(true);
        addKeyListener(this);

        int delay = 1000 / fps;
        timer = new Timer(delay, e -> gameLoop());
        timer.start();

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
        Graphics2D g2d = (Graphics2D) g;
        food.draw(g, Color.RED);
        snake.forEach(tile -> tile.draw(g, Color.GREEN)); // to optimize it is posible to draw only the head and tail
        g2d.setColor(Color.WHITE);

        // Set the thickness for the grid lines
        float thickness = 0.1f;
        g2d.setStroke(new BasicStroke(thickness));

        for (int x = 0; x < windowWidth; x += TileSize) {
            g2d.drawLine(x, 0, x, windowHeight);
        }
        for (int y = 0; y < windowHeight; y += TileSize) {
            g2d.drawLine(0, y, windowWidth, y);
        }
    }

    public void gameLoop() {
        Tile head = snake.peekFirst();
        //Tile newHead = new Tile(head.x + dx, head.y + dy);
        Tile newHead = new Tile((head.x + dx + windowWidth / TileSize) % (windowWidth / TileSize), (head.y + dy + windowHeight / TileSize) % (windowHeight / TileSize));
        snake.addFirst(newHead);

        if (newHead.checkTileCollision(food)) food = randomFoodTile();
        else snake.removeLast();
        for (Tile tile : snake) {
            if (tile != newHead && newHead.checkTileCollision(tile)) {
                timer.stop();
                System.out.println("Game Over");
                break;
            }
        }
        moveMade = true;
        repaint();
    }

    public Tile randomFoodTile() {
        int maxTilesX = windowWidth / TileSize;
        int maxTilesY = windowHeight / TileSize;

        List<Tile> possibleTiles = new ArrayList<>();

        for (int x = 0; x < maxTilesX; x++) {
            for (int y = 0; y < maxTilesY; y++) {
                possibleTiles.add(new Tile(x, y));
            }
        }
        // Remove positions occupied by the snake
        for (Tile snakeTile : snake) {
            possibleTiles.removeIf(tile -> tile.x == snakeTile.x && tile.y == snakeTile.y);
        }
        if (possibleTiles.isEmpty()) {
            throw new IllegalStateException("No possible positions for food"); // @TODO change it to game over
        }

        Tile randomTile = possibleTiles.get(random.nextInt(possibleTiles.size()));
        return new Tile(randomTile.x, randomTile.y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!moveMade) return;
        if (e.getKeyCode() == KeyEvent.VK_UP && dy != 1) {
            dx = 0;
            dy = -1;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && dy != -1) {
            dx = 0;
            dy = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && dx != 1) {
            dx = -1;
            dy = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && dx != -1) {
            dx = 1;
            dy = 0;
        }
        moveMade = false;
    }

    // Unnecessary methods
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

}

